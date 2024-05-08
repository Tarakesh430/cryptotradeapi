package com.crypto.trade.api.controller;

import com.crypto.trade.api.request.OrderRequest;
import com.crypto.trade.api.response.ApiResponse;
import com.crypto.trade.api.response.OrderResponse;
import com.crypto.trade.api.service.CryptoOrderService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/cryptotrade/order")
public class CryptoOrderController {

    private final Logger logger = LoggerFactory.getLogger(CryptoOrderController.class);
    private final CryptoOrderService cryptoOrderService;

    @GetMapping("/order")
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

    @PostMapping("/order")
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest,
                                                  @RequestHeader HttpHeaders httpHeaders) {
        try {
            logger.info("Create Order for the Request {}", orderRequest);
            OrderResponse orderResponse = cryptoOrderService.placeOrder(orderRequest, httpHeaders);
            return ApiResponse.success("Successfully Retrieved Order Details", orderResponse);
        } catch (Exception ex) {
            logger.info("Exception in executing the Order {}", orderRequest);
            return ApiResponse.error("Validation Failed", ex.getMessage());
        }
    }

    @GetMapping("/orders")
    public ApiResponse<List<OrderResponse>> getOrders(@RequestParam("count") String count,
                                                      @RequestParam("from_time") String from_time,
                                                      @RequestParam("to_time") String to_time, @RequestParam("side") String side,
                                                      @RequestParam("symbols") String symbols, @RequestParam("exchange") String exchange,
                                                      @RequestParam("type") String type, @RequestParam("status") String status,
                                                      @RequestHeader HttpHeaders httpHeaders) {
        try {
            logger.info("Received Get Orders Request wit count{} from_time {} to_time {} symbols{},exchange {} ,type {} ,status {} side {}",
                    count, from_time, to_time, symbols, exchange, type, status, side);
            List<OrderResponse> orderResponses = cryptoOrderService.getOrders(count, from_time, to_time, side, symbols, exchange, type, status);
            return ApiResponse.success("Successfully Retrieved Order Details", orderResponses);
        } catch (Exception ex) {
            logger.info("Exception in retrieving the Orders");
            return ApiResponse.error("Validation Failed", ex.getMessage());
        }
    }

    @DeleteMapping("/order")
    public ApiResponse<OrderResponse> deleteOrder(@RequestParam("exchange") String exchange,
                                                  @RequestParam("global-order-id") String globalOrderId, @RequestHeader HttpHeaders httpHeaders) {
        try {
            OrderResponse orderDetails = cryptoOrderService.deleteOrder(exchange, globalOrderId, httpHeaders);
            logger.info("Successfully Retrieved OrderDetails for exchange{} globalOrder Id {}", exchange, globalOrderId);
            return ApiResponse.success("Successfully Retrieved Order Details", orderDetails);
        } catch (Exception ex) {
            logger.info("Exception While rendering the Order Details for exchange {} order Id {}", exchange, globalOrderId);
            return ApiResponse.error("Validation Failed", ex.getMessage());
        }
    }
}
