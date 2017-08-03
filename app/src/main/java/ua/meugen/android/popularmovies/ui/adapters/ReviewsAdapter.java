package ua.meugen.android.popularmovies.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.model.responses.ReviewItemDto;
import ua.meugen.android.popularmovies.presenter.listeners.OnClickReviewListener;

/**
 * @author meugen
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewItemViewHolder> {

    private final LayoutInflater inflater;
    private final OnClickReviewListener listener;

    private List<ReviewItemDto> reviews;

    public ReviewsAdapter(
            final Context context,
            final OnClickReviewListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
        this.reviews = Collections.emptyList();
    }

    public void setReviews(final List<ReviewItemDto> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @Override
    public ReviewItemViewHolder onCreateViewHolder(
            final ViewGroup parent, final int viewType) {
        return new ReviewItemViewHolder(inflater.inflate(
                R.layout.item_review, parent, false));
    }

    @Override
    public void onBindViewHolder(final ReviewItemViewHolder holder, final int position) {
        holder.bind(reviews.get(position));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.content) TextView contentView;
        @BindView(R.id.author) TextView authorView;

        private ReviewItemDto review;

        public ReviewItemViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final ReviewItemDto dto) {
            review = dto;
            contentView.setText(dto.getContent());
            authorView.setText(dto.getAuthor());
        }

        @OnClick(R.id.container)
        public void click() {
            if (listener != null) {
                listener.onClickReview(review);
            }
        }
    }

}
