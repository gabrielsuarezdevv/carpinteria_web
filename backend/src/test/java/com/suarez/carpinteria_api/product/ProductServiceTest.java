package com.suarez.carpinteria_api.product;

import com.suarez.carpinteria_api.common.ResourceNotFoundException;
import com.suarez.carpinteria_api.product.dto.ProductResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

// Clase de prueba para ProductService. Utiliza Mockito para simular el comportamiento del ProductRepository y probar la lógica de negocio del servicio de productos.
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    // Simula el repositorio de productos para evitar interacciones reales con la base de datos durante las pruebas.
    @Mock
    private ProductRepository productRepository;

    // Inyecta una instancia de ProductService con el ProductRepository simulado para probar la lógica de negocio del servicio.
    @InjectMocks
    private ProductService productService;

    // Prueba unitaria para el método findById de ProductService. Verifica que cuando se busca un producto existente, se devuelve la respuesta correcta.
    @Test
    void findById_whenProductExists_returnsResponse() {
        // Arrange: Configura el comportamiento simulado del ProductRepository para devolver un producto específico cuando se busca por ID.
        Product product = new Product("Martillo", "Mango de fresno", 15, new BigDecimal("18.50"));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act: Llama al método findById del ProductService con el ID del producto simulado.
        ProductResponse response = productService.findById(1L);

        // Assert: Verifica que la respuesta devuelta por el servicio tenga los valores esperados.
        assertThat(response.name()).isEqualTo("Martillo");
        assertThat(response.stock()).isEqualTo(15);
    }

    @Test
    void findAll_returnsAllProducts() {
        // Arrange: crear dos productos y hacer que el mock devuelva List.of(...)
        Product product1 = new Product("Martillo", "Mango de fresno", 15, new BigDecimal("18.50"));
        Product product2 = new Product("Sierra", "Hoja de acero", 10, new BigDecimal("25.00"));
        when(productRepository.findAll()).thenReturn(List.of(product1, product2));

        // Act: llamar a productService.findAll()
        List<ProductResponse> responses = productService.findAll();

        // Assert: compruobar que la lista tiene 2 elementos
        assertThat(responses).hasSize(2);
    }

    @Test
    void findById_whenProductDoesNotExist_throwsException() {
        // Arrange: hacer que el mock devuelva Optional.empty()
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert: llama a productService.findById(99L) y verifica que lanza ResourceNotFoundException
        assertThatThrownBy(() -> productService.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}