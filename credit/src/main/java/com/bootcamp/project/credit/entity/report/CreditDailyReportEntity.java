package com.bootcamp.project.credit.entity.report;

import com.bootcamp.project.credit.entity.CreditEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditDailyReportEntity {
    private String clientDocumentNumber;
    private long numberOfCredits;
    private Double averageCurrentDebt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date currentDate;
    List<CreditEntity> creditList;
}
