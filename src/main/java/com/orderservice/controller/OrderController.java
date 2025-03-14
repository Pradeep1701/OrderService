package com.orderservice.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderservice.dto.Order;
import com.orderservice.dto.OrderEvent;
import com.orderservice.kafkaService.OrderProducer;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
	
	private OrderProducer orderProducer;
	
	public OrderController(OrderProducer orderProducer) {
		this.orderProducer = orderProducer;
	}
	
	@PostMapping("/orders")
	public String placeOrder(@RequestBody Order order) {
		
		order.setOrderId(UUID.randomUUID().toString());
		
		OrderEvent orderEvent  = new OrderEvent();
		orderEvent.setStatus("PENDING");
		orderEvent.setMessage("Order status is in pending state");
		orderEvent.setOrder(order);
		
		orderProducer.sendMessage(orderEvent);
		System.out.println("Successful");
		return "Order placed successfully!";
	}
	
}
