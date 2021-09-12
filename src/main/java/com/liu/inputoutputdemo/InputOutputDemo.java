package com.liu.inputoutputdemo;



import org.jeasy.batch.core.filter.HeaderRecordFilter;
import org.jeasy.batch.core.job.Job;
import org.jeasy.batch.core.job.JobBuilder;
import org.jeasy.batch.core.job.JobExecutor;
import org.jeasy.batch.core.job.JobReport;
import org.jeasy.batch.core.writer.FileRecordWriter;
import org.jeasy.batch.core.writer.StandardOutputRecordWriter;
import org.jeasy.batch.flatfile.DelimitedRecordMapper;
import org.jeasy.batch.flatfile.FlatFileRecordReader;
import org.jeasy.batch.xml.XmlRecordMarshaller;

import javax.xml.bind.JAXBException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InputOutputDemo {

    public static void main(String[] args) throws JAXBException {
        Path path = FileSystems.getDefault().getPath("inputoutdemo/tweets.csv");

        Job job = InputOutputDemo.fromCsv2Xml();
        // Execute the job
        JobExecutor jobExecutor = new JobExecutor();
        JobReport report = jobExecutor.execute(job);
        jobExecutor.shutdown();

        // Print the job execution report
        System.out.println(report);
    }


    private static Job standardOutput() throws JAXBException {
        // Create the data source
        Path dataSource = Paths.get("inputoutdemo/tweets.csv");

        // Build a batch job
        return new JobBuilder<String, String>()
                .named("hello world job")
                .reader(new FlatFileRecordReader(dataSource))
                .writer(new StandardOutputRecordWriter<>())
                .build();

    }
    private static Job fromCsv2Xml() throws JAXBException {
        Path inputFile = Paths.get("D:\\git_repo\\yy\\easy-batch-demo\\src\\main\\resources\\inputoutdemo\\tweets.csv");
        Path outputFile = Paths.get("D:\\git_repo\\yy\\easy-batch-demo\\src\\main\\resources\\inputoutdemo\\tweets.xml");
        return new JobBuilder<String, String>()
                .reader(new FlatFileRecordReader(inputFile))
                .filter(new HeaderRecordFilter<>())
                .mapper(new DelimitedRecordMapper<>(Tweet.class, "id", "user", "message"))
                .marshaller(new XmlRecordMarshaller<>(Tweet.class))
                .writer(new FileRecordWriter(outputFile))
                .batchSize(10)
                .build();
    }
}
