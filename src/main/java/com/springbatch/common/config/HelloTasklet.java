package com.springbatch.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
public class HelloTasklet implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("================ step2 ================== ");

        JobExecution jobExecution = contribution.getStepExecution().getJobExecution();
        ExecutionContext executionContext = jobExecution.getExecutionContext();
        // 실패해도 다시 실행하면 context에 저장된 값을 가져올 수 있다.
        String test = (String) executionContext.get("test");
        log.info(test);
        Thread.sleep(10000);
//        throw new RuntimeException("예외발생!");
        return null;
    }
}
