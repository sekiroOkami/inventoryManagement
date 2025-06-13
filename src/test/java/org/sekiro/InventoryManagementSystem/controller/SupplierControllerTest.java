package org.sekiro.InventoryManagementSystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sekiro.InventoryManagementSystem.dto.Response;
import org.sekiro.InventoryManagementSystem.dto.SupplierDTO;
import org.sekiro.InventoryManagementSystem.service.SupplierService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SupplierControllerTest {

//    @Mock
//    private SupplierService supplierService;
//
//    @InjectMocks
//    private SupplierController supplierController;
//
//    private MockMvc mockMvc;
//    private ObjectMapper objectMapper;
//    private SupplierDTO supplierDTO;
//    private Response response;
//
//    @BeforeEach
//    void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(supplierController).build();
//        objectMapper = new ObjectMapper();
//
//        supplierDTO = new SupplierDTO();
//        supplierDTO.setId(1L);
//        supplierDTO.setName("Test Supplier");
//        supplierDTO.setContactInfo("test@supplier.com");
//
//        response = new Response();
//        response.setSuccess(true);
//        response.setData(supplierDTO);
//    }
//
//    @Test
//    void getAllSuppliers_ReturnsListOfSuppliers() throws Exception {
//        List<SupplierDTO> suppliers = Arrays.asList(supplierDTO);
//        Response response = new Response();
//        response.setSuccess(true);
//        response.setData(suppliers);
//
//        when(supplierService.getAllSuppliers()).thenReturn(response);
//
//        mockMvc.perform(get("/api/supplier/all")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.data[0].id").value(1L))
//                .andExpect(jsonPath("$.data[0].name").value("Test Supplier"));
//
//        verify(supplierService).getAllSuppliers();
//    }
//
//    @Test
//    void getSupplierById_ValidId_ReturnsSupplier() throws Exception {
//        when(supplierService.getSupplierById(1L)).thenReturn(response);
//
//        mockMvc.perform(get("/api/supplier/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.data.id").value(1L))
//                .andExpect(jsonPath("$.data.name").value("Test Supplier"));
//
//        verify(supplierService).getSupplierById(1L);
//    }
//
//    @Test
//    @WithMockUser(authorities = {"ADMIN"})
//    void addSupplier_ValidSupplierDTO_ReturnsCreatedSupplier() throws Exception {
//        when(supplierService.addSupplier(any(SupplierDTO.class))).thenReturn(response);
//
//        mockMvc.perform(post("/api/supplier/add")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(supplierDTO)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.data.id").value(1L))
//                .andExpect(jsonPath("$.data.name").value("Test Supplier"));
//
//        verify(supplierService).addSupplier(any(SupplierDTO.class));
//    }
//
//    @Test
//    @WithMockUser(authorities = {"ADMIN"})
//    void updateSupplier_ValidIdAndDTO_ReturnsUpdatedSupplier() throws Exception {
//        when(supplierService.updateSupplier(eq(1L), any(SupplierDTO.class))).thenReturn(response);
//
//        mockMvc.perform(put("/api/supplier/update/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(supplierDTO)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.data.id").value(1L))
//                .andExpect(jsonPath("$.data.name").value("Test Supplier"));
//
//        verify(supplierService).updateSupplier(eq(1L), any(SupplierDTO.class));
//    }
//
//    @Test
//    @WithMockUser(authorities = {"ADMIN"})
//    void deleteSupplier_ValidId_ReturnsSuccess() throws Exception {
//        when(supplierService.deleteSupplier(1L)).thenReturn(response);
//
//        mockMvc.perform(delete("/api/supplier/delete/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.success").value(true));
//
//        verify(supplierService).deleteSupplier(1L);
//    }
//
//    @Test
//    void addSupplier_Unauthorized_ReturnsForbidden() throws Exception {
//        mockMvc.perform(post("/api/supplier/add")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(supplierDTO)))
//                .andExpect(status().isForbidden());
//
//        verify(supplierService, never()).addSupplier(any(SupplierDTO.class));
//    }


}