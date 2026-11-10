package com.suarez.carpinteria_api.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.suarez.carpinteria_api.common.ResourceNotFoundException;
import com.suarez.carpinteria_api.product.dto.ProductRequest;
import com.suarez.carpinteria_api.product.dto.ProductResponse;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    // Simula peticiones HTTP al controller sin levantar un servidor
    @Autowired 
    private MockMvc mockMvc;

    // Sustituye al servicio real: el controller no ejecuta lógica ni toca la base de datos
    @MockitoBean
    private ProductService productService;

    @Test
    void create_whenNameIsBlank_returns400() throws Exception {
        String body = """
                {
                  "name": "",
                  "description": "Invalid product",
                  "stock": 5,
                  "price": 10.00
                }
                """;

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors.name").exists());
    }

    @Test
    void create_whenPriceIsNull_returns400() throws Exception {
        String body = """
                {
                  "name": "Invalid Product",
                  "description": "Invalid product",
                  "stock": 5,
                  "price": null
                }
                """;
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors.price").exists());
    }

    @Test
    void findById_whenProductDoesNotExist_returns404() throws Exception {
        // Arrange: Configura el comportamiento simulado del ProductService para lanzar
        // ResourceNotFoundException cuando se busca un producto inexistente.
        when(productService.findById(99L)).thenThrow(new ResourceNotFoundException("Product", 99L));

        // Act & Assert: Realiza una solicitud GET al endpoint y verifica que devuelve
        // un estado 404 y el mensaje de error esperado.
        mockMvc.perform(get("/api/products/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Product not found with id 99"));
    }

    @Test
    void findAllProducts_returns200() throws Exception {
        // Arrange: Configura el comportamiento simulado del ProductService para
        // devolver una lista vacía.
        when(productService.findAll()).thenReturn(List.of(
                new ProductResponse(1L, "Martillo", "Mango de fresno", 15, new BigDecimal("18.50"))));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Martillo"))
                .andExpect(jsonPath("$[0].stock").value(15));
    }

    @Test
    void create_whenRequestIsValid_returns201() throws Exception {
        // Arrange
        String body = """
                {
                  "name": "Alicate Medieval",
                  "description": "Alicate de hierro apto para forja",
                  "stock": 5,
                  "price": 21.99
                }
                """;
        when(productService.create(any(ProductRequest.class)))
                .thenReturn(new ProductResponse(1L, "Alicate Medieval", "Alicate de hierro apto para forja", 5,
                        new BigDecimal("21.99")));

        // Act + Assert: respuesta HTTP
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Alicate Medieval"))
                .andExpect(jsonPath("$.stock").value(5));

        // Assert: lo que recibió el servicio
        ArgumentCaptor<ProductRequest> captor = ArgumentCaptor.forClass(ProductRequest.class);
        verify(productService).create(captor.capture());
        assertThat(captor.getValue().name()).isEqualTo("Alicate Medieval");
        assertThat(captor.getValue().stock()).isEqualTo(5);
    }
}