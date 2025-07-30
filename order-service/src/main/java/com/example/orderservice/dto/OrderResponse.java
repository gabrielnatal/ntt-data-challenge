package com.example.orderservice.dto;

import java.math.BigDecimal;
import java.util.List;

public class OrderResponse {

    public static class Item {
        private Long productId;
        private String name;
        private BigDecimal price;

        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }
    }

    private List<Item> items;
    private BigDecimal total;

    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
}
