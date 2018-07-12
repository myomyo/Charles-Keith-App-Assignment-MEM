package com.mem.charleskeith.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import com.mem.charleskeith.R;
import com.mem.charleskeith.adapters.NewInAdapter;
import com.mem.charleskeith.data.models.NewProductModel;
import com.mem.charleskeith.data.vos.ProductVO;
import com.mem.charleskeith.delegates.NewInDelegate;
import com.mem.charleskeith.events.ApiErrorEvent;
import com.mem.charleskeith.events.SuccessGetNewProductEvent;
import com.mem.charleskeith.utils.NewProductConstants;
import com.mem.charleskeith.viewpods.EmptyViewPod;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewInActivity extends BaseActivity implements NewInDelegate{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_new_in)
    RecyclerView rvNewIn;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.vp_empty)
    EmptyViewPod vpEmpty;

    @BindView(R.id.iv_rectangle_one)
    ImageView ivRectangleOne;

    @BindView(R.id.iv_rectangle_two)
    ImageView getIvRectangleTwo;

    /*@BindView(R.id.tabs)
    TabLayout tabLayout;*/

    private NewInAdapter mNewInAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_in);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this,this);

        mNewInAdapter = new NewInAdapter(this);
        mNewInAdapter.setLayoutChangeFlag(false);
        rvNewIn.setAdapter(mNewInAdapter);
        rvNewIn.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));


        // start pagination
        rvNewIn.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private boolean isListEndReached = false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && ((LinearLayoutManager) recyclerView.getLayoutManager())
                        .findLastCompletelyVisibleItemPosition() == recyclerView.getAdapter().getItemCount() - 1
                        && !isListEndReached) {

                    isListEndReached = true;

                    NewProductModel.getObjInstance().loadNewProductList();

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int pastVisibleItems = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if ((visibleItemCount + pastVisibleItems) < totalItemCount) {
                    isListEndReached = false;
                }
            }
        }); // end pagination

        NewProductModel.getObjInstance().loadNewProductList();
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NewProductModel.getObjInstance().forceRefreshNewsList();
            }
        });

        vpEmpty.setEmptyData(R.drawable.placeholder_image, getString(R.string.empty_msg));

        ivRectangleOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNewInAdapter.setLayoutChangeFlag(false);
                rvNewIn.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));
            }
        });

        getIvRectangleTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNewInAdapter.setLayoutChangeFlag(true);
                final GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2,GridLayoutManager.VERTICAL, false);
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {                    @Override
                    public int getSpanSize(int position) {
                        return mNewInAdapter.isPositionHeader(position) ? gridLayoutManager.getSpanCount() : 1;
                    }

                });
                rvNewIn.setLayoutManager(gridLayoutManager);
            }
        });
    }

    @Override
    public void onTapItem(ProductVO product) {
        Intent intent = new Intent(getApplicationContext(), NewInDetailsActivity.class);
        intent.putExtra(NewProductConstants.PRODUCT_ID, product.getProductId());
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessGetNewProduct(SuccessGetNewProductEvent event){
        Log.d("onSuccessGetNewProduct", "onSuccessGetNewProduct : "+event.getProductList().size());
        mNewInAdapter.appendNewProductList(event.getProductList());
        swipeRefreshLayout.setRefreshing(false);
        vpEmpty.setVisibility(View.GONE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFailGetNewProduct(ApiErrorEvent event) {

        swipeRefreshLayout.setRefreshing(false);
        Snackbar.make(swipeRefreshLayout, event.getErrorMsg(), Snackbar.LENGTH_INDEFINITE).show();
        vpEmpty.setVisibility(View.VISIBLE);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessForceRefreshGetNewProduct(SuccessGetNewProductEvent event){
        mNewInAdapter.setProductList(event.getProductList());
        Log.d("onSuccessForceRefresh", "onSuccessForceRefreshGetNewProduct : "+event.getProductList().size());
        swipeRefreshLayout.setRefreshing(false);
    }
}
