package com.cbfacademy.apiassessment;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.List; 

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@RestController
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
    @Bean
    public CommandLineRunner commandLineRunner(TreeService treeService) {
        return args -> {
           
            List<Tree> trees = treeService.getAllTrees();
            System.out.println("Total trees: " + trees.size());

    
        };
    }
}
