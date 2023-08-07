package com.idstar.karyawan.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadConfiguration {
    //    https://rizkimufrizal.github.io/belajar-spring-async/
    /*
    https://stackoverflow.com/questions/17659510/core-pool-size-vs-maximum-pool-size-in-threadpoolexecutor
    https://www.baeldung.com/java-threadpooltaskexecutor-core-vs-max-poolsize
     */

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setThreadNamePrefix("default_task_executor_thread");
        executor.initialize();
        return executor;
    }

}

