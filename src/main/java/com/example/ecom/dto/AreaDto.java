package com.example.ecom.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class AreaDto {

    private Long id;

    @NonNull
    private String areaName;

    @NonNull
    private Long city;
}
