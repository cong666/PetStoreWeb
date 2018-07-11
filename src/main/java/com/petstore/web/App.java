package com.petstore.web;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * Hello world!
 *
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages= {"com.petstore"})
public class App 
{
	@Bean(name="restTemplateLb")
    RestTemplate lbRestTemplate() {
        return new RestTemplate();
    }
    public static void main( String[] args ) {
    	SpringApplication.run(App.class, args);
    }
}
