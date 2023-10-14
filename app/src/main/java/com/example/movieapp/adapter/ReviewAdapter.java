package com.example.movieapp.adapter;

import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.models.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private static final String POSITIVE_TYPE = "Позитивный";
    private static final String NEGATIVE_TYPE = "Негативный";
    private static final String TAG = "ReviewAdapter";

    private List<Review> reviewList = new ArrayList<>();

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.reviewContentTV.setText(review.getContent());
        holder.reviewTitleTV.setText(review.getTitle());
        holder.reviewAuthorTV.setText(review.getAuthor());

        if (review.getType() != null) {
            int backgroundId;
            String type = review.getType();

            switch (type) {
                case POSITIVE_TYPE:
                    backgroundId = android.R.color.holo_green_light;
                    break;
                case NEGATIVE_TYPE:
                    backgroundId = android.R.color.holo_red_light;
                    break;
                default:
                    backgroundId = android.R.color.holo_orange_light;
            }
            Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(), backgroundId);
            holder.reviewContainerLayout.setBackground(drawable);
        }
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout reviewContainerLayout;
        private final TextView reviewAuthorTV;
        private final TextView reviewTitleTV;
        private final TextView reviewContentTV;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            reviewContainerLayout = itemView.findViewById(R.id.reviewContainerLayout);
            reviewAuthorTV = itemView.findViewById(R.id.reviewAuthorTV);
            reviewTitleTV = itemView.findViewById(R.id.reviewTitleTV);
            reviewContentTV = itemView.findViewById(R.id.reviewContentTV);
        }
    }
}
