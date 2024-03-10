package com.banne;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableConfigurationProperties
public class MainApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(MainApplication.class,args);
    }
}
