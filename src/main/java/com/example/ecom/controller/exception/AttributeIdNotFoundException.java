package com.example.ecom.controller.exception;

import com.example.ecom.model.Entity;

public class AttributeIdNotFoundException extends  BadRequestException{
    public AttributeIdNotFoundException() {
        super("The passed Id is not found", Entity.ATTRIBUTE, "id_missing");
    }
}
