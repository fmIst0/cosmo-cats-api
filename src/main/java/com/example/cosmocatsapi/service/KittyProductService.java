package com.example.cosmocatsapi.service;

import com.example.cosmocatsapi.domain.KittyProduct;
import java.util.List;

public interface KittyProductService {
    List<KittyProduct> getAllKittyProducts();
}