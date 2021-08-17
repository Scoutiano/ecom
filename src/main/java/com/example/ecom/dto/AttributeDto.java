package com.example.ecom.dto;

import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class AttributeDto {
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String value;

    @NonNull
    private Integer position;
}
