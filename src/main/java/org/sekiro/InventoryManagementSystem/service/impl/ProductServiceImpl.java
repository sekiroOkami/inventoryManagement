package org.sekiro.InventoryManagementSystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.sekiro.InventoryManagementSystem.dto.ProductDTO;
import org.sekiro.InventoryManagementSystem.dto.Response;
import org.sekiro.InventoryManagementSystem.entities.Category;
import org.sekiro.InventoryManagementSystem.entities.Product;
import org.sekiro.InventoryManagementSystem.exceptions.NotFoundException;
import org.sekiro.InventoryManagementSystem.repository.CategoryRepository;
import org.sekiro.InventoryManagementSystem.repository.ProductRepository;
import org.sekiro.InventoryManagementSystem.service.ProductService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;
import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    private static final String IMAGE_DIRECTOR_FRONTEND = "C:/Users/jonwi/Desktop/Education Folder/Java/InventoryManagementSystem/InventoryManagementSystem/product-image";
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Response saveProduce(ProductDTO productDTO, MultipartFile imageFile) throws IOException {
        Objects.requireNonNull(imageFile);
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(()-> new NotFoundException("Category Not found"));

        // map out product dto to product entity
        Product productToSave = Product.builder()
                .name(productDTO.getName())
                .sku(productDTO.getSku())
                .price(productDTO.getPrice())
                .stockQuantity(productDTO.getStockQuantity())
                .description(productDTO.getDescription())
                .category(category)
                .build();

        String imagePath = saveImage(imageFile);
        productToSave.setImageUrl(imagePath);

        productRepository.save(productToSave);

        return Response.builder()
                .status(200)
                .message("Product successfully saved")
                .build();
    }

    @Override
    public Response updateProduct(ProductDTO productDTO, MultipartFile imageFile)  {

        Product existingProduct = productRepository.findById(productDTO.getProductId())
                .orElseThrow(() -> new NotFoundException("Product Not Found"));

        // check if image is associated with the update request
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = null;
            try {
                imagePath = saveImageToFrontendPublicFolder(imageFile);
            } catch (IOException e) {
                throw new RuntimeException("Error during saveImage to Folder");
            }
            existingProduct.setImageUrl(imagePath);
        }

        // check if category is to be changed for the product
        if (productDTO.getProductId() != null && productDTO.getProductId() > 0) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(()-> new NotFoundException("Category Not found"));
            existingProduct.setCategory(category);
        }

        // check and update fields
        if (productDTO.getName() != null && !productDTO.getName().isBlank()) {
            existingProduct.setName(productDTO.getName());
        }

        if (productDTO.getSku() !=null && !productDTO.getSku().isBlank()){
            existingProduct.setSku(productDTO.getSku());
        }

        if (productDTO.getDescription() !=null && !productDTO.getDescription().isBlank()){
            existingProduct.setDescription(productDTO.getDescription());
        }

        if (productDTO.getPrice() !=null && productDTO.getPrice().compareTo(BigDecimal.ZERO) >=0){
            existingProduct.setPrice(productDTO.getPrice());
        }

        if (productDTO.getStockQuantity() !=null && productDTO.getStockQuantity() >=0){
            existingProduct.setStockQuantity(productDTO.getStockQuantity());
        }

        // Update the product
        productRepository.save(existingProduct);
        return Response.builder()
                .status(200)
                .message("Product successfully Updated")
                .build();
    }

    @Override
    public Response getAllProducts() {
        var products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<ProductDTO> productDTOS = modelMapper.map(products, new TypeToken<List<ProductDTO>>(){}.getType());
        return Response.builder()
                .status(200)
                .message("success")
                .productDTOS(productDTOS)
                .build();
    }

    @Override
    public Response getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product Not Found"));

        return Response.builder()
                .status(200)
                .message("success")
                .productDTO(modelMapper.map(product, ProductDTO.class))
                .build();
    }

    @Override
    public Response deleteProduct(Long id) {
        productRepository.deleteById(id);
        return Response.builder()
                .status(200)
                .message("Product successfully deleted")
                .build();
    }

    private String saveImageToFrontendPublicFolder(MultipartFile imageFile) throws IOException {
        //validate image check
        if (!imageFile.getContentType().startsWith("image/")){
            throw new IllegalArgumentException("Only image files are allowed");
        }
        //create the directory to store images if it doesn't exist
        File directory = new File(IMAGE_DIRECTOR_FRONTEND);

        if (!directory.exists()){
            directory.mkdir();
            log.info("Directory was created");
        }
        //generate unique file name for the image
        String uniqueFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        //get the absolute path of the image
        String imagePath = IMAGE_DIRECTOR_FRONTEND + uniqueFileName;

        try {
            File desctinationFile = new File(imagePath);
            imageFile.transferTo(desctinationFile); //we are transfering(writing to this folder)

        }catch (Exception e){
            throw new IllegalArgumentException("Error occurend while saving image" + e.getMessage());
        }

        return "products/"+uniqueFileName;
    }

    private String saveImage(MultipartFile imageFile) throws IOException {
        Objects.requireNonNull(imageFile);

        // validate image
        if (!imageFile.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }

        // Define the directory path
        Path directory = Paths.get(IMAGE_DIRECTOR_FRONTEND);

        // Create the directory if it doesn't exist
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
            log.info("Directory created: {}", directory);
        }

        // Generate unique file name
        String originalFilename = imageFile.getOriginalFilename();
        if (originalFilename == null || originalFilename.contains("..")) {
            throw new IllegalArgumentException("Invalid file name");
        }

        String uniqueFileName = UUID.randomUUID() + "_" + originalFilename;

        // construct the absolute path
        Path imagePath = directory.resolve(uniqueFileName);

        // save the image
        try {
            Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
            log.info("Image save successfully: {}", imagePath);
        } catch (IOException e) {
            throw new IOException("Error occurred while saving image: " + e.getMessage(), e);
        }

        return imagePath.toString();
    }
}
