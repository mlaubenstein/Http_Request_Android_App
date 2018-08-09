package com.example.marvin.http_requests;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marvin.http_requests.Data.Item;

import java.util.List;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder>{
    private List<Item> mItems;
    private Context mContext;
    private ItemClickListener mItemListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView titleTextview;
        ItemClickListener mItemListener;

        public ViewHolder(View itemView, ItemClickListener itemListener) {
            super(itemView);
            titleTextview = (TextView) itemView.findViewById(android.R.id.text1);

            this.mItemListener = itemListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            Item item = getItem(getAdapterPosition());
            this.mItemListener.onItemClick(item);
            notifyDataSetChanged();
        }
    }

    public AnswersAdapter(Context context, List<Item> posts, ItemClickListener itemListener) {
        mItems          = posts;
        mContext        = context;
        mItemListener   = itemListener;
    }


    @Override
    public AnswersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView, this.mItemListener);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(AnswersAdapter.ViewHolder holder, int position) {

        Item item = mItems.get(position);

        TextView textView = holder.titleTextview;
        textView.setText(item.getOwner().getDisplayName());
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void updateAnswers(List<Item> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    private Item getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public interface ItemClickListener {
        void onItemClick(Item item);
    }
}
