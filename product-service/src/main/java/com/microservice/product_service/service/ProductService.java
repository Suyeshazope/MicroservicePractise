package com.microservice.product_service.service;

import com.microservice.product_service.dto.ProductDTO;
import com.microservice.product_service.dto.ProductResponseDTO;
import com.microservice.product_service.model.Product;
import com.microservice.product_service.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRespository ;

    public ProductService(ProductRepository productRespository) {
        this.productRespository = productRespository;
    }

    public ProductResponseDTO createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrize(productDTO.getPrize());
        product.setSkuCode(productDTO.getSkuCode());

        productRespository.save(product);
        return new ProductResponseDTO(product.getId() , product.getName() , product.getDescription() , product.getPrize() , product.getSkuCode()) ;
    }

    public List<ProductResponseDTO> getAllProducts(){
        List<Product> products = productRespository.findAll() ;
        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponseDTO mapToProductResponse(Product product) {
        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setId(product.getId());
        responseDTO.setName(product.getName());
        responseDTO.setDescription(product.getDescription());
        responseDTO.setPrize(product.getPrize());
        responseDTO.setSkuCode(product.getSkuCode());
        return responseDTO;
    }
}
