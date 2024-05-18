package org.xenakil.productservice.mapper;

import org.mapstruct.Mapper;
import org.xenakil.productservice.dto.request.ProductRequest;
import org.xenakil.productservice.dto.response.ProductResponse;
import org.xenakil.productservice.model.Product;

@Mapper
public interface ProductMapper {
    ProductResponse productToProductResponse(Product product);
    ProductRequest productToProductRequest(Product product);
    Product productRequestToProduct(ProductRequest productRequest);
    Product productResponseToProduct(ProductResponse productResponse);
}
