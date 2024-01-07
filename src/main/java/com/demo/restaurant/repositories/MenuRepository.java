package com.demo.restaurant.repositories;

import com.demo.restaurant.models.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MenuRepository extends JpaRepository<Menu, UUID>, JpaSpecificationExecutor<Menu> {
    boolean existsProductByName(String name);
    Optional<Menu> findProductByName(String name);


}
