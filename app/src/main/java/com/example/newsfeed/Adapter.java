package com.example.newsfeed;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.newsfeed.models.Article;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Article> articles;
    private Context context;
    private OnItemClickListener onItemClickListener;


    public Adapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, int position) {
        final MyViewHolder holder = holders;
        Article models = articles.get(position);



        Glide.with(context)
                .load(models.getUrlToImage())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);

        holder.title.setText(models.getTitle());
        holder.desc.setText(models.getDescription());
        holder.author.setText(models.getAuthor());
        holder.publishedAt.setText(Utils.DateFormat(models.getPublishedAt()));

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title, desc, author, publishedAt;
        ImageView imageView;
        OnItemClickListener onItemClickListener;
        public MyViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            author = itemView.findViewById(R.id.author);
            publishedAt = itemView.findViewById(R.id.publishedAt);
            imageView = itemView.findViewById(R.id.img);

            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(view, getAdapterPosition());

        }
    }
}
