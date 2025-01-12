package com.ororura.cryptobazar.dtos;

import lombok.Data;

@Data
public class ProductDTO {
    private String name;
    private String description;
    private String owner;
    private Integer amount;
    private String photo;
}
