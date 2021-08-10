package com.example.ecom.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Required;

import java.util.Date;

@Setter
@Getter
@RequiredArgsConstructor
public class CustomerDto {

    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private Date dob;
    @NonNull
    private Long area;
}
