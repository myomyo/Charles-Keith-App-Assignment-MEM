package com.mem.charleskeith.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;


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
        rvNewIn.setAdapter(mNewInAdapter);
        rvNewIn.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));

        /*tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_crop_din_black_24dp));*/

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
        });

        NewProductModel.getObjInstance().loadNewProductList();
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NewProductModel.getObjInstance().forceRefreshNewsList();
            }
        });

        //vpEmpty.setEmptyData(R.drawable.placeholder_image, getString(R.string.empty_msg));
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
        swipeRefreshLayout.setRefreshing(false);
    }
}
