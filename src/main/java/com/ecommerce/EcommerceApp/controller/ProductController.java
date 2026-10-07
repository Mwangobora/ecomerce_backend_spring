package com.ecommerce.EcommerceApp.controller;

import com.ecommerce.EcommerceApp.model.Product;
import com.ecommerce.EcommerceApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final model.Product product_1;
    private final model.Product product;
    @Autowired
    private ProductService service;

    ProductController(model.Product product, model.Product product_1) {
        this.product = product;
        this.product_1 = product_1;
    }

     public String greet(){
         return("hello world");
     }

     @GetMapping("/products")
     public List<Product> getAllProducts(){
         return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK).getBody();
     }
     @GetMapping("product/{id}")
     public ResponseEntity<Product> getProduct(@PathVariable int Id){
         Product product = service.getProduct(Id);

         if(product != null)
             return  new ResponseEntity<>(product, HttpStatus.OK);
         else
             return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }

     @PostMapping("/product")
     public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile  imageFile)
     {
        try {
            Product savedProduct = service.addProduct(product, imageFile);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (Exception e) {
             return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
       
     }

   @GetMapping("/product/{productId}/image")
    public  ResponseEntity<byte[]> getImagebyProductId(@PathVariable int productId) throws Exception {
         try {
             Product product =  service.getProduct(productId);
             byte[] imageFile = product.getImageData();

             return  ResponseEntity.ok().
                     contentType(MediaType.valueOf(product.getImageType()))
                     .body(imageFile);

         }
         catch (Exception e)
         {
             throw new Exception("failed to get the image");
         }
   }

   @PutMapping("/product/{id}")
    public  ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product,
                                                 @RequestPart MultipartFile imageFile){
         Product product1 = service.updateProduct(id,product, imageFile);
         if(product1 != null){
             return new ResponseEntity<>("Updated", HttpStatus.OK);
         }
         return new ResponseEntity<>("Failed to update product",HttpStatus.BAD_REQUEST);
   }

   @DeleteMapping("product/{id}")
    public  ResponseEntity<String> deleteProduct(@PathVariable int id){
         try {
             Product product = service.deleteProduct(id);
             if(product != null)
                return new ResponseEntity<>("Deleted successifully", HttpStatus.OK);
             else
                return  new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
              
         } catch (Exception e) {
             throw new RuntimeException(e);
         }
   }
}
