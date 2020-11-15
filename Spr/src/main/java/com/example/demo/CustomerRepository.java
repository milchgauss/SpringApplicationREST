package com.example.demo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
//This interface maps each customer to a Long by extending the use
    //of the Jpa repository
    //Jobs of this repository:
//Creates new instances
    //Updates existing ones
    //Deleting
    //Finding (one, all, by complex properties)
}
