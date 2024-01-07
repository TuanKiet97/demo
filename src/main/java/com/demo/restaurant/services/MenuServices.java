package com.demo.restaurant.services;

import com.demo.restaurant.models.dto.MenuDTO;
import com.demo.restaurant.models.entities.Menu;

import java.util.Set;
import java.util.UUID;

public interface MenuServices {

    Set<MenuDTO> getAllProductsOrderByStatusAsc( String sort,int size, int page);
    MenuDTO getOneProductByName(String name);

    Menu getOneProductById(UUID uuid);

    MenuDTO createProduct(MenuDTO product);

    MenuDTO updateProduct(UUID id, MenuDTO product);

    MenuDTO deleteProduct(UUID id);
}
