package com.weddingplanner.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
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

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPartner1Name() { return partner1Name; }
    public void setPartner1Name(String partner1Name) { this.partner1Name = partner1Name; }

    public String getPartner2Name() { return partner2Name; }
    public void setPartner2Name(String partner2Name) { this.partner2Name = partner2Name; }

    public LocalDate getWeddingDate() { return weddingDate; }
    public void setWeddingDate(LocalDate weddingDate) { this.weddingDate = weddingDate; }

    public Integer getGuestCount() { return guestCount; }
    public void setGuestCount(Integer guestCount) { this.guestCount = guestCount; }

    public String getPackageType() { return packageType; }
    public void setPackageType(String packageType) { this.packageType = packageType; }
}