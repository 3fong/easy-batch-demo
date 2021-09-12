package com.liu.flowdemo;

import org.jeasy.batch.core.processor.RecordProcessor;
import org.jeasy.batch.core.record.GenericRecord;
import org.jeasy.batch.core.record.Record;

public class ProductProcessor implements RecordProcessor<Product, Product> {

    @Override
    public Record<Product> processRecord(Record<Product> record) {
        Product product = record.getPayload();
        product.setName(product.getName().toUpperCase());
        return new GenericRecord<>(record.getHeader(), product);
    }
}
