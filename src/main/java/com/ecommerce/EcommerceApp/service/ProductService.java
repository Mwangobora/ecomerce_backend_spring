package com.ecommerce.EcommerceApp.service;

import com.ecommerce.EcommerceApp.model.Product;
import com.ecommerce.EcommerceApp.repo.ProductRepo;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

import com.sun.jdi.VoidValue;

@Service
public class ProductService {
    private final ProductRepo repo;

    public ProductService(ProductRepo repo) {
        this.repo = repo;
    }

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product getProduct(int Id){
        return repo.getById(Id);
    }

    public Product addProduct(Product product, MultipartFile imageFile) {
        try {
            product.setImageName(imageFile.getOriginalFilename());
            product.setImageType(imageFile.getContentType());
            product.setImageData(imageFile.getBytes());
            repo.save(product);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return product;
    }


    public Product updateProduct(int id, Product product, MultipartFile imageFile) {
        try {
            product.setImageData(imageFile.getBytes());
            product.setImageName(imageFile.getOriginalFilename());
            product.setImageType(imageFile.getContentType());

            return  repo.save(product);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteProduct(int id) {
        try {
          repo.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }
}