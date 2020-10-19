package com.shruti.searchapplication.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.shruti.searchapplication.R;
import com.shruti.searchapplication.dao.ArticlesResponse;
import com.shruti.searchapplication.databinding.ItemArticleBinding;

import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.MyViewHolder> {

    private List<ArticlesResponse.Docs> docs;
    private LayoutInflater layoutInflater;

    public ArticlesAdapter(List<ArticlesResponse.Docs> docs) {
        this.docs = docs;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        ItemArticleBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_article, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.setArticles(docs.get(position));

    }

    @Override
    public int getItemCount() {
        return docs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ItemArticleBinding binding;

        public MyViewHolder(@NonNull ItemArticleBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
