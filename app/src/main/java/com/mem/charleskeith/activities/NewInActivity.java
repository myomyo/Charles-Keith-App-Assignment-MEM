package com.mem.charleskeith.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


import com.mem.charleskeith.R;
import com.mem.charleskeith.adapters.NewInAdapter;
import com.mem.charleskeith.delegates.NewInDelegate;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewInActivity extends BaseActivity implements NewInDelegate{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_new_in)
    RecyclerView rvNewIn;

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


    }

    @Override
    public void onTapItem() {
        Intent intent = new Intent(getApplicationContext(), NewInDetailsActivity.class);
        startActivity(intent);
    }
}
