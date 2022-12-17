package com.bootcamp.project.credit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditReportEntity {
    private String productCode;
    List<CreditEntity> creditList;
}