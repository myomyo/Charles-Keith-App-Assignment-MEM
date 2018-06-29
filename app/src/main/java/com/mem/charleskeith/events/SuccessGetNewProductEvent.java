package com.mem.charleskeith.events;

import com.mem.charleskeith.data.vos.ProductVO;

import java.util.List;

public class SuccessGetNewProductEvent {

    private List<ProductVO> productList;

    public SuccessGetNewProductEvent(List<ProductVO> productList) {
        this.productList = productList;
    }

    public List<ProductVO> getProductList() {
        return productList;
    }
}
