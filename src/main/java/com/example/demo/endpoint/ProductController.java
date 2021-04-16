package com.example.demo.endpoint;

import com.example.demo.dto.ProductDto;
import com.example.demo.mappers.ProductMapper;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/allProducts")
    public List<Product> allProducts() {
        return productService.getAll();
    }

    @GetMapping(value = "/product/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
                                         produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto getProduct(@PathVariable("id") int id) {
        Product byId = productService.getById(id);
        return ProductMapper.INSTANCE.toDto(byId);
    }

    @PostMapping("/product/save")
    public Product saveProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @DeleteMapping("/delete/product/{id}")
    public void deleteProduct(@PathVariable("id") int id) {
        productService.delete(id);
    }

    @PutMapping("/product/update/{id}")
    public void updateProduct(@PathVariable("id") int id, @RequestBody Product product) {
        productService.updateProduct(id, product);
    }

}
