package com.example.ecom.controller.exception;

import com.example.ecom.model.Entity;

import java.util.Locale;

public class NullIdException extends BadRequestException{
    public NullIdException(Entity entity) {
        super("Passed" + entity.name().toLowerCase() + "Id is null", entity, "id_null");
    }
}
