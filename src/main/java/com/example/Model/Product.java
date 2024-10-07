package com.example.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String productName;

    private String productDescription;

    private String price;

    private String quantity;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
