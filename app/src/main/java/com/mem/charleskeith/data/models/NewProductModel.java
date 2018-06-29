package com.mem.charleskeith.data.models;

import com.mem.charleskeith.data.vos.ProductVO;
import com.mem.charleskeith.events.SuccessForceRefreshGetNewProductEvent;
import com.mem.charleskeith.events.SuccessGetNewProductEvent;
import com.mem.charleskeith.network.NewProductDataAgent;
import com.mem.charleskeith.network.RetrofitDataAgentImpl;
import com.mem.charleskeith.utils.NewProductConstants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewProductModel {

    private static NewProductModel objInstance;

    private NewProductDataAgent mDataAgent;

    private int mPage;

    private Map<Integer, ProductVO> mProductMap;

    private NewProductModel() {

        mDataAgent = RetrofitDataAgentImpl.getObjInstance();
        mPage = 1;
        mProductMap = new HashMap<>();
        EventBus.getDefault().register(this);
    }

    public static NewProductModel getObjInstance() {

        if(objInstance == null){
            objInstance = new NewProductModel();
        }
        return objInstance;
    }

    public void loadNewProductList(){
        mDataAgent.loadNewProductList(mPage, NewProductConstants.ACCESS_TOKEN, false);
    }

    public void forceRefreshNewsList() {
        mPage = 1;
        mDataAgent.loadNewProductList(1, NewProductConstants.ACCESS_TOKEN, true);
    }

    public ProductVO getProductById(int productId){
        return mProductMap.get(productId);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onSuccessGetNewProduct(SuccessGetNewProductEvent event) {


        setDataIntoRepository(event.getProductList());
        mPage++;
    }
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onSuccessForceRefreshGetNewProduct(SuccessForceRefreshGetNewProductEvent event) {
        setDataIntoRepository(event.getProductList());
    }

    private void setDataIntoRepository(List<ProductVO> productList) {
        for(ProductVO product : productList){
            mProductMap.put(product.getProductId(), product);
        }

    }

}
