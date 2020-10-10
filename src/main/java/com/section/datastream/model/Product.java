package com.section.datastream.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Product {

    @JsonProperty("productId")
    private int productId;

    @JsonProperty("productName")
    private String productName;

    @JsonProperty("productManufacturingCity")
    private String productManufacturingCity;
}
