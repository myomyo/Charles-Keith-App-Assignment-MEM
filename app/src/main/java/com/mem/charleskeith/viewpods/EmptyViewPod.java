package com.mem.charleskeith.viewpods;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mem.charleskeith.R;
import com.mem.charleskeith.utils.GlideApp;


import butterknife.BindView;
import butterknife.ButterKnife;

public class EmptyViewPod extends RelativeLayout {
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;

    @BindView(R.id.tv_empty)
    TextView tvEmpty;

    public EmptyViewPod(Context context) {
        super(context);
    }

    public EmptyViewPod(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyViewPod(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this, this);

    }

    public void setEmptyData(String emptyImageUrl, String emptyMsg){

        GlideApp.with(ivEmpty.getContext()).load(emptyImageUrl).into(ivEmpty);

        tvEmpty.setText(emptyMsg);
    }

    public void setEmptyData(int emptyImageResource, String emptyMsg){
        ivEmpty.setImageResource(emptyImageResource);
        tvEmpty.setText(emptyMsg);
    }
}
