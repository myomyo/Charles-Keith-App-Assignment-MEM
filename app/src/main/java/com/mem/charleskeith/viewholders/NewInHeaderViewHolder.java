package com.mem.charleskeith.viewholders;

import android.view.View;
import android.widget.TextView;

import com.mem.charleskeith.R;
import com.mem.charleskeith.data.vos.ProductVO;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewInHeaderViewHolder extends BaseNewInViewHolder {

    //private ProductVO mProduct;

    @BindView(R.id.tv_items_count)
    TextView tvItemsCount;

    public NewInHeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindData(ProductVO product, int itemCount) {
        tvItemsCount.setText(tvItemsCount.getContext().getResources().getString(R.string.format_item_count, String.valueOf(itemCount)));

    }
}
