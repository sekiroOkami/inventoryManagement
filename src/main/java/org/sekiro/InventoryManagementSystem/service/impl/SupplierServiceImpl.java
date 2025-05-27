package org.sekiro.InventoryManagementSystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.sekiro.InventoryManagementSystem.dto.CategoryDTO;
import org.sekiro.InventoryManagementSystem.dto.Response;
import org.sekiro.InventoryManagementSystem.dto.SupplierDTO;
import org.sekiro.InventoryManagementSystem.entities.Category;
import org.sekiro.InventoryManagementSystem.entities.Supplier;
import org.sekiro.InventoryManagementSystem.exceptions.NotFoundException;
import org.sekiro.InventoryManagementSystem.repository.CategoryRepository;
import org.sekiro.InventoryManagementSystem.repository.SupplierRepository;
import org.sekiro.InventoryManagementSystem.service.SupplierService;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    public SupplierServiceImpl(SupplierRepository categoryRepository, ModelMapper modelMapper) {
        this.supplierRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Response addSupplier(SupplierDTO supplierDTO) {
        Supplier supplierToSave = modelMapper.map(supplierDTO, Supplier.class);
        supplierRepository.save(supplierToSave);

        return Response.builder()
                .status(200)
                .message("Supplier added successfully.")
                .build();
    }

    @Override
    public Response updateSupplier(Long id, SupplierDTO supplierDTO) {
        var existingSupplier = supplierRepository.findById(id).orElseThrow(()-> new NotFoundException("Supplier Not Found"));
        if (supplierDTO.getName() != null) existingSupplier.setName(supplierDTO.getName());
        if (supplierDTO.getAddress() != null) existingSupplier.setAddress(supplierDTO.getAddress());

        supplierRepository.save(existingSupplier);

        return Response.builder()
                .status(200)
                .message("Supplier Successfully Updated")
                .build();
    }

    @Override
    public Response getAllSuppliers() {

        List<Supplier> categories = supplierRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<SupplierDTO> supplierDTOS = modelMapper.map(categories, new TypeToken<List<SupplierDTO>>(){}.getType());

        return Response.builder()
                .status(200)
                .message("success")
                .supplierDTOS(supplierDTOS)
                .build();
    }

    @Override
    public Response getSupplierById(Long id) {
        var supplier = supplierRepository.findById(id).orElseThrow(() -> new NotFoundException("Supplier not found"));
        var supplierDTO = modelMapper.map(supplier, SupplierDTO.class);
        return Response.builder()
                .status(200)
                .message("success")
                .supplierDTO(supplierDTO)
                .build();
    }

    @Override
    public Response deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
        return Response.builder()
                .status(200)
                .message("Supplier Successfully Deleted")
                .build();
    }
}
