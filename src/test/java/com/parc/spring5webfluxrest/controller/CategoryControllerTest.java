package com.parc.spring5webfluxrest.controller;

import com.parc.spring5webfluxrest.domain.Category;
import com.parc.spring5webfluxrest.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class CategoryControllerTest {

    public static final String API_V_1_CATEGORIES = "/api/v1/categories/";
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

        given(categoryRepository.findAll())
                .willReturn(Flux.just(Category.builder().description("cat1").build(),
                        Category.builder().description("cat2").build()));

        webTestClient.get()
                .uri(API_V_1_CATEGORIES)
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    public void getById() {
        given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().description("cat1").build()));

        webTestClient.get()
                .uri(API_V_1_CATEGORIES+"1")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    public void createNewCategory(){
        given(categoryRepository.saveAll(any(Publisher.class))).
                willReturn(Flux.just(Category.builder().build()));

        Mono<Category> categoryToSaveMono = Mono.just(Category.builder().description("Some category").build());

        webTestClient.post()
                .uri(API_V_1_CATEGORIES)
                .body(categoryToSaveMono, Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void updateCategory(){
        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> updatedCategoryMono = Mono.just(Category.builder().description("Updated category").build());

        webTestClient.put()
                .uri(API_V_1_CATEGORIES + "asas")
                .body(updatedCategoryMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void patchCategoryWithChanges(){
        given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().build()));


        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> updatedCategoryMono = Mono.just(Category.builder().description("Updated category").build());

        webTestClient.patch()
                .uri(API_V_1_CATEGORIES + "asas")
                .body(updatedCategoryMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(categoryRepository).save(any());
    }

    @Test
    public void patchCategoryWithNoChanges(){
        given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().build()));

        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> updatedCategoryMono = Mono.just(Category.builder().build());

        webTestClient.patch()
                .uri(API_V_1_CATEGORIES + "asas")
                .body(updatedCategoryMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(categoryRepository, never()).save(any());
    }
}