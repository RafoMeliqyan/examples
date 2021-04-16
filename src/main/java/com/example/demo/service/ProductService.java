package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {

    final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Cacheable(value = "products", key = "#product.name")
    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void updateProduct(int id, Product product) {
        Product byId = productRepository.getOne(id);

        byId.setName(product.getName());
        byId.setDescription(product.getDescription());
        byId.setPrice(product.getPrice());

        productRepository.save(byId);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Cacheable("products")
    public Product getById(int id) {
        return productRepository.getOne(id);
    }

    @Cacheable(value = "products", key = "#product.name")
    public Product createOrReturnCached(Product product) {
        logger.info("creating product: {}", product);
        return productRepository.save(product);
    }

    @CachePut(value = "products", key = "#product.name")
    public Product createAndRefreshCache(Product product) {
        logger.info("creating product: {}", product);
        return productRepository.save(product);
    }

    public void delete(int id) {
        logger.info("deleting product by id: {}", id);
        productRepository.deleteById(id);
    }

    @CacheEvict("products")
    public void deleteAndEvict(int id) {
        logger.info("deleting product by id: {}", id);
        productRepository.deleteById(id);
    }

}
