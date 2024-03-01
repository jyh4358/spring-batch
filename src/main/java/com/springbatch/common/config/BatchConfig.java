package com.springbatch.common.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final JobRepository jobRepository;


    @Bean
    public Job helloJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("helloJob", jobRepository)
                .start(helloStep1(jobRepository, platformTransactionManager))
                .next(helloStep2(jobRepository, platformTransactionManager))
                .build();
    }

//    @Bean(name = "helloJob2")
//    public Job helloJob2(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
//
//        return new JobBuilder("helloJob2", jobRepository)
//                .start(helloStep1(jobRepository, platformTransactionManager))
//                .next(helloStep2(jobRepository, platformTransactionManager))
//                .build();
//    }

    @Bean
    public Step helloStep1(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("helloStep", jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        // jobParameter 가져오기
                        JobParameters jobParameters = contribution.getStepExecution().getJobParameters();
                        JobExecution jobExecution = contribution.getStepExecution().getJobExecution();
                        ExecutionContext executionContext = jobExecution.getExecutionContext();
                        executionContext.put("test", "test!!!");
                        log.info("================ step1 ================== ");
                        log.info("{} :::: {}", jobParameters.getLocalDateTime("date"), jobParameters.getString("name"));

                        return null;
                    }
                }, platformTransactionManager)
                .build();
    }

    @Bean
    public Step helloStep2(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("helloStep", jobRepository)
                .tasklet(new HelloTasklet(), platformTransactionManager)
                .build();
    }

    @Bean
    public TaskExecutorJobLauncher taskExecutorJobLauncher() throws Exception {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }
}
