package com.example.orderservice.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class OrderRequest {
    @NotEmpty
    private List<Long> productIds;

    public List<Long> getProductIds() { return productIds; }
    public void setProductIds(List<Long> productIds) { this.productIds = productIds; }
}
