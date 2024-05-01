package com.crypto.trade.api.controller;

import com.crypto.trade.api.response.ApiResponse;
import com.crypto.trade.api.response.CoinSwitchResponse;
import com.crypto.trade.api.response.PortFolio;
import com.crypto.trade.api.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wallet")
public class WalletResource {
    private final Logger logger = LoggerFactory.getLogger(WalletResource.class);
    private final WalletService walletService;

    @GetMapping("/portfolio")
    public ApiResponse<CoinSwitchResponse<List<PortFolio>>> getPortFolio(){
        try {
            CoinSwitchResponse<List<PortFolio>> portFolioDetails = walletService.getPortFolioDetails();
            return ApiResponse.success("Success", portFolioDetails);
        } catch (Exception ex) {
            logger.info("Exception e {}",ex);
            return ApiResponse.error("Failed in getting PortFolio Details " + ex.getMessage());
        }
    }


}
