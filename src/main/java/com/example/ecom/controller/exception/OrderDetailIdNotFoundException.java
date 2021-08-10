package com.example.ecom.controller.exception;

import com.example.ecom.model.Entity;

public class OrderDetailIdNotFoundException extends BadRequestException{
    public OrderDetailIdNotFoundException() {
        super("The passed Id is not found", Entity.ORDER, "id_missing");
    }
}
