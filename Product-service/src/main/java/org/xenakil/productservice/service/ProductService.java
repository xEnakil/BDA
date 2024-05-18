package org.xenakil.productservice.service;

import org.xenakil.productservice.dto.response.ProductResponse;
import org.xenakil.productservice.dto.request.ProductRequest;

import java.util.List;

public interface ProductService {

    ProductResponse addProduct(ProductRequest productRequest);
    ProductResponse updateProduct(String id ,ProductRequest productRequest);
    void deleteProduct(ProductRequest productRequest);
    List<ProductResponse> getAllProducts();
}
