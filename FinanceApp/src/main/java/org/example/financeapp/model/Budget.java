package org.example.financeapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "budget")
@Data
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double income;
    private double housing;
    private double utilities;
    private double transport;
    private double leisure;
    private double food_drink;
    private double clothing;
    private double miscellaneous;
//Double check what categories I want, default value to 0 if not present, always display all categories.
//Can view by month or specify which category to view by.
    //Do I want to store a sum line? or calculate with the values each time?
}
