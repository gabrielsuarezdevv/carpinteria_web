package com.suarez.carpinteria_api.product;

import com.suarez.carpinteria_api.common.ResourceNotFoundException;
import com.suarez.carpinteria_api.product.dto.ProductRequest;
import com.suarez.carpinteria_api.product.dto.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// El servicio de productos es responsable de manejar la lógica de negocio relacionada con los productos.
// Proporciona métodos para crear, actualizar, eliminar y recuperar productos desde la base de datos a través 
// del repositorio de productos. Además, convierte las entidades de producto en objetos de respuesta que se pueden enviar al cliente.
@Service
public class ProductService {

    // Inyecta el repositorio de productos para interactuar con la base de datos.
    private final ProductRepository productRepository;

    // Constructor que recibe el repositorio de productos como dependencia.
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Método para obtener todos los productos. Marca la transacción como de solo lectura para optimizar el rendimiento.
    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream().map(this::toResponse).toList();
    }

    // Método para obtener un producto por su ID. Marca la transacción como de solo lectura para optimizar el rendimiento.
    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        return toResponse(getProductOrThrow(id));
    }

    // Método para crear un nuevo producto. Marca la transacción como de escritura para permitir la persistencia de datos.
    @Transactional
    public ProductResponse create(ProductRequest request) {
        Product product = new Product(request.name(), request.description(), request.stock(), request.price());
        return toResponse(productRepository.save(product));
    }

    // Método para actualizar un producto existente. Marca la transacción como de escritura para permitir la persistencia de datos.
    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = getProductOrThrow(id);
        product.update(request.name(), request.description(), request.stock(), request.price());
        return toResponse(product);
    }

    // Método para eliminar un producto por su ID. Marca la transacción como de escritura para permitir la persistencia de datos.
    @Transactional
    public void delete(Long id) {
        productRepository.delete(getProductOrThrow(id));
    }

    // Método privado para obtener un producto por su ID o lanzar una excepción si no se encuentra.
    private Product getProductOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
    }

    // Método privado para convertir una entidad de producto en un objeto de respuesta.
    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getStock(),
                product.getPrice()
        );
    }
}
