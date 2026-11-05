package com.suarez.carpinteria_api.product;

import org.springframework.data.jpa.repository.JpaRepository;

// El repositorio de productos es responsable de interactuar con la base de datos para realizar operaciones CRUD 
// (Crear, Leer, Actualizar, Eliminar) en la entidad Product.
public interface ProductRepository extends JpaRepository<Product, Long> {
}