package com.example.demo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//This is the MVC layer of the application
//The goal is to wrap the repository using Spring's web modules (MVC-> Module View Controller)
// -> And then apply REST -> Representational state transfer
@RestController
public class CustomerController {
    private final CustomerRepository repository;
    private final CustomerAssembler customerAssembler;

    CustomerController(CustomerRepository repository,CustomerAssembler customerAssembler){

        this.repository=repository;
        this.customerAssembler=customerAssembler;
    }

    //Root
    // Retrieves the current customers
    /*
    @GetMapping("/customers")
    List<Customer> all(){
        return repository.findAll();
    }
    */

    @PutMapping("/employees/{id}")
    ResponseEntity<?> replaceCustomer(@RequestBody Customer customer, @PathVariable Long id){
        Customer updatedCustomer=repository.findById(id)
                .map(customerO->{
                    customerO.setName(customer.getName());
                    customerO.setName(customer.getRole());
                    return repository.save(customerO);

                })
                .orElseGet(()->{
                    customer.setId(id);
                    return repository.save(customer);

                });
        EntityModel<Customer> customerModel = customerAssembler.toModel(updatedCustomer);
        return ResponseEntity
                .created(customerModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(customerModel);
    }

    //Saves customers
    @PostMapping("/customers")

    //ResponseEntity is used to create an HTTP 201 Create dstatus message
    ///Typically includes a Location response heade,r and we use the
    //URI derived from the model's self-related link
    ResponseEntity<?> newCustomers(@RequestBody Customer newCustomers){
        EntityModel<Customer> entityModel=customerAssembler.toModel(repository.save(newCustomers));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }


    @GetMapping("/employees")
    CollectionModel<EntityModel<Customer>> all() {

        List<EntityModel<Customer>> customers= repository.findAll().stream()
                .map(customerAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(customers, linkTo(methodOn(CustomerController.class).all()).withSelfRel());
        // .withSelfRel -> Build a link to the all() method.
        //This is the aggregate route!
        //For the one() method, this is the link to the specific client
        //HATEOS stands for:

        //Hypermedia as the Engine of Application state
        //Application architecture that distinguishes it
        //from other network applications

        //Interacts with a network application whose application servers provide
        //information dynamically through hypermedia

        //Links make it possible to evolve REST
        //services over time, existing links can be  maintained while new links
        //are added in the future
        //HATEOS container Collecitonmodel is aimed at
        //encapsulating collections, and lets you include links
/*        List<EntityModel<Customer>> employees = repository.findAll().stream()
                .map(customer -> EntityModel.of(customer,
                        linkTo(methodOn(CustomerController.class).one(customer.getID())).withSelfRel(),
                        linkTo(methodOn(CustomerController.class).all()).withRel("employees")))
                .collect(Collectors.toList());

        return CollectionModel.of(employees, linkTo(methodOn(CustomerController.class).all()).withSelfRel());*/
    }
    @GetMapping("/employees/{id}")
    EntityModel<Customer> one(@PathVariable Long id) {

        Customer customer = repository.findById(id) //
                .orElseThrow(() -> new CustomerNotFoundException(id));

        //Assemblers are a part of HATEOS :
        // A subclass of entity models that will
        //Assemble URLs. Premade in HATEOS.
        //Hypermedia as the Engine of Application State

        //Application architecture that dstinguishes it from
        //other network applications

        // REST client now needs no knowledge of prior knowledge
        //and how to interact with an application or server
        //beyond knowledge of hypermedia

        //Decouples the client and server without needing to
        //operate through a shared interface.
        //Server can evolve independently, links can be altered in
        //case sofware updates


        return customerAssembler.toModel(customer);
    }

    @DeleteMapping("employees/{id}")
    ResponseEntity<?> deleteEmployee(@PathVariable Long id){

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }



    //Getting individual customers
    //We will wrap the object in an EntityModel
    // This will allow us to add links to it.
    // Remember: RESTful applications allow you to add
    //Links to it without hardcoding URIs
/*
    @GetMapping("/employees/{id}")
         EntityModel<Customer> one(@PathVariable Long id){
         Customer customer= repository.findById(id)
                .orElseThrow(()-> new CustomerNotFoundException(id));
        //Entity models return links + data, instead of only the data
        return EntityModel.of(customer,
                linkTo(methodOn(CustomerController.class).one(id)).withSelfRel(),
                //Builds a link to the aggregate root and calls it employees
                linkTo(methodOn(CustomerController.class).all()).withRel("employees"));
    }*/


    // Below is the method without using RESTful services
   /* @GetMapping("/employees/{id}")
    Customer one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }*/
// We'll make another class to handle a 404 issue, or rather,
    //To label the error as 404.




}
