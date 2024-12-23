package com.example.cosmocatsapi.service.impl;

import com.example.cosmocatsapi.domain.KittyProduct;
import com.example.cosmocatsapi.service.KittyProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KittyProductServiceImpl implements KittyProductService {
    List<KittyProduct> kittyProducts = new ArrayList<>(List.of(
            KittyProduct.builder().id(1L).name("Cat food").description("...").price(5.99).build(),
            KittyProduct.builder().id(2L).name("Cat drink").description("...").price(3.99).build()
    ));

    @Override
    public List<KittyProduct> getAllKittyProducts() {
        return kittyProducts;
    }
}