package com.SBT_BatchJob.config;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import com.SBT_BatchJob.entity.Employee;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private EntityManagerFactory entityManagerFactory;


       @Bean
       @Scheduled(cron = "0 0 0 L * ?")
       //@Scheduled(fixedDelay = 120000) // 2 minutes in milliseconds
    public Job updateExperienceJob() {
        return jobBuilderFactory.get("updateExperienceJob")
                .incrementer(new RunIdIncrementer())
                .start(updateExperienceStep())
                .build();
    }

    @Bean
    public Step updateExperienceStep() {
        return stepBuilderFactory.get("updateExperienceStep")
                .<Employee, Employee>chunk(10)
                .reader(employeeReader())
                .processor(new EmployeeItemProcessor())
                .writer(employeeWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Employee> employeeReader() {
        JpaPagingItemReader<Employee> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("SELECT e FROM Employee e");
        return reader;
    }



    @Bean
    public JpaItemWriter<Employee> employeeWriter() {
        JpaItemWriter<Employee> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }


}
