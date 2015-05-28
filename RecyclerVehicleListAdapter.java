package com.teliapp.moov.adapters;

import android.content.Context;
import android.content.Intent;

import com.teliapp.moov.activities.ProfileAddNewVehicle;
import com.teliapp.moov.models.Vehicle;

import java.util.List;

public class RecyclerVehicleListAdapter extends RecyclerListAdapter<Vehicle> {

    public RecyclerVehicleListAdapter(List<Vehicle> list) {
        super(list);
    }

    @Override
    protected String getName(Vehicle current) {
        return current.getModel();
    }

    @Override
    protected Intent getIntent(Context context, Vehicle current) {
        Intent intent = new Intent(context, ProfileAddNewVehicle.class);
        intent.putExtra("vehicle_id", current.getVehicleId());
        intent.putExtra("message", "update vehicle");
        return intent;
    }
}
