package com.springbatch.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//@Component
//@RequiredArgsConstructor
//public class JobRunner implements ApplicationRunner {
//
//    private final JobLauncher jobLauncher;
//
//
//
//    private final Job helloJob2;
//
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addString("name", "user1")
//                .addLocalDateTime("date", LocalDateTime.now())
//                .toJobParameters();
//        jobLauncher.run(helloJob2, jobParameters);
//    }
//}
