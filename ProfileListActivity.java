package com.teliapp.moov.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.teliapp.moov.R;
import com.teliapp.moov.adapters.RecyclerListAdapter;
import com.teliapp.moov.models.UserService;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public abstract class ProfileListActivity<T> extends ActionBarActivity {

    private List<T> mList;
    protected String mUserToken;
    protected int mUserId;
    private RecyclerListAdapter mListAdapter;

    @InjectView(R.id.add_new_item)
    TextView addItemTextView;
    @InjectView(R.id.drawer_list_items)
    RecyclerView recyclerView;
    @InjectView(R.id.drawer_list_items_progress)
    ProgressBar listProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_my_list);
        ButterKnife.inject(this);

        addItemTextView.setText(getAddItemText());

        mUserId = UserService.loadId(this);
        mUserToken = UserService.loadAccessToken(this);

        getList();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        listProgress.setVisibility(View.VISIBLE);
        getList();
    }

    protected abstract int getAddItemText();

    protected abstract void getList();

    protected void setAdapter(List<T> list) {
        if (mListAdapter == null) {
            mListAdapter = getAdapter(mList = list);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(mListAdapter);
        } else {
            mList.clear();
            mList.addAll(list);
            mListAdapter.notifyDataSetChanged();
        }
        listProgress.setVisibility(View.GONE);
    }

    protected abstract RecyclerListAdapter getAdapter(List<T> list);

    @OnClick(R.id.add_new_item)
    void onAddNewVehicleClick() {
        startActivity(getStartIntent());
    }

    protected abstract Intent getStartIntent();
}
