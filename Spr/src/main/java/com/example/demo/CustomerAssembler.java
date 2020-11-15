package com.example.demo;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


//To simplify link creation so as to not repeat links!
@Component
public class CustomerAssembler implements RepresentationModelAssembler<Customer,EntityModel<Customer>> {

    @Override
    public EntityModel<Customer> toModel(Customer customer) {
       return EntityModel.of(customer,
               linkTo(methodOn(CustomerController.class).one(customer.getID())).withSelfRel(),
               linkTo(methodOn(CustomerController.class).all()).withRel("employees"));

// toModel() is based on converting a non model object (Customer) into a model
        //based object
        // Model defines a holder for model attributes and is primarily
        //designed for adding attributes to the model
    }
}
