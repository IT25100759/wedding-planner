package com.weddingplanner.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "weddings")
public class Wedding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String partner1Name;
    private String partner2Name;
    private LocalDate weddingDate;
    private Integer guestCount;
    private String packageType;
    private String status; // planned, confirmed, completed

    @OneToMany(mappedBy = "wedding", cascade = CascadeType.ALL)
    private List<Budget> budgets;

    @OneToMany(mappedBy = "wedding", cascade = CascadeType.ALL)
    private List<Payment> payments;

}
