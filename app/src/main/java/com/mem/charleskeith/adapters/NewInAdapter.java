package com.mem.charleskeith.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mem.charleskeith.R;

import com.mem.charleskeith.data.vos.ProductVO;
import com.mem.charleskeith.delegates.NewInDelegate;
import com.mem.charleskeith.viewholders.BaseNewInViewHolder;
import com.mem.charleskeith.viewholders.NewInHeaderViewHolder;
import com.mem.charleskeith.viewholders.NewInViewHolder;

import java.util.ArrayList;
import java.util.List;

public class NewInAdapter extends RecyclerView.Adapter<BaseNewInViewHolder> {


    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private NewInDelegate mNewInDelegate;
    private List<ProductVO> mProductList;
    private boolean mLayoutChangeFlag;

    public NewInAdapter(NewInDelegate newInDelegate) {
        this.mNewInDelegate = newInDelegate;
        mProductList = new ArrayList<>();
    }

    @NonNull
    @Override
    public BaseNewInViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        if(viewType == TYPE_HEADER){

            view = layoutInflater.inflate(R.layout.viewholder_new_in_header, parent, false);
            return new NewInHeaderViewHolder(view);

        }else if (viewType == TYPE_ITEM){
            /*if(mLayoutChangeFlag){
                view = layoutInflater.inflate(R.layout.viewholder_new_in_grip, parent, false);
                return new NewInViewHolder(view, mNewInDelegate);
            }else{*/
                view = layoutInflater.inflate(R.layout.viewholder_new_in, parent, false);
                return new NewInViewHolder(view, mNewInDelegate);
            //}


        }

       return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseNewInViewHolder holder, int position) {

        holder.bindData(mProductList.get(position), mProductList.size());
    }


    @Override
    public int getItemCount() {
        return mProductList.size()+1; //TODO need to add 1 to list size;
    }

    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        if(isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    public boolean isPositionHeader(int position)
    {
        return position == 0;
    }

    public void setProductList(List<ProductVO> productList) {
        mProductList = productList;
        notifyDataSetChanged();
    }

    public void appendNewProductList(List<ProductVO> productList){

        mProductList.addAll(productList);
        notifyDataSetChanged();
    }

    public void setLayoutChangeFlag(boolean mLayoutChangeFlag) {
        this.mLayoutChangeFlag = mLayoutChangeFlag;
    }
}
