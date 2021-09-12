package com.liu.flowdemo;

import org.jeasy.batch.core.filter.StartsWithStringRecordFilter;
import org.jeasy.batch.core.job.Job;
import org.jeasy.batch.core.job.JobBuilder;
import org.jeasy.batch.core.job.JobExecutor;
import org.jeasy.batch.core.job.JobReport;
import org.jeasy.batch.core.writer.StandardOutputRecordWriter;
import org.jeasy.batch.flatfile.DelimitedRecordMapper;
import org.jeasy.batch.flatfile.FlatFileRecordReader;
import org.jeasy.batch.validation.BeanValidationRecordValidator;

import java.nio.file.Paths;

public class FlowDemo {
    public static void main(String[] args) {
        String[] fields = {"id", "name", "description", "price", "published", "lastUpdate"};
        Job job = new JobBuilder()
                .reader(new FlatFileRecordReader(Paths.get("D:\\git_repo\\yy\\easy-batch-demo\\src\\main\\resources\\flowdemo\\flow.csv")))
                .filter(new StartsWithStringRecordFilter("#"))
                .mapper(new DelimitedRecordMapper<>(Product.class, fields))
                .validator(new BeanValidationRecordValidator())
                .processor(new ProductProcessor())
                .writer(new StandardOutputRecordWriter())
                .build();

        JobExecutor jobExecutor = new JobExecutor();
        JobReport report = jobExecutor.execute(job);
        jobExecutor.shutdown();

        System.out.println("job report = " + report);
    }
}
