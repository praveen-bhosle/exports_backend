package com.example.demo.records;

import java.util.List;

import com.example.demo.model.Image;

public record  ProductDTO  ( Long id , String sizeA ,  String sizeB ,  String quality  , List<Image> images ,  Long cost  )  {} 