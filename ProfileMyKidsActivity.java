package com.teliapp.moov.activities;

import android.content.Intent;
import android.util.Log;

import com.teliapp.moov.ApiClient;
import com.teliapp.moov.R;
import com.teliapp.moov.adapters.RecyclerKidListAdapter;
import com.teliapp.moov.adapters.RecyclerListAdapter;
import com.teliapp.moov.database.Database;
import com.teliapp.moov.database.DatabaseCallback;
import com.teliapp.moov.models.Kid;
import com.teliapp.moov.models.KidListResponse;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ProfileMyKidsActivity extends ProfileListActivity<Kid> implements DatabaseCallback<List<Kid>>,
        Callback<KidListResponse> {

    @Override
    public void onDatabaseComplete(List<Kid> result) {
        if (result == null || result.isEmpty()) {
            Log.e(null, "Kids database is empty");
            ApiClient.getRetrofitApiClient().kidsList(mUserId, mUserToken, this);
        } else {
            Log.e(null, "Kids database is not empty");
            setAdapter(result);
        }
    }

    @Override
    public void success(KidListResponse kidListResponse, Response response) {
        Log.e("Retrofit Kids List", "success");

        setAdapter(kidListResponse.responseData);
        Database.addOrUpdateKid(this, kidListResponse.responseData, null);
    }

    @Override
    public void failure(RetrofitError error) {
        Log.e("Retrofit Kids List", "fail");
    }

    @Override
    protected int getAddItemText() {
        return R.string.title_add_kid;
    }

    @Override
    protected void getList() {
        Database.getKids(this, this, null);
    }

    @Override
    protected RecyclerListAdapter getAdapter(List<Kid> list) {
        return new RecyclerKidListAdapter(list);
    }

    @Override
    protected Intent getStartIntent() {
        Intent intent = new Intent(this, ProfileAddNewKid.class);
        intent.putExtra("message", "add kid");
        return intent;
    }
}
