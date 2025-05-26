package com.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.product.dto.ProductDTO;
import com.product.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDto) {
		ProductDTO product = productService.createProduct(productDto);
		return new ResponseEntity<>(product, HttpStatus.CREATED);
	}

	@PutMapping("/update/{productId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDto,
			@PathVariable(name = "productId") Long productId) {
		ProductDTO updateProduct = productService.updateProduct(productDto, productId);
		return new ResponseEntity<>(updateProduct, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{productId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteProduct(@PathVariable(name = "productId") Long productId) {
		productService.deleteProduct(productId);
		return new ResponseEntity<>("Product deleted", HttpStatus.OK);
	}

	@GetMapping("/get/{productId}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable(name = "productId") Long productId) {
		ProductDTO product = productService.getProduct(productId);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<List<ProductDTO>> getAllProducts(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "productName", required = false) String sortBy,
			@RequestParam(value = "sortOrder", defaultValue = "asc", required = false) String sortOrder) {

		List<ProductDTO> products = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@PutMapping("/update-stock")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<ProductDTO> updateProductStock(@RequestParam Long productId, @RequestParam int stock) {
		ProductDTO updateProduct = productService.updateProductStock(productId, stock);
		return new ResponseEntity<>(updateProduct, HttpStatus.OK);
	}
}
