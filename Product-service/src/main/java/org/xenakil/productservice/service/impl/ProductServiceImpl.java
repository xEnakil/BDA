package org.xenakil.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.xenakil.productservice.dto.response.ProductResponse;
import org.xenakil.productservice.dto.request.ProductRequest;
import org.xenakil.productservice.mapper.ProductMapper;
import org.xenakil.productservice.model.Product;
import org.xenakil.productservice.repository.ProductRepository;
import org.xenakil.productservice.service.ProductService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse addProduct(ProductRequest productRequest) {
        Product product = productMapper.productRequestToProduct(productRequest);
        productRepository.save(product);
        return productMapper.productToProductResponse(product);
    }

    @Override
    public ProductResponse updateProduct(String id ,ProductRequest productRequest) {
        Product product = productRepository.findById(id);
        if (product != null) {
            product = productMapper.productRequestToProduct(productRequest);
            return productMapper.productToProductResponse(productRepository.save(product));
        }
        return null;
    }

    @Override
    public void deleteProduct(ProductRequest productRequest) {
        productRepository.delete(productMapper.productRequestToProduct(productRequest));
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::productToProductResponse)
                .toList();
    }
}
