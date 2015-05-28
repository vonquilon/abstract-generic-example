package com.teliapp.moov.adapters;

import android.content.Context;
import android.content.Intent;

import com.teliapp.moov.activities.ProfileAddNewKid;
import com.teliapp.moov.models.Kid;

import java.util.List;

public class RecyclerKidListAdapter extends RecyclerListAdapter<Kid> {

    public RecyclerKidListAdapter(List<Kid> list) {
        super(list);
    }

    @Override
    protected String getName(Kid current) {
        return current.getFirstName();
    }

    @Override
    protected Intent getIntent(Context context, Kid current) {
        Intent intent = new Intent(context, ProfileAddNewKid.class);
        intent.putExtra("kid_id", current.getKidId());
        intent.putExtra("message", "update kid");
        return intent;
    }
}
