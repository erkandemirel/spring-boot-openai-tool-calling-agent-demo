package com.example.toolcallingagent.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OrderTools {

    private static final Logger log = LoggerFactory.getLogger(OrderTools.class);

    private final Map<Long, String> orders = new ConcurrentHashMap<>(Map.of(
            1001L, "SHIPPED",
            1002L, "PREPARING",
            1003L, "DELIVERED"
    ));

    private final Map<Long, String> estimatedDeliveryDates = Map.of(
            1001L, "2026-10-18",
            1002L, "2026-10-20",
            1003L, "ALREADY_DELIVERED"
    );

    @Tool(description = "Gets the current status of an order by its order ID")
    public String getOrderStatus(
            @ToolParam(description = "The unique order ID") Long orderId) {

        log.info("Tool called: getOrderStatus, orderId={}", orderId);
        return orders.getOrDefault(orderId, "ORDER_NOT_FOUND");
    }

    @Tool(description = "Gets the estimated delivery date of an order by its order ID")
    public String getEstimatedDeliveryDate(
            @ToolParam(description = "The unique order ID") Long orderId) {

        log.info("Tool called: getEstimatedDeliveryDate, orderId={}", orderId);
        return estimatedDeliveryDates.getOrDefault(orderId, "ORDER_NOT_FOUND");
    }

    @Tool(description = "Cancels an order only when its current status is PREPARING")
    public String cancelOrder(
            @ToolParam(description = "The unique order ID") Long orderId) {

        log.info("Tool called: cancelOrder, orderId={}", orderId);
        String currentStatus = orders.get(orderId);

        if (currentStatus == null) {
            return "ORDER_NOT_FOUND";
        }

        if (!"PREPARING".equals(currentStatus)) {
            return "CANCELLATION_NOT_ALLOWED: order status is " + currentStatus;
        }

        orders.put(orderId, "CANCELLED");
        return "ORDER_CANCELLED";
    }
}
