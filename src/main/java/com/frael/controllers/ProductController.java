package com.frael.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frael.models.Product;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Controlador Reactivo
 * 
 * @author Frael
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api-reactive/products")
public class ProductController {

    @PostMapping
    public ResponseEntity<Mono<Product>> create(@RequestBody Product product){
        return null;
    }

}
