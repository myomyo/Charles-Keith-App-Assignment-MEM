package com.mem.charleskeith.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


import com.mem.charleskeith.R;
import com.mem.charleskeith.adapters.NewInAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewInActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_new_in)
    RecyclerView rvNewIn;

    private NewInAdapter mNewInAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_in);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this,this);

        mNewInAdapter = new NewInAdapter();
        rvNewIn.setAdapter(mNewInAdapter);
        rvNewIn.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));



    }
}
