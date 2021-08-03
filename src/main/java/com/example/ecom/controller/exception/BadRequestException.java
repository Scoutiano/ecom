package com.example.ecom.controller.exception;

import com.example.ecom.model.Entity;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;

public class BadRequestException extends AbstractThrowableProblem {
    private Entity entity;
    private String errorCode;
    public BadRequestException(String message, Entity entity, String errorCode) {
        super(null, message, Status.BAD_REQUEST, errorCode);
        this.errorCode = errorCode;
        this.entity = entity;
    }
}
