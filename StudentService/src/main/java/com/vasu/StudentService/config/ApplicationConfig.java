package com.vasu.StudentService.config;

import com.vasu.StudentService.entity.StudentScholarship;
import com.vasu.StudentService.repository.StudentScholarshipRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;

@Configuration
@AllArgsConstructor
public class ApplicationConfig {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);
    private final StudentScholarshipRepository repository;

    @Bean
    @StepScope
    public FlatFileItemReader<StudentScholarship> reader(@Value("#{jobParameters[fullPathFileName]}") String pathToFile) {
        logger.info("Configuring FlatFileItemReader");
        FlatFileItemReader<StudentScholarship> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource(new File(pathToFile)));
        itemReader.setName("Student-Reader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<StudentScholarship> lineMapper() {
        logger.info("Configuring LineMapper");
        DefaultLineMapper<StudentScholarship> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("studentRollNumber", "studentName", "scienceMarks", "mathsMarks", "computerMarks", "englishMarks", "isEligible");

        BeanWrapperFieldSetMapper<StudentScholarship> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(StudentScholarship.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public CustomItemProcessor processor() {
        logger.info("Configuring CustomItemProcessor");
        return new CustomItemProcessor();
    }

    @Bean
    public RepositoryItemWriter<StudentScholarship> writer() {
        logger.info("Configuring RepositoryItemWriter");
        RepositoryItemWriter<StudentScholarship> writer = new RepositoryItemWriter<>();
        writer.setRepository(repository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step step(FlatFileItemReader<StudentScholarship> reader, JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        logger.info("Configuring Step");
        return new StepBuilder("Student-Step",jobRepository).
                <StudentScholarship, StudentScholarship>chunk(10,transactionManager)
                .reader(reader)
                .processor(processor())
                .writer(writer())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job runJob(FlatFileItemReader<StudentScholarship> reader, JobCompletionNotificationImpl listener,JobRepository jobRepository,PlatformTransactionManager transactionManager) {
        logger.info("Configuring Job");
        return new JobBuilder("import-Students",jobRepository)
                .listener(listener)
                .flow(step(reader, jobRepository,transactionManager))
                .end()
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        logger.info("Configuring TaskExecutor");
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }
}
