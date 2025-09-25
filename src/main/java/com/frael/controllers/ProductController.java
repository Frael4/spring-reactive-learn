package com.frael.controllers;

import java.time.Duration;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frael.models.Product;
import com.frael.services.ProductService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Controlador Reactivo
 * 
 * @author Frael
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api-reactive/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Mono<Product>> create(@RequestBody Product product) {
        Mono<Product> saved = this.productService.save(product);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Mono<Product>> update(@PathVariable Long id, Product product) {
        Mono<Product> updated = productService.update(product, id);
        return ResponseEntity.status(200).body(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<Product>> findById(@PathVariable Long id) {
        Mono<Product> product = productService.findById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
    
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<Product>> findAll() {
        Flux<Product> products = this.productService.findAll().delayElements(Duration.ofMillis(500));

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Mono<Void>> deleteById(Long id){
        Mono<Void> deleted = productService.deleteById(id);

        return new ResponseEntity<>(deleted, HttpStatus.ACCEPTED);
    }
    

}
