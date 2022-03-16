package com.example.socks.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class SocksDto {

    @NotEmpty
    private String color;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer cottonPart;

    @NotNull
    @Positive
    private Integer quantity;
}
