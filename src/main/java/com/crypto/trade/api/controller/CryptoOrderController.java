package com.crypto.trade.api.controller;

import com.crypto.trade.api.exception.MyCustomException;
import com.crypto.trade.api.request.OrderRequest;
import com.crypto.trade.api.response.ApiResponse;
import com.crypto.trade.api.response.OrderResponse;
import com.crypto.trade.api.service.CryptoOrderService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/cryptotrade")
public class CryptoOrderController {

    private final Logger logger = LoggerFactory.getLogger(CryptoOrderController.class);
    private final CryptoOrderService cryptoOrderService;

    @GetMapping("/order")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderDetails(@RequestParam("global-order-id") String globalOrderId, @RequestHeader HttpHeaders httpHeaders) throws Exception {
        try {
            OrderResponse orderDetails = cryptoOrderService.getOrderDetails(globalOrderId, httpHeaders);
            logger.info("Successfully Retrieved OrderDetails for globalOrder Id {}", globalOrderId);
            return ResponseEntity.ok(ApiResponse.success("Successfully Retrieved Order Details", orderDetails));
        } catch (Exception ex) {
            logger.info("Exception While rendering the Order Details for order Id {}", globalOrderId);
            throw new MyCustomException(ex.getMessage());
        }
    }

    @PostMapping("/order")
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@RequestBody OrderRequest orderRequest,
                                                  @RequestHeader HttpHeaders httpHeaders) {
        try {
            logger.info("Create Order for the Request {}", orderRequest);
            OrderResponse orderResponse = cryptoOrderService.placeOrder(orderRequest, httpHeaders);
            return ResponseEntity.ok(ApiResponse.success("Successfully Retrieved Order Details", orderResponse));
        } catch (Exception ex) {
            logger.info("Exception in executing the Order {}", orderRequest);
            //return ApiResponse.error("Validation Failed", ex.getMessage());
            throw new MyCustomException(ex.getMessage());
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrders(String count, String fromTime, String toTime,
                                                                      String side, String symbols, String type,
                                                                      String status,
                                                                      @RequestParam("exchange") String exchange,
                                                                      @RequestHeader HttpHeaders httpHeaders) {
        try {
            logger.info("Received Get Orders Request wit count{} from_time {} to_time {} symbols{},exchange {} ,type {} ,status {} side {}",
                    count, fromTime, toTime, symbols, exchange, type, status, side);
            List<OrderResponse> orderResponses = cryptoOrderService.getOrders(count, fromTime, toTime, side, symbols, exchange, type, status, httpHeaders);
            return ResponseEntity.ok(ApiResponse.success("Successfully Retrieved Order Details", orderResponses));
        } catch (Exception ex) {
            logger.info("Exception in retrieving the Orders");
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(ApiResponse.error("Error In Retrieving orders", ex.getMessage()));
            throw new MyCustomException(ex.getMessage());
        }
    }

    @DeleteMapping("/order")
    public ApiResponse<OrderResponse> deleteOrder(@RequestParam("global-order-id") String globalOrderId, @RequestHeader HttpHeaders httpHeaders) {
        try {
            OrderResponse orderDetails = cryptoOrderService.deleteOrder(globalOrderId, httpHeaders);
            logger.info("Deleted Order globalOrder Id {}", globalOrderId);
            return ApiResponse.success("Successfully Deleted Order", orderDetails);
        } catch (Exception ex) {
            logger.info("Exception While rendering the Order Details for exchange {} order Id {}", exchange, globalOrderId);
            //return ApiResponse.error("Validation Failed", ex.getMessage());
            throw new MyCustomException(ex.getMessage());

        }
    }


}
