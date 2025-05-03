package org.sekiro.InventoryManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sekiro.InventoryManagementSystem.enums.TransactionStatus;
import org.sekiro.InventoryManagementSystem.enums.TransactionType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDTO {

    private Long id;

    @Min(value = 0, message = "Total products cannot be negative.")
    private Integer totalProducts;

    @NotNull(message = "Total price is required.")
    @DecimalMin(value = "0.0", inclusive = true, message = "Total price cannot be negative.")
    private BigDecimal totalPrice;

    private TransactionType transactionType;

    private TransactionStatus status;

    @Size(max = 255, message = "Description cannot exceed 255 characters.")
    private String description;

    private LocalDateTime updatedAt ;

    private LocalDateTime createdAt ;

    private UserDTO users;

    private ProductDTO product;

    private SupplierDTO supplier;

}
