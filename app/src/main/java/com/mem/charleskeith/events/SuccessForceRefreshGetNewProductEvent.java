package com.mem.charleskeith.events;

import com.mem.charleskeith.data.vos.ProductVO;

import java.util.List;

public class    SuccessForceRefreshGetNewProductEvent extends SuccessGetNewProductEvent {
    public SuccessForceRefreshGetNewProductEvent(List<ProductVO> productList) {
        super(productList);
    }
}
