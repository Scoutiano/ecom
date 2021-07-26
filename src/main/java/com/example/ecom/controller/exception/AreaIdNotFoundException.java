package com.example.ecom.controller.exception;

public class AreaIdNotFoundException extends  BadRequestException{

    public AreaIdNotFoundException() {
        super("The passed Id is not found", "area", "id_missing");
    }
}
