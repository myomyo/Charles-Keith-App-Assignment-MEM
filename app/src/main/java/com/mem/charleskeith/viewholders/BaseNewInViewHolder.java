package com.mem.charleskeith.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mem.charleskeith.data.vos.ProductVO;

public abstract class BaseNewInViewHolder extends RecyclerView.ViewHolder {


    public BaseNewInViewHolder(View itemView) {
        super(itemView);
    }

    public void bindData(ProductVO product){

    }

    public void bindData(ProductVO product, int itemCount){}
}
