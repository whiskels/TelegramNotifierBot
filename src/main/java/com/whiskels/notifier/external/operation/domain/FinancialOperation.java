package com.whiskels.notifier.external.operation.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.whiskels.notifier.AbstractBaseEntity;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Comparator;

/**
 * Customer receivable data is received from JSON of the following syntax:
 * [{"id":111,
 * "date":"2020-11-19",
 * "currency":"",
 * "amount":"",
 * "amount_usd":"",
 * "amount_rub":"",
 * "bank":"",
 * "bank_account":"",
 * "legal_name":"",
 * "contractor":"",
 * "type":"",
 * "contractor_account":,
 * "contractor_legal_name":"",
 * "category":"",
 * "subcategory":"",
 * "project":"",
 * "office":"",
 * "description":""}
 */

@Entity
@Table(name = "financial_operation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class FinancialOperation extends AbstractBaseEntity implements Comparable<FinancialOperation> {
    @JsonProperty("id")
    int crmId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate date;
    String currency;
    double amount;
    @JsonProperty("amount_usd")
    double amountUsd;
    @JsonProperty("amount_rub")
    double amountRub;
    String bank;
    @JsonProperty("bank_account")
    String bankAccount;
    @JsonProperty("legal_name")
    String legalName;
    String contractor;
    String type;
    @JsonProperty("contractor_account")
    String contractorAccount;
    @JsonProperty("contractor_legal_name")
    String contractorLegalName;
    String category;
    String subcategory;
    String project;
    String office;
    String description;
    LocalDate loadDate;

    @Override
    public int compareTo(@NotNull FinancialOperation o) {
        return Comparator.comparing(FinancialOperation::getAmountRub)
                .thenComparing(FinancialOperation::getContractor)
                .reversed()
                .compare(this, o);
    }
}
