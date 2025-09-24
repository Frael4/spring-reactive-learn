package com.frael.repository;

import org.springframework.stereotype.Repository;
import com.frael.models.Product;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

/**
 * Repositorio Product
 * @apiNote Interfaz que extiende de la interface reactive repository
 * 
 * @author Frael
 */
@Repository
public interface ProductRepository extends R2dbcRepository<Product, Long> {

}
