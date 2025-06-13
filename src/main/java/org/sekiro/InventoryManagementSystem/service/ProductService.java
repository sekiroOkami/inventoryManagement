package org.sekiro.InventoryManagementSystem.service;

import org.sekiro.InventoryManagementSystem.dto.ProductDTO;
import org.sekiro.InventoryManagementSystem.dto.Response;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    Response saveProduce(ProductDTO productDTO, MultipartFile imageFile) throws IOException;
    Response updateProduct(ProductDTO productDTO, MultipartFile imageFile) throws IOException;
    Response getAllProducts();
    Response getProductById(Long id);
    Response deleteProduct(Long id);
}
