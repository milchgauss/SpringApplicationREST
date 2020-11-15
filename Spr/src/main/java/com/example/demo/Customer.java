package com.example.demo;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

//The Entity annotation
//POJOS representing data that can be persisted to the database.
// Entity represents a table stored in a database
// Every instance of an entity represents a row in a table

@Entity
public class Customer {

    @Id // Specifies the primary key of an entity
    @GeneratedValue(strategy=GenerationType.AUTO)
    //ID is the primary key, and the names and roles
    //are the attributes for our domain objects
    private Long id;

    private String firstname;
    private String lastname;

    private String role;
    //Default constructor exists only for the skae of  JPA.
    //Do not use it directly, so it is designated as protected.

    Customer(){}

    public Customer (String firstname, String lastName,String role){
        this.firstname=firstname;
        this.role=role;
        this.lastname=lastName;
    }


    public Long getID(){
        return this.id;
    }
    public String getFirstName(){
        return this.firstname;
    }
    public String getLastName(){return this.lastname;}
    public String getRole(){
        return this.role;
    }
    public String getName(){return this.firstname+""+this.lastname;}


    public void setId(Long id){
        this.id=id;
    }
    public void setName(String name){
        String[]parts=name.split(" ");
        this.firstname=parts[0];
        this.lastname=parts[1];
    }
    public void setRole(String role){
        this.role=role;
    }

    @Override
    public boolean equals(Object o){

        if (this==o)
            return true;
        //If it is not even an instance of the customer class,
        //It will never be equal:
        if(!(o instanceof Customer))
            return false;

        Customer customer=(Customer)o;

        return Objects.equals(this.id, customer.id) &&
        Objects.equals(this.firstname,customer.firstname)&& Objects.equals(this.lastname,customer.lastname)
        && Objects.equals(this.role,customer.role);

    }
    //Good practice; Override equals, hashCode, and toString
    //To make sure you are dealing with the intended objects

    @Override
    public int hashCode(){
        return Objects.hash(this.id,this.firstname,this.role,this.lastname);

    }
    @Override
    public String toString(){
        return "Id Is:"+this.id
                +"The first name is"+this.firstname
                +"The last name is"+ this.lastname
                + "The role is"+this.role;
    }
    }



