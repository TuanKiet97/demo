package com.demo.restaurant.repositories;

import com.demo.restaurant.models.entities.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, UUID> {

    boolean existsByBillBillId(UUID billId);

    Set<BillDetail> findByBillBillId(UUID billId);

    int countByMenuItemMenuItemId(UUID menuItemId);
}

