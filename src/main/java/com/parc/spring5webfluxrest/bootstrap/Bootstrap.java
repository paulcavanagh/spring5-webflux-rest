package com.parc.spring5webfluxrest.bootstrap;

import com.parc.spring5webfluxrest.domain.Category;
import com.parc.spring5webfluxrest.domain.Vendor;
import com.parc.spring5webfluxrest.repositories.CategoryRepository;
import com.parc.spring5webfluxrest.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        if(categoryRepository.count().block() == 0) {

            System.out.println("#### LOADING DATA ON BOOTSTRAP #####");

            categoryRepository.save(Category.builder().description("Fruits").build()).block();
            categoryRepository.save(Category.builder().description("Nuts").build()).block();
            categoryRepository.save(Category.builder().description("Breads").build()).block();
            categoryRepository.save(Category.builder().description("Meats").build()).block();
            categoryRepository.save(Category.builder().description("Eggs").build()).block();

            System.out.println("Loaded Categories: " + categoryRepository.count().block());

            vendorRepository.save(Vendor.builder()
                    .firstName("Roy")
                    .lastName("Keane")
                    .build()).block();


            vendorRepository.save(Vendor.builder()
                    .firstName("Eric")
                    .lastName("Cantona")
                    .build()).block();

            vendorRepository.save(Vendor.builder()
                    .firstName("Steve")
                    .lastName("Bruce")
                    .build()).block();

            vendorRepository.save(Vendor.builder()
                    .firstName("Dennis")
                    .lastName("Irwin")
                    .build()).block();

            vendorRepository.save(Vendor.builder()
                    .firstName("Ronaldo")
                    .lastName("Santos")
                    .build()).block();

            System.out.println("Loaded Vendor: " + vendorRepository.count().block());

        }

    }
}
