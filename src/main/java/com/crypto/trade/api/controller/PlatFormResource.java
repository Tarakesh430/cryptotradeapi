package com.crypto.trade.api.controller;

import com.crypto.trade.api.response.ApiResponse;
import com.crypto.trade.api.response.CoinSwitchResponse;
import com.crypto.trade.api.service.PlatformService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/platform")
public class PlatFormResource {
    private final Logger logger = LoggerFactory.getLogger(PlatFormResource.class);
    private final PlatformService platformService;

    @GetMapping("/activeCoins")
    public ApiResponse<CoinSwitchResponse<Map<String, List<String>>>> getAllActiveCoins(@RequestParam(name = "exchange", required = true) String exchange) {
        try {
            CoinSwitchResponse<Map<String, List<String>>> allActiveCoins = platformService.getAllActiveCoins(exchange);
            return ApiResponse.success("Success", allActiveCoins);
        } catch (Exception ex) {
            return ApiResponse.error("Failed in getting all Active coins for exchange " + exchange);
        }
    }
}
