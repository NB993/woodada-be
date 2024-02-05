package com.woodada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@ImportAutoConfiguration({FeignAutoConfiguration.class})
@EnableFeignClients
@ConfigurationPropertiesScan
@SpringBootApplication
public class WoodadaBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(WoodadaBeApplication.class, args);
	}

}
