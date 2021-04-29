package com.regur.springbatch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j // log 사용자를 위한 lombok 어노테이션
@RequiredArgsConstructor // 생성자 DI를 위한 lombok 어노테이션
@Configuration
public class SimpleJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleJob(){
        return jobBuilderFactory.get("simpleJob") // batch job 이름
                .start(simpleStep1(null))
                .next(simpleStep2(null))
                .build();
    }

    @Bean
    @JobScope
    public Step simpleStep1(@Value("#{jobParameters[requestDate]}") String requestDate){
        return stepBuilderFactory.get("simpleStep1") // step 이름
                .tasklet((stepContribution, chunkContext) -> { // step 안에서 수행될 기능 step 안에서 단일로 수행될 커스텀한 기능
                    log.info(">>>> This is Step1");
                    log.info(">>>> requestDate {}", requestDate);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    @JobScope
    public Step simpleStep2(@Value("#{jobParameters[requestDate]}") String requestDate){
        return stepBuilderFactory.get("simpleStep2") // step 이름
                .tasklet((stepContribution, chunkContext) -> { // step 안에서 수행될 기능 step 안에서 단일로 수행될 커스텀한 기능
                    log.info(">>>> This is Step2");
                    log.info(">>>> requestDate {}", requestDate);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
