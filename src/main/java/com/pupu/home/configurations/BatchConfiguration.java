package com.pupu.home.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.pupu.home.dto.Member;
import com.pupu.home.notifiers.JobCompletionNotificationListener;
import com.pupu.home.processor.PersonItemProcessor;
import com.pupu.home.readers.PersonItemReader;
import com.pupu.home.writers.PersonItemWriter;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
	}
	
	@Bean
	public JobCompletionNotificationListener listener() {
		return new JobCompletionNotificationListener();
	}

	@Bean
	public PersonItemReader reader() {
		return new PersonItemReader();
	}

	@Bean
	public PersonItemProcessor processor() {
		return new PersonItemProcessor();
	}

	@Bean
	public PersonItemWriter writer() {
		return new PersonItemWriter();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<Member, Member>chunk(10).reader(reader()).processor(processor()).writer(writer()).build();
	}
	
	@Bean
	public JobCompletionNotificationListener JobCompletionNotificationListener() {
		return new JobCompletionNotificationListener();
	}

	@Bean
	public Job job(Step step1) {
		return jobBuilderFactory.get("myJob").start(step1).listener(JobCompletionNotificationListener()).build();
	}
}
