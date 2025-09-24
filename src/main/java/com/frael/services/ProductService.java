package com.frael.services;

import org.springframework.stereotype.Service;
import com.frael.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.frael.models.*;;

/**
 * Servicio Reactivo
 * 
 * @author Frael
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Registro de un {@link #Product}
     * 
     * @apiNote Devuelve un flujo Mono de tipo Product
     * @param product
     * @return Mono<Product>
    */
    public Mono<Product> save(Product product){
        return this.productRepository.save(product);
    }

    /**
     * Actualizacion del Producto
     * 
     * 
     * @param product
     * @param id
     * @return Mono<Product>
     */
    public Mono<Product> update(Product product, Long id){

        //Obtenemos el producto en un mono flujo
        Mono<Product> oldProduct = this.productRepository.findById(id);

        return oldProduct.flatMap( p -> {
            p.setName(product.getName());
            p.setDescription(product.getDescription());
            p.setPrice(product.getPrice());

          return productRepository.save(p);
        });
    }

    /**
     * Devuelve todos los Productos
     * 
     * @apiNote Devuelve multiples flujos de Productos
     * @return Flux<Producto>
     */
    public Flux<Product> findAll(){
        return this.productRepository.findAll();
    }
    
    /**
     * Encuentra un Producto por su Id
     * 
     * @param id
     * @return
     */
    public Mono<Product> findById(Long id){
        return this.productRepository.findById(id);
    }

    public Mono<Void> deleteById(Long id){
        return this.productRepository.deleteById(id);
    }
}
