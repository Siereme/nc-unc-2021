package com.app.config;

import com.app.model.emailInfo.NewEmail;
import com.app.repository.EmailsRepository;
import com.app.service.mail.job.QueueItemProcessor;
import com.app.service.mail.job.QueueItemReader;
import com.app.service.mail.job.QueueItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.util.List;

import static com.app.ConstantVariables.INT_JOB_BATCH_SIZE;
import static com.app.ConstantVariables.JOB_BATCH_SIZE;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Autowired
    EmailsRepository repository;

    @Bean
    public ItemReader<NewEmail> itemReader() throws UnexpectedInputException, ParseException {
        return new QueueItemReader<>(repository.getNewFilmEmails());
    }

    @Bean
    public ItemProcessor<NewEmail, NewEmail> itemProcessor() {
        return new QueueItemProcessor();
    }

    @Bean
    public ItemWriter<NewEmail> itemWriter(List<NewEmail> emails) throws MalformedURLException {
        return new QueueItemWriter();
    }

    @Bean
    protected Step step1(ItemReader<NewEmail> reader,
                         ItemProcessor<NewEmail, NewEmail> processor,
                         ItemWriter<NewEmail> writer) {
        return steps.get("step1").<NewEmail, NewEmail> chunk(INT_JOB_BATCH_SIZE)
                .reader(reader).processor(processor).writer(writer).build();
    }

    @Bean(name = "firstBatchJob")
    public Job job(@Qualifier("step1") Step step1) {
        return jobs.get("firstBatchJob").start(step1).build();
    }
}
