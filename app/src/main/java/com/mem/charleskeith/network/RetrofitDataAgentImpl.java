package com.mem.charleskeith.network;


import com.mem.charleskeith.events.ApiErrorEvent;
import com.mem.charleskeith.events.SuccessForceRefreshGetNewProductEvent;
import com.mem.charleskeith.events.SuccessGetNewProductEvent;
import com.mem.charleskeith.network.response.GetNewProductResponse;
import com.mem.charleskeith.utils.NewProductConstants;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitDataAgentImpl implements NewProductDataAgent {

    private static RetrofitDataAgentImpl sObjInstance;

    private NewProductApi mNewProductApi;

    public RetrofitDataAgentImpl() {

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NewProductConstants.API_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        mNewProductApi = retrofit.create(NewProductApi.class);
    }

    public static RetrofitDataAgentImpl getObjInstance() {

        if (sObjInstance == null) {
            sObjInstance = new RetrofitDataAgentImpl();
        }
        return sObjInstance;
    }

    @Override
    public void loadNewProductList(int page, String accessToken, final boolean isForceRefresh) {
        Call<GetNewProductResponse> loadProductCall =mNewProductApi.loadNewProdctList(accessToken, page);

        loadProductCall.enqueue(new Callback<GetNewProductResponse>() {
            @Override
            public void onResponse(Call<GetNewProductResponse> call, Response<GetNewProductResponse> response) {
                GetNewProductResponse productResponse = response.body();
                if (productResponse != null && productResponse.isResponseOK()) {
                    if(isForceRefresh){
                        SuccessForceRefreshGetNewProductEvent event = new SuccessForceRefreshGetNewProductEvent(productResponse.getNewProducts());
                        EventBus.getDefault().post(event);
                    }else {
                        SuccessGetNewProductEvent event = new SuccessGetNewProductEvent(productResponse.getNewProducts());
                        EventBus.getDefault().post(event);
                    }

                } else {
                    if (productResponse == null) {
                        ApiErrorEvent event = new ApiErrorEvent("Empty response in network call.");
                        EventBus.getDefault().post(event);
                    } else {
                        ApiErrorEvent event = new ApiErrorEvent(productResponse.getMessage());
                        EventBus.getDefault().post(event);
                    }
                }

            }

            @Override
            public void onFailure(Call<GetNewProductResponse> call, Throwable t) {

                ApiErrorEvent event = new ApiErrorEvent(t.getMessage());
                EventBus.getDefault().post(event);
            }
        });
    }
}
