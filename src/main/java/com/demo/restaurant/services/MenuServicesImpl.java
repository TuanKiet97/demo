package com.demo.restaurant.services;

import com.demo.restaurant.exceptions.ResponseBadRequest;
import com.demo.restaurant.exceptions.ResponseNotImplement;
import com.demo.restaurant.models.dto.MenuDTO;
import com.demo.restaurant.models.entities.Menu;
import com.demo.restaurant.models.mapper.MenuMapper;
import com.demo.restaurant.repositories.MenuRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Set;
import java.util.UUID;

@Service
public class MenuServicesImpl implements MenuServices {

    private static final Logger LOGGER = LogManager.getLogger(MenuServicesImpl.class);

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public MenuDTO getOneProductByName(String name) {
	Menu productName = menuRepository.findProductByName(name)
		.orElseThrow(() -> {
		    LOGGER.warn("No product does not exist with name: {}", name);
		    return new ResponseBadRequest("No product does not exist with name: " + name);
		});
	return menuMapper.convertEntityToDTO(productName);
    }

    @Override
    public MenuDTO createProduct(MenuDTO product) {
	boolean existProduct = menuRepository.existsProductByName(product.getName());
	String productName = product.getName();
	if(!existProduct) {
	    Menu addedProduct = menuMapper.convertDTOToEntity(product);
	    menuRepository.save(addedProduct);
	    return menuMapper.convertEntityToDTO(addedProduct);
	} else {
	    LOGGER.warn("Product with name '{}' already exists", productName);
	    throw new ResponseNotImplement("Product name already exists");
	}
    }


    @Override
    public Menu getOneProductById(UUID id) {
	return menuRepository.findById(id)
		.orElseThrow(() -> {
		    LOGGER.warn("No product does not exist with id: {}",  id );
		    return new ResponseBadRequest("No product does not exist with id: " + id);
		}
	);
    }


    @Override
    public MenuDTO updateProduct(UUID id, MenuDTO product) {
	Menu existProduct = menuRepository.findById(id)
		.orElseThrow(()->{
		    LOGGER.warn("No product does not exist with id: {}", id);
		    return new ResponseNotImplement("No product does not exist with id");
		});
	Boolean productName = menuRepository.existsProductByName(product.getName());
	if(!productName){
	    existProduct.setName(product.getName());
	    existProduct.setDescription(product.getDescription());
	    existProduct.setImage(product.getImage());
	    existProduct.setType(product.getType());

	    menuRepository.save(existProduct);
	    return menuMapper.convertEntityToDTO(existProduct);
	} else {
	    LOGGER.warn("Product name with id {} already exists", id);
	    throw new ResponseNotImplement("Product name already exists");
	}
    }

    @Override
    public MenuDTO deleteProduct(UUID id) {
	Menu product = menuRepository.findById(id)
		.orElseThrow(()-> {
		    LOGGER.warn("No product does not exist with id: {}", id);
		    return new ResponseBadRequest("No product does not exist with id: " + id);
		});
	menuRepository.save(product);
	return menuMapper.convertEntityToDTO(product);
    }



    @Override
    public Set<MenuDTO> getAllProductsOrderByStatusAsc(String sort, int size, int page) {
	return null;
    }


}
