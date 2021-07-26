package com.example.ecom.controller.exception;

public class CityIdNotFoundException extends  BadRequestException{

    public CityIdNotFoundException() {
        super("The passed Id is not found", "city", "id_missing");
    }
}
