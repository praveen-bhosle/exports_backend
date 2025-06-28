package  com.example.demo.records ;

import java.util.UUID;

public record CartProductDTO(UUID id ,  Long quantity , ProductDTO product) {}