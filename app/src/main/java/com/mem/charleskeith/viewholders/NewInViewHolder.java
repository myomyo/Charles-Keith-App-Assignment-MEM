package com.mem.charleskeith.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mem.charleskeith.delegates.NewInDelegate;

import butterknife.ButterKnife;

public class NewInViewHolder extends RecyclerView.ViewHolder {

    private NewInDelegate mNewInDelegate;

    public NewInViewHolder(View itemView, NewInDelegate newInDelegate) {
        super(itemView);
        //ButterKnife.bind(this,itemView);
        this.mNewInDelegate = newInDelegate;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNewInDelegate.onTapItem();
            }
        });


    }
}
