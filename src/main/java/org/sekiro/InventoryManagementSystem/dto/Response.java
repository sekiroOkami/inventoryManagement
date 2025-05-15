package org.sekiro.InventoryManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.sekiro.InventoryManagementSystem.enums.UserRole;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    // generic
    private int status;
    private String message;

    // for login
    private String token;
    private UserRole role;
    private String expireTime;

    // for pagination
    private Integer totalPages;
    private Long totalElement;

    // data output optional
    private UserDTO user;
    private List<UserDTO> users;

    private TransactionDTO transactionDTO;
    private List<TransactionDTO> transactionDTOS;

    private SupplierDTO supplierDTO;
    private List<SupplierDTO> supplierDTOS;

    private CategoryDTO categoryDTO;
    private List<CategoryDTO> categoryDTOS;

    private ProductDTO productDTO;
    private List<ProductDTO> productDTOS;

    private final LocalDateTime timestamp = LocalDateTime.now();

}
