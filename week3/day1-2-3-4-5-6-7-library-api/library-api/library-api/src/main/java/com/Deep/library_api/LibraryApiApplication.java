package com.Deep.library_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableCaching //Turn on the caching abstraction and look for @Cacheable annotations
@EnableAsync
@EnableScheduling
public class LibraryApiApplication implements AsyncConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApiApplication.class, args);
	}

	@Override
	public Executor getAsyncExecutor() {  // Add this
		return taskExecutor();
	}

	@Bean(name = "taskExecutor")
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);      // Min threads always ready
		executor.setMaxPoolSize(50);       // Max threads if needed
		executor.setQueueCapacity(100);    // Queue size before rejecting
		executor.setThreadNamePrefix("async-");
		executor.initialize();
		return executor;
	}

}
