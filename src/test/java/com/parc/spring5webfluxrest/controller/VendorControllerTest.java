package com.parc.spring5webfluxrest.controller;


import com.parc.spring5webfluxrest.domain.Vendor;
import com.parc.spring5webfluxrest.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class VendorControllerTest {

    public static final String BASE_URI = "/api/v1/vendors/";
    WebTestClient webTestClient;
    VendorController vendorController;
    VendorRepository vendorRepository;

    @Before
    public void setUp() throws Exception {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    public void getAllVendors(){
        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(Vendor.builder().firstName("Paul").lastName("Cot").build(),
                        Vendor.builder().firstName("Joe").lastName("Dee").build()));

        webTestClient.get()
                .uri(BASE_URI)
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    public void getById() {
        BDDMockito.given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(Vendor.builder().firstName("cat1").lastName("dog").build()));

        webTestClient.get()
                .uri(BASE_URI +"1")
                .exchange()
                .expectBody(Vendor.class);
    }


    @Test
    public void createNewVendor(){
        BDDMockito.given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().build()));

        Mono<Vendor> vendorToSaveMono = Mono.just(Vendor.builder().firstName("Apple").build());

        webTestClient.post()
                .uri(BASE_URI)
                .body(vendorToSaveMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void updateVendor(){
        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> updatedVendorMono = Mono.just(Vendor.builder().firstName("Apple").build());

        webTestClient.put()
                .uri(BASE_URI + "sasa")
                .body(updatedVendorMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

}
