package com.demo.restaurant.models.mapper;

import com.demo.restaurant.models.dto.MenuDTO;
import com.demo.restaurant.models.entities.Menu;
import org.springframework.stereotype.Component;

import static com.demo.restaurant.common.enums.MenuStatus.ON_SELL;

@Component
public class MenuMapper {
    public MenuDTO convertEntityToDTO(Menu menu) {
	MenuDTO menuDTO = new MenuDTO();

	menuDTO.setId(menu.getMenuItemId());
	menuDTO.setName(menu.getName());
	menuDTO.setDescription(menu.getDescription());
	menuDTO.setImage(menu.getImage());
	menuDTO.setPrice(menu.getPrice());
	menuDTO.setType(menu.getType());
	menuDTO.setStatus(menu.getStatus());

	return menuDTO;
    }

    public Menu convertDTOToEntity(MenuDTO menuDTO) {
	Menu menu = new Menu();

	menu.setMenuItemId(menuDTO.getId());
	menu.setName(menuDTO.getName());
	menu.setDescription(menuDTO.getDescription());
	menu.setImage(menuDTO.getImage());
	menu.setPrice(menuDTO.getPrice());
	menu.setType(menuDTO.getType());
	menu.setStatus(menuDTO.getStatus() != null ? menuDTO.getStatus() : ON_SELL.getStatus());

	return menu;
    }
}
