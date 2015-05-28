package com.teliapp.moov.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teliapp.moov.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public abstract class RecyclerListAdapter<T> extends RecyclerView.Adapter<RecyclerListAdapter.MyViewHolder> {

    private List<T> mList;

    public RecyclerListAdapter(List<T> list) {
        mList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_row, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final T current = mList.get(position);
        holder.name.setText(getName(current));

        if (position % 2 != 0) {
            holder.name.setBackgroundColor(Color.parseColor("#f1f2f2"));
        } else {
            holder.name.setBackgroundColor(Color.WHITE);
        }

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                context.startActivity(getIntent(context, current));
            }
        });
    }

    protected abstract String getName(T current);

    protected abstract Intent getIntent(Context context, T current);

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.custom_list_row_text_view)
        TextView name;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
