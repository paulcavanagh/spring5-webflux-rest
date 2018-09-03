package com.parc.spring5webfluxrest.controller;

import com.parc.spring5webfluxrest.domain.Vendor;
import com.parc.spring5webfluxrest.repositories.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@RestController
public class VendorController {

    public static final String API_V_1_VENDORS = "/api/v1/vendors/";
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

    @PostMapping(API_V_1_VENDORS)
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> createNewVendor(@RequestBody Publisher<Vendor> vendorStream){
        return vendorRepository.saveAll(vendorStream).then();
    }


    @PutMapping(API_V_1_VENDORS +"{id}")
    Mono<Vendor> updateVendor(@PathVariable String id, @RequestBody Vendor vendor) {
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }
}
