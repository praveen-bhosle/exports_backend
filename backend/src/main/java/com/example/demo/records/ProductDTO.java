package com.example.demo.records;

import java.util.List;
import java.util.UUID;

import com.example.demo.model.Image;

public record  ProductDTO  ( UUID id , String sizeA ,  String sizeB ,  String quality  , List<Image> images ,  Long cost  )  {} 