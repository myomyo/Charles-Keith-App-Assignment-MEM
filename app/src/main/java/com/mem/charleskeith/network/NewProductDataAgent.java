package com.mem.charleskeith.network;

public interface NewProductDataAgent {

    void loadNewProductList(int page, String accessToken, boolean isForceRefresh);
}
