package com.ecommerce.EcommerceApp.repo;

import com.ecommerce.EcommerceApp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,Integer> {
    
}
