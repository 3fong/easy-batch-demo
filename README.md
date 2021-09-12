## easy-batch 使用demo

easy-batch 是一个简化批处理的java框架.批处理中很多非业务处理代码如:读,写,过滤,解析,校验,日志记录,结果报告等.easy-batch就是为了简化这些操作,让你专注于核心业务处理

### easy-batch 工作方式

通过easy-batch Job 抽象出简单的处理链.所有的操作都围绕Record展开.Job按顺序读取数据源,按步骤进行业务处理,并批量写入到数据槽中.

![easy-batch Job处理流程](https://img2020.cnblogs.com/blog/1096086/202109/1096086-20210912223953876-1856743113.png)


框架通过提供Record,Batch API,抽象数据格式,以实现在流程处理中采用统一的方式,与数据源和数据槽类型无关.



### 使用

- 引入maven依赖.
   
实际使用中可以只引入: easy-batch-core.这里由于使用了实体校验,故直接引入了校验包,它内部引入了easy-batch-core.

```
        <dependency>
            <groupId>org.jeasy</groupId>
            <artifactId>easy-batch-validation</artifactId>
            <version>7.0.1</version>
        </dependency>
```

- 使用

```
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
```

reader: 读取解析外部文件    
filters: 进行数据过滤    
mapper: 字段与实体映射(绑定)    
validator: 数据校验    
processor: 引入自定义业务流程处理(你应该关系的核心业务逻辑)    
writer: 结果写出逻辑    
report: 执行结果    

- 项目结构介绍

flowdemo: 项目流程处理示例.包含了实际业务处理流程processor.    
inputoutputdemo: 文件读入文件导出示例.    

#### 参考资料

[easy-batch document](https://github.com/j-easy/easy-batch)    
[easy-batch 核心概念](https://www.cnblogs.com/rongfengliang/p/12727136.html)