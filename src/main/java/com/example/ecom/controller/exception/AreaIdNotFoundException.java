package com.example.ecom.controller.exception;

import com.example.ecom.model.Entity;

public class AreaIdNotFoundException extends  BadRequestException{
    public AreaIdNotFoundException() {
        super("The passed Id is not found", Entity.AREA, "id_missing");
    }
}
