package com.example.ecom.controller.exception;

import com.example.ecom.model.Entity;

public class CustomerIdNotFoundException extends BadRequestException{
    public CustomerIdNotFoundException() {
        super("The passed Id is not found", Entity.CUSTOMER, "id_missing");
    }
}
