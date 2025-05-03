package org.sekiro.InventoryManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO {

    private Long id;

    private Long productId;

    private Long categoryId;

    private Long supplierId;

    private String name;

    private String sku;

    private BigDecimal price;

    @Min(value = 0, message = "Stock quantity cannot less than zero")
    private Integer stockQuantity;

    private String description;

    private String imageUrl;

    private LocalDateTime expireDate;

    private LocalDateTime updatedAt ;

    private LocalDateTime createdAt ;
}


