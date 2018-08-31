package com.parc.spring5webfluxrest.controller;

import com.parc.spring5webfluxrest.domain.Category;
import com.parc.spring5webfluxrest.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.hamcrest.Matchers.any;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;

public class CategoryControllerTest {

    WebTestClient webTestClient;
    CategoryController categoryController;
    CategoryRepository categoryRepository;



    @Before
    public void setUp() throws Exception {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient =WebTestClient.bindToController(categoryController).build();
    }

    @Test
    public void list() {

        BDDMockito.given(categoryRepository.findAll())
                .willReturn(Flux.just(Category.builder().description("cat1").build(),
                        Category.builder().description("cat2").build()));

        webTestClient.get()
                .uri("/api/v1/categories/")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    public void getById() {
        BDDMockito.given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().description("cat1").build()));

        webTestClient.get()
                .uri("/api/v1/categories/1")
                .exchange()
                .expectBody(Category.class);
    }
}