package ua.meugen.android.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.dto.ReviewItemDto;

/**
 * @author meugen
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewItemViewHolder> {

    private final LayoutInflater inflater;
    private final List<ReviewItemDto> reviews;

    private OnClickReviewListener onClickReviewListener;

    public ReviewsAdapter(final Context context, final List<ReviewItemDto> reviews) {
        this.inflater = LayoutInflater.from(context);
        this.reviews = reviews;
    }

    @Override
    public ReviewItemViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = inflater.inflate(R.layout.item_review, parent, false);
        return new ReviewItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReviewItemViewHolder holder, final int position) {
        holder.bind(reviews.get(position));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public OnClickReviewListener getOnClickReviewListener() {
        return onClickReviewListener;
    }

    public void setOnClickReviewListener(final OnClickReviewListener onClickReviewListener) {
        this.onClickReviewListener = onClickReviewListener;
    }

    private void callOnClickReviewListener(final int position) {
        if (onClickReviewListener != null) {
            onClickReviewListener.onClickReview(reviews.get(position));
        }
    }

    public class ReviewItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView contentView;
        private final TextView authorView;

        public ReviewItemViewHolder(final View itemView) {
            super(itemView);
            contentView = (TextView) itemView.findViewById(R.id.content);
            authorView = (TextView) itemView.findViewById(R.id.author);
        }

        public void bind(final ReviewItemDto dto) {
            contentView.setText(dto.getContent());
            authorView.setText(dto.getAuthor());
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            callOnClickReviewListener(getAdapterPosition());
        }
    }

    public interface OnClickReviewListener {

        void onClickReview(ReviewItemDto dto);
    }
}
