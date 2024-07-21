package com.group2.AutismAssesmentBackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user-data")
public class UserDataEntity {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Long A1;
    private Long A2;
    private Long A3;
    private Long A4;
    private Long A5;
    private Long A6;
    private Long A7;
    private Long A8;
    private Long A9;
    private Long A10;
    private Long Age_Months;
    private String Sex;
    private String Ethnicity;
    private String Jaundice;
    private String Family_mem_with_ASD;
    private String Who_completed_the_test;
    private Long Result;
    @Column(unique = true)
    private String Token;

}
