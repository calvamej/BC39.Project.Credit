package com.bootcamp.project.credit.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Credit")
public class CreditEntity {
    @Id
    private String id;
    private String creditNumber;
    //PRODUCT CODE: CC = CREDIT CARD, BC = BUSINESS CREDIT, PC = PERSONAL CREDIT.
    private String productCode;
    private String clientDocumentNumber;
    private String clientType;
    private String clientSubType;
    private String creditCardNumber;
    private String accountNumber;
    private double currentDebt;
    private double creditLimit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date debtDueDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date createDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date modifyDate;
}
