package com.example.orderservice.web;

import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.dto.ProductDto;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final WebClient.Builder webClientBuilder;

    public OrderController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<OrderResponse> create(@Valid @RequestBody OrderRequest req) {
        WebClient client = webClientBuilder.build();

        List<Mono<ProductDto>> calls = new ArrayList<>();
        for (Long id : req.getProductIds()) {
            calls.add(
                client.get()
                    .uri("lb://PRODUCT-SERVICE/products/{id}", id)
                    .retrieve()
                    .bodyToMono(ProductDto.class)
            );
        }

        return Mono.zip(calls, arr -> {
            List<OrderResponse.Item> items = new ArrayList<>();
            BigDecimal total = BigDecimal.ZERO;

            for (Object o : arr) {
                ProductDto p = (ProductDto) o;
                OrderResponse.Item item = new OrderResponse.Item();
                item.setProductId(p.getId());
                item.setName(p.getName());
                item.setPrice(p.getPrice());
                items.add(item);
                total = total.add(p.getPrice());
            }

            OrderResponse resp = new OrderResponse();
            resp.setItems(items);
            resp.setTotal(total);
            return resp;
        });
    }
}
