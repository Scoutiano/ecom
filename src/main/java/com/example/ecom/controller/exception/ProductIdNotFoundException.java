package com.example.ecom.controller.exception;

import com.example.ecom.model.Entity;

public class ProductIdNotFoundException extends  BadRequestException{
    public ProductIdNotFoundException() {
        super("The passed Id is not found", Entity.PRODUCT, "id_missing");
    }
}
