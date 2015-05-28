package com.teliapp.moov.activities;

import android.content.Intent;
import android.util.Log;

import com.teliapp.moov.ApiClient;
import com.teliapp.moov.R;
import com.teliapp.moov.adapters.RecyclerListAdapter;
import com.teliapp.moov.adapters.RecyclerVehicleListAdapter;
import com.teliapp.moov.database.Database;
import com.teliapp.moov.database.DatabaseCallback;
import com.teliapp.moov.models.Vehicle;
import com.teliapp.moov.models.VehicleListResponse;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ProfileMyVehiclesActivity extends ProfileListActivity<Vehicle> implements DatabaseCallback<List<Vehicle>>,
        Callback<VehicleListResponse> {

    @Override
    public void onDatabaseComplete(List<Vehicle> result) {
        if (result != null && !result.isEmpty()) {
            Log.e(null, "Vehicles database is not empty");
            setAdapter(result);
        } else {
            Log.e(null, "Vehicles database is empty");
            ApiClient.getRetrofitApiClient().vehiclesList(mUserId, mUserToken, this);
        }
    }

    @Override
    public void success(VehicleListResponse vehicleListResponse, Response response) {
        Log.e("Retrofit Vehicles List", "success");

        Database.addOrUpdateVehicle(this, vehicleListResponse.responseData, null);
        setAdapter(vehicleListResponse.responseData);
    }

    @Override
    public void failure(RetrofitError error) {
        Log.e("Retrofit Vehicles List", "failure");
    }

    @Override
    protected int getAddItemText() {
        return R.string.title_add_new_vehicle;
    }

    @Override
    protected void getList() {
        Database.getVehicles(this, this, null);
    }

    @Override
    protected RecyclerListAdapter getAdapter(List<Vehicle> list) {
        return new RecyclerVehicleListAdapter(list);
    }

    @Override
    protected Intent getStartIntent() {
        Intent intent = new Intent(this, ProfileAddNewVehicle.class);
        intent.putExtra("message", "add vehicle");
        return intent;
    }
}
