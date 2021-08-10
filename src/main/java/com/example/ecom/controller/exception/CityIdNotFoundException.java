package com.example.ecom.controller.exception;

import com.example.ecom.model.Entity;

public class CityIdNotFoundException extends  BadRequestException{
    public CityIdNotFoundException() {
        super("The passed Id is not found", Entity.CITY, "id_missing");
    }
}
