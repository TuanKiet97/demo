package com.demo.restaurant.controller;

import com.demo.restaurant.models.dto.MenuDTO;
import com.demo.restaurant.models.entities.Menu;
import com.demo.restaurant.responses.ResponseObject;
import com.demo.restaurant.services.MenuServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;


@RestController
@RequestMapping(value = "/product")
public class MenuController {
    @Autowired
    private MenuServices menuServices;

    @PostMapping("")
    public ResponseEntity createProduct(@RequestBody MenuDTO product) {
	MenuDTO addedProduct = menuServices.createProduct(product);
	String message = "Add new product successfully";
	return ResponseEntity.status(HttpStatus.OK).body(
		new ResponseObject(201, message, addedProduct));
    }


    @GetMapping("/name")
    public ResponseEntity<ResponseObject> getOneProductByName(@PathVariable String name) {
	MenuDTO productName = menuServices.getOneProductByName(name);
	String message = "Get one product successfully";
	return ResponseEntity.status(HttpStatus.OK).body(
		new ResponseObject(200, message, productName));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateProduct(@PathVariable UUID id,
							@RequestBody @Validated MenuDTO product) {
	MenuDTO updatedProduct = menuServices.updateProduct(id, product);
	String message = "Update product successfully";

	return ResponseEntity
		.status(HttpStatus.OK)
		.body(new ResponseObject(200, message, updatedProduct));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteProduct( @PathVariable UUID id) {
	MenuDTO menuDTOItem = menuServices.deleteProduct(id);
	String message = "Delete product successfully";

	return ResponseEntity
		.status(HttpStatus.OK)
		.body(new ResponseObject(200, message, menuDTOItem));

    }


    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllProductsOrderByStatusAsc(@RequestParam(name = "sort", required = false, defaultValue = "") String sort,
									 @RequestParam(name = "size", required = false, defaultValue = "4") int size,
									 @RequestParam(name = "page", required = false, defaultValue = "1") int page) {
	Set<MenuDTO> products = menuServices.getAllProductsOrderByStatusAsc(sort, size, page);
	String message = !products.isEmpty() ?
		"Get all products successfully" :
		"Other products are being updated";

	return ResponseEntity
		.status(HttpStatus.OK)
		.body(new ResponseObject(200, message, products));

    }

}
