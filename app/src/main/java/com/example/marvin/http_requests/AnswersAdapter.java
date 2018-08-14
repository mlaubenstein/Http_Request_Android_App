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
    private List<Item> Items;
    private ItemClickListener ItemListener;
    private Context Context;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView            titleTextview;
        private ItemClickListener   ItemListener;

        ViewHolder(View itemView, ItemClickListener itemListener) {
            super(itemView);

            titleTextview = itemView.findViewById(android.R.id.text1);
            this.ItemListener = itemListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            Item item = getItem(getAdapterPosition());
            this.ItemListener.onItemClick(item);
            notifyDataSetChanged();
        }
    }

    AnswersAdapter(Context context, List<Item> posts, ItemClickListener itemListener) {
        Items         = posts;
        Context       = context;
        ItemListener  = itemListener;
    }


    @Override
    public AnswersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context         = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView           = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        return new ViewHolder(postView, this.ItemListener);
    }


    @Override
    public void onBindViewHolder(AnswersAdapter.ViewHolder holder, int position) {

        Item item           = this.Items.get(position);
        TextView textView   = holder.titleTextview;
                 textView.setText(item.getOwner().getDisplayName());

    }


    @Override
    public int getItemCount() {
        return Items.size();
    }

    public void updateAnswers(List<Item> items) {
        this.Items = items;
        notifyDataSetChanged();
    }

    private Item getItem(int adapterPosition) {
        return this.Items.get(adapterPosition);
    }

    public interface ItemClickListener {
        void onItemClick(Item item);
    }
}
