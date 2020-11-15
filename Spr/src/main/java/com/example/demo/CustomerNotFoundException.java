package com.example.demo;
// Extends a runtime exception in case
//a customer is not found


public class CustomerNotFoundException extends RuntimeException {
CustomerNotFoundException(Long id){
    super("Could't find the customer!"+id);
}
}
