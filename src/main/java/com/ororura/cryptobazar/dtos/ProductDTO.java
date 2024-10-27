package com.ororura.cryptobazar.dtos;

import lombok.Data;

@Data
public class ProductDTO {
    private String name;
    private String description;
    private Long ownerId;
    private Integer amount;
}
