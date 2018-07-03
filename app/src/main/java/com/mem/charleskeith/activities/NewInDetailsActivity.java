package com.mem.charleskeith.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mem.charleskeith.R;
import com.mem.charleskeith.data.models.NewProductModel;
import com.mem.charleskeith.data.vos.ProductVO;
import com.mem.charleskeith.delegates.NewInDetailsDelegate;
import com.mem.charleskeith.utils.GlideApp;
import com.mem.charleskeith.utils.NewProductConstants;
import com.mem.charleskeith.viewpods.EmptyViewPod;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewInDetailsActivity extends BaseActivity implements NewInDetailsDelegate{

    @BindView(R.id.iv_item_details)
    ImageView ivItemImage;

    @BindView(R.id.tv_item_name_details)
    TextView tvItemName;

    @BindView(R.id.vp_empty)
    EmptyViewPod vpEmpty;

    @BindView(R.id.rl_newin_details)
    RelativeLayout rlDetails;

    @BindView(R.id.btn_info)
    Button btnInfo;

    //private NewInDetailsDelegate mDetailDelegate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_in_details);

        ButterKnife.bind(this, this);

        int productId = getIntent().getIntExtra(NewProductConstants.PRODUCT_ID, -1);

        Log.d("NewInDetailsActivity", "Product Id : "+ productId);

        ProductVO product = NewProductModel.getObjInstance().getProductById(productId);
        if(product != null){
            bindData(product);
        } else {
            vpEmpty.setVisibility(View.VISIBLE);
            rlDetails.setVisibility(View.GONE);
            vpEmpty.setEmptyData(R.drawable.placeholder_image, getString(R.string.empty_msg));
        }


    }

   /* @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }*/

    private void bindData(final ProductVO product) {
        tvItemName.setText(product.getProductTitle());
        GlideApp.with(ivItemImage.getContext())
                .load(product.getProductImage())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error)
                .into(ivItemImage);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onTapItemInfo();
            }
        });
    }

    @Override
    public void onTapItemInfo() {
        Intent intent = new Intent(this, NewInDetailsInfoActivity.class);
        //intent.putExtra(NewProductConstants.PRODUCT_ID, product.getProductId());
        startActivity(intent);
    }
}
