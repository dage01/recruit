package com.dage.recruit.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class JobPostingDTO {
    private String yyyy;
    private String degrees;
    private String remarks;
    private String endDate;
    private String openTg;
    private long closingDay;
}
