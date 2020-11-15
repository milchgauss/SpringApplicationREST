package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadCustomers {

    //Initialize a logger and import same data!
    //@SpringBootApplication will automatically enable component scanning

    private static final Logger log= LoggerFactory.getLogger(LoadCustomers.class);

    @Bean
    CommandLineRunner initDataBase(CustomerRepository repository,OrderRepo orderRepo){
        return args -> {
            log.info("Loading " + repository.save(new Customer("EmployeeOne","Last!", "Stacker")));
            log.info("Loading " + repository.save(new Customer("EmloyeeTwo","LastTwo!", "Sorter")));
            log.info("Loading"+ repository.save(new Customer("EmployeeThree","LastThree!","HybridEmployee")));


            orderRepo.save(new Order("",Status.COMPLETED));
            orderRepo.save(new Order("",Status.IN_PROGRESS));

        };

    }

}
