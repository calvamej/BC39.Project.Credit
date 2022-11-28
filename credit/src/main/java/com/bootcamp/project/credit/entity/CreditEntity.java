package com.bootcamp.project.credit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Credit")
public class CreditEntity {
    @Id
    private ObjectId id;
    private String creditNumber;
    private String client;
    private String creditType;
    private String clientType;
    private String accountNumber;
    private double totalDebt;
    private double currentDebt;
    private double creditLimit;
    private Date insert_date;
}
