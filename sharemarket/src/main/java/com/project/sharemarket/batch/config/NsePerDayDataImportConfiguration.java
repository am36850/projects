package com.project.sharemarket.batch.config;

import com.project.sharemarket.domains.NsePerDayData;
import com.project.sharemarket.mapper.NsePerDayDataMapper;
import com.project.sharemarket.repository.NsePerDayDataRepository;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class NsePerDayDataImportConfiguration {

    private final NsePerDayDataRepository nsePerDayDataRepository;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final NsePerDayDataMapper nsePerDayDataMapper;

    @Value(value = "${nse.data.location}*.txt")
    private Resource[] resources;

    @Value(value = "${nse.data.location}")
    private String fileLocation;

    @Autowired
    public NsePerDayDataImportConfiguration(NsePerDayDataRepository nsePerDayDataRepository, JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
            NsePerDayDataMapper nsePerDayDataMapper) {
        this.nsePerDayDataRepository = nsePerDayDataRepository;
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.nsePerDayDataMapper = nsePerDayDataMapper;
    }

    @Bean
    @StepScope
    FlatFileItemReader<NsePerDayData> reader(@Value("#{stepExecutionContext['fileName']}") String filename) {
        //@formatter:off
        return new FlatFileItemReaderBuilder<NsePerDayData>()
                 .name("NsePerDayDataFileReader")
                 .targetType(NsePerDayData.class)
                 .resource(new FileSystemResource(fileLocation+"/"+filename+".txt"))
                 .lineMapper(getLineMapper())
                 .linesToSkip(1)
                 .delimited()
                 .names(new String[]{"ticker","date","open","high","low","close","volume","oi"})
                 .build();
        //@formatter:on
    }

    @Bean
    DefaultLineMapper<NsePerDayData> getLineMapper() {
        DefaultLineMapper<NsePerDayData> defaultLineMapper = new DefaultLineMapper<>();
        defaultLineMapper.setFieldSetMapper(nsePerDayDataMapper);
        defaultLineMapper.setLineTokenizer(new DelimitedLineTokenizer());
        return defaultLineMapper;
    }

    @Bean
    MultiResourceItemReader<NsePerDayData> multiResourceItemReader() {
        MultiResourceItemReader<NsePerDayData> multiResourceItemReader = new MultiResourceItemReader<>();
        multiResourceItemReader.setResources(resources);
        multiResourceItemReader.setDelegate(reader(""));
        return multiResourceItemReader;
    }

    @Bean
    Step nseDataImporterStep() {
        //@formatter:off
        return stepBuilderFactory.get("NseDataImporterStep")
                .<NsePerDayData, NsePerDayData>chunk(10)
                .reader(multiResourceItemReader())
                .writer(writer())
                .faultTolerant()
                .skip(FlatFileParseException.class)
                .skipLimit(1000000)
                .build();
        //@formatter:on
    }

    @Bean
    RepositoryItemWriter<NsePerDayData> writer() {
        //@formatter:off
        return new RepositoryItemWriterBuilder<NsePerDayData>()
                    .repository(nsePerDayDataRepository)
                    .methodName("save")
                    .build();
        //@formatter:on
    }

    @Bean
    Job importUserJob() {
        //@formatter:off
        return jobBuilderFactory.get("ImportUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(nseDataImporterStep())
                .end()
                .build();
        //@formatter:on
    }

    @Bean
    public Step partitionStep() {
        //@formatter:off
        return stepBuilderFactory.get("partitionStep")
                .partitioner("slaveStep", partitioner())
                .step(nseDataImporterStep())
                .taskExecutor(taskExecutor())
                .build();
        //@formatter:on
    }

    @Bean
    public MultiResourceDeliveryFilePartitioner partitioner() {
        return new MultiResourceDeliveryFilePartitioner(resources);
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setQueueCapacity(5);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }

}
