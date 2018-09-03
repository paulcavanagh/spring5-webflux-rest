package com.parc.spring5webfluxrest.controller;

import com.parc.spring5webfluxrest.domain.Category;
import com.parc.spring5webfluxrest.repositories.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CategoryController {

    public static final String API_V_1_CATEGORIES = "/api/v1/categories/";
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping(API_V_1_CATEGORIES)
    Flux<Category> list(){
        return categoryRepository.findAll();
    }

    @GetMapping(API_V_1_CATEGORIES +"{id}")
    Mono<Category> getById(@PathVariable String id){
        return  categoryRepository.findById(id);
    }

    @PostMapping(API_V_1_CATEGORIES)
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> createNewCategory(@RequestBody Publisher<Category> categoryStream){
        return categoryRepository.saveAll(categoryStream).then();
    }


    @PutMapping(API_V_1_CATEGORIES +"{id}")
    Mono<Category> updateCategory(@PathVariable String id, @RequestBody Category category){
        category.setId(id);
        return categoryRepository.save(category);
    }

    @PatchMapping(API_V_1_CATEGORIES +"{id}")
    Mono<Category> patchCategory(@PathVariable String id, @RequestBody Category category){

        Category foundCategory = categoryRepository.findById(id).block();

        if(foundCategory.getDescription() != category.getDescription()) {
            foundCategory.setDescription(category.getDescription());
            return categoryRepository.save(foundCategory);
        }

        return Mono.just(foundCategory);
    }


}
