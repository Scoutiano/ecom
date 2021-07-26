package com.example.ecom.controller.exception;

public class NullIdException extends BadRequestException{
    public NullIdException(String entity) {
        super("Passed" + entity + "Id is null", entity, "id_null");
    }
}
