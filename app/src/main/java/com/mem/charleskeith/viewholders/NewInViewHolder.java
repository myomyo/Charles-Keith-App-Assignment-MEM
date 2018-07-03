package com.mem.charleskeith.viewholders;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mem.charleskeith.R;
import com.mem.charleskeith.data.vos.ProductVO;
import com.mem.charleskeith.delegates.NewInDelegate;
import com.mem.charleskeith.utils.GlideApp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;

public class NewInViewHolder extends BaseNewInViewHolder {

    private NewInDelegate mNewInDelegate;
    private ProductVO mProduct;


    @BindView(R.id.iv_new_item)
    ImageView ivNewItem;

    @BindView(R.id.tv_new_item_name)
    TextView tvNewItemName;

    public NewInViewHolder(View itemView, NewInDelegate newInDelegate) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mNewInDelegate = newInDelegate;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNewInDelegate.onTapItem(mProduct);
            }
        });
    }

    @Override
    public void bindData(ProductVO product, int itemCount) {

        //ivNewItem.setImageDrawable(null);
        mProduct = product;
        tvNewItemName.setText(product.getProductTitle());
        GlideApp.with(ivNewItem.getContext())
                .load(product.getProductImage())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error)
                .into(ivNewItem);
    }



}
