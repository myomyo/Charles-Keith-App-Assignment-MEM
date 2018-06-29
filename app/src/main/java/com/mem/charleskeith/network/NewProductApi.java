package com.mem.charleskeith.network;

import com.mem.charleskeith.network.response.GetNewProductResponse;
import com.mem.charleskeith.utils.NewProductConstants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NewProductApi {

    @FormUrlEncoded
    @POST(NewProductConstants.GET_NEW_PRODUCT)
    Call<GetNewProductResponse> loadNewProdctList(
          @Field(NewProductConstants.PARAM_ACCESS_TOKEN) String accessToken,
          @Field(NewProductConstants.PAGE) int page);
}
