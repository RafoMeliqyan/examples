package com.example.demo.service;

import com.example.demo.mappers.ProductMapper;
import com.example.demo.model.Product;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceTest {

    final Logger logger = LoggerFactory.getLogger(ProductServiceTest.class);

    @Autowired
    private ProductService productService;

    @Test
    void contextLoads() {
    }

    @Test
    void save_OK_MapTest() {
        Product product = new Product("ator","sdaasd", 1500);
        Product productTwo = new Product("sexan","sasddaasd", 2500);

        productService.save(product);
        productService.save(productTwo);

        ProductMapper.INSTANCE.toDto(product);
        ProductMapper.INSTANCE.toDto(productTwo);
    }

    @Test
    void getTest() {
        Product product = new Product(5,"ator","sdaasd", 1500);
        productService.save(product);
        getAndPrint(product.getId());
    }

    @Test
    void createOrReturnRefreshCachedTest() {
        Product product = productService.createOrReturnCached(new Product("ator","sdaasd", 1500));
        logger.info("created product1: {}", product);
        Product productTwo = productService.createOrReturnCached(new Product("ator","sdaasdd", 15700));
        logger.info("created product2: {}", productTwo);
        Product productTree = productService.createAndRefreshCache(new Product("ator","sdsasaasd", 4500));
        logger.info("created product3: {}", productTree);
        Product productFour = productService.createAndRefreshCache(new Product("ator","sdaasasaasd", 8500));
        logger.info("created product4: {}", productFour);
    }

    @Test
    void deleteEvictProductTest() {
        Product product = productService.save(new Product("ator", "fasfsd", 1700));
        Product productTwo = productService.save(new Product("sexan", "fasfsd", 1700));

        logger.info("{}", productService.getById(product.getId()));
        logger.info("{}", productService.getById(productTwo.getId()));

        productService.delete(product.getId());
        productService.deleteAndEvict(productTwo.getId());

        logger.info("{}", productService.getById(product.getId()));
        logger.info("{}", productService.getById(productTwo.getId()));
    }

    private void getAndPrint(int id) {
        logger.info("user found: {}", productService.getById(id));
    }

}
