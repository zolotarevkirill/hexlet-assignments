package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Sort;

import java.util.List;

import exercise.model.Product;
import exercise.repository.ProductRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getProducts(@RequestParam(required = false) Integer min,
                                     @RequestParam(required = false) Integer max,
                                     @RequestParam(required = false, defaultValue = "ASC") String order) {
        Sort sort = Sort.by(order.equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC, "price");
        if (min != null && max != null) {
            return productRepository.findAllByPriceBetween(min, max, sort);
        } else if (min != null) {
            return productRepository.findAllByPriceGreaterThanEqual(min, sort);
        } else if (max != null) {
            return productRepository.findAllByPriceLessThanEqual(max, sort);
        } else {
            return productRepository.findAll(sort);
        }
    }



    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {

        var product =  productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return product;
    }
}
