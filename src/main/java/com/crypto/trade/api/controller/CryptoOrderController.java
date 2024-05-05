package com.crypto.trade.api.controller;

import com.crypto.trade.api.response.ApiResponse;
import com.crypto.trade.api.response.OrderResponse;
import com.crypto.trade.api.service.CryptoOrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/cryptotrade/order")
public class CryptoOrderController {

    private final Logger logger = LoggerFactory.getLogger(CryptoOrderController.class);
    private final CryptoOrderService cryptoOrderService;

    @GetMapping
    public ApiResponse<OrderResponse> getOrderDetails(@RequestParam("exchange") String exchange,
                                                      @RequestParam("global-order-id") String globalOrderId, @RequestHeader HttpHeaders httpHeaders) {
        try {
            OrderResponse orderDetails = cryptoOrderService.getOrderDetails(exchange, globalOrderId, httpHeaders);
            logger.info("Successfully Retrieved OrderDetails for exchange{} globalOrder Id {}", exchange, globalOrderId);
            return ApiResponse.success("Successfully Retrieved Order Details", orderDetails);
        } catch (Exception ex) {
            logger.info("Exception While rendering the Order Details for exchange {} order Id {}", exchange, globalOrderId);
            return ApiResponse.error("Validation Failed", ex.getMessage());
        }
    }

//    @PostMapping
//    public ApiResponse<OrderResponse> createOrder(@RequestParam("exchange") String exchange,
//                                                      @RequestParam("global-order-id") String globalOrderId, @RequestHeader HttpHeaders httpHeaders) {
//        try {
//            OrderResponse orderDetails = cryptoOrderService.getOrderDetails(exchange, globalOrderId, httpHeaders);
//            logger.info("Successfully Retrieved OrderDetails for exchange{} globalOrder Id {}", exchange, globalOrderId);
//            return ApiResponse.success("Successfully Retrieved Order Details", orderDetails);
//        } catch (Exception ex) {
//            logger.info("Exception While rendering the Order Details for exchange {} order Id {}", exchange, globalOrderId);
//            return ApiResponse.error("Validation Failed", ex.getMessage());
//        }
//    }
}
