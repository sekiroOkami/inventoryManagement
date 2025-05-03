package org.sekiro.InventoryManagementSystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.sekiro.InventoryManagementSystem.enums.TransactionStatus;
import org.sekiro.InventoryManagementSystem.enums.TransactionType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 0, message = "Total products cannot be negative.")
    private Integer totalProducts;

    @NotNull(message = "Total price is required.")
    @DecimalMin(value = "0.0", inclusive = true, message = "Total price cannot be negative.")
    private BigDecimal totalPrice;

    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(value = EnumType.STRING)
    private TransactionStatus status;

    @Size(max = 255, message = "Description cannot exceed 255 characters.")
    private String description;

    @LastModifiedDate
    private LocalDateTime updatedAt ;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", totalProducts=" + totalProducts +
                ", totalPrice=" + totalPrice +
                ", transactionType=" + transactionType +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
