package com.crypto.trade.api.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Data
public class CryptoExchangeDto implements Serializable {

    private Long exchangeId;
    private String exchangeName;
    private Integer isActive;
    private String createdBy;
    private String updatedBy;
    private Long createdTime;
    private Long updatedTime;
}
