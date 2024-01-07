package com.demo.restaurant.models.entities;

import com.demo.restaurant.common.enums.BillStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID billId;

    @OneToMany(mappedBy = "bill", fetch = FetchType.EAGER)
    private Set<BillDetail> billDetails;

    @Column(name = "ordered_time", nullable = false)
    private LocalDateTime orderedTime;

    @Column(name = "status", nullable = false)
    private String status = BillStatus.UNPAID.getStatus();

    public Bill(LocalDateTime orderedTime) {
	this.orderedTime = orderedTime;
    }

    public Bill(Set<BillDetail> billDetails, LocalDateTime orderedTime) {
        this.billDetails = billDetails;
        this.orderedTime = orderedTime;
    }
}
