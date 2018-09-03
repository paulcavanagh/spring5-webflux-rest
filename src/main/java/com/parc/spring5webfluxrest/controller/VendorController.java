package com.parc.spring5webfluxrest.controller;

import com.parc.spring5webfluxrest.domain.Vendor;
import com.parc.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class VendorController {

    public static final String API_V_1_VENDORS = "/api/v1/vendors";
    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping(API_V_1_VENDORS)
    Flux<Vendor> getAllVendors(){
        return vendorRepository.findAll();
    }

    @GetMapping(API_V_1_VENDORS +"{id}")
    Mono<Vendor> getById(@PathVariable String id){
        return vendorRepository.findById(id);
    }
}
