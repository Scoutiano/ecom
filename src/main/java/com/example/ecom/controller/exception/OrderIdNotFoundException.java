package com.example.ecom.controller.exception;

import com.example.ecom.model.Entity;

public class OrderIdNotFoundException extends BadRequestException{
    public OrderIdNotFoundException() {
        super("The passed Id is not found", Entity.ORDER, "id_missing");
    }
}
