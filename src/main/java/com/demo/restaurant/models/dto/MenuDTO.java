package com.demo.restaurant.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuDTO {
    private UUID id;

    private String name;

    private String description;

    private String image;

    private double price;

    private String type;

    private String status;

    public MenuDTO(String name, String description, String image, double price, String type) {
	this.name = name;
	this.description = description;
	this.image = image;
	this.price = price;
	this.type = type;
    }
}
