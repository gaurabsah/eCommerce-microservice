package com.product.service;

import java.util.List;

import com.product.dto.ProductDTO;

public interface ProductService {
	
	ProductDTO createProduct(ProductDTO productDto);

	ProductDTO updateProduct(ProductDTO productDto, Long productId);

    void deleteProduct(Long productId);

    ProductDTO getProduct(Long productId);

    List<ProductDTO> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortOrder);


}
