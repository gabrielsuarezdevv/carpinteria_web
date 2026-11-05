package com.suarez.carpinteria_api.product;

import com.suarez.carpinteria_api.product.dto.ProductRequest;
import com.suarez.carpinteria_api.product.dto.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// El controlador de productos es responsable de manejar las solicitudes HTTP relacionadas con los productos.
// Define los endpoints de la API para crear, actualizar, eliminar y recuperar productos.
@RestController
@RequestMapping("/api/products")
public class ProductController {

    // Inyecta el servicio de productos para manejar la lógica de negocio relacionada con los productos.
    private final ProductService productService;

    // Constructor que recibe el servicio de productos como dependencia.
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Endpoint para obtener todos los productos.
    @GetMapping
    public List<ProductResponse> findAll() {
        return productService.findAll();
    }

    // Endpoint para obtener un producto por su ID.
    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    // Endpoint para crear un nuevo producto.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@Valid @RequestBody ProductRequest request) {
        return productService.create(request);
    }

    // Endpoint para actualizar un producto existente.
    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        return productService.update(id, request);
    }

    // Endpoint para eliminar un producto por su ID.
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}