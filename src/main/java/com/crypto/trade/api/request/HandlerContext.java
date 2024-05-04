package com.crypto.trade.api.request;


import com.crypto.trade.api.dto.CoinDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpHeaders;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class HandlerContext implements Serializable {
    private HttpHeaders httpHeaders;
    private String exchange;
    private List<CoinDto> coins;
}
