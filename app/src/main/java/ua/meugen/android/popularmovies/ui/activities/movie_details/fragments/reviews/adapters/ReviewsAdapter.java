package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ua.meugen.android.popularmovies.app.di.PerFragment;
import ua.meugen.android.popularmovies.databinding.ItemReviewBinding;
import ua.meugen.android.popularmovies.model.db.entity.ReviewItem;
import ua.meugen.android.popularmovies.ui.activities.base.BaseActivityModule;

/**
 * @author meugen
 */
@PerFragment
public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewItemViewHolder> {

    private final LayoutInflater inflater;
    private final OnClickReviewListener listener;

    private List<ReviewItem> reviews;

    @Inject
    public ReviewsAdapter(
            @Named(BaseActivityModule.ACTIVITY_CONTEXT) final Context context,
            final OnClickReviewListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
        this.reviews = Collections.emptyList();
    }

    public void swapReviews(final List<ReviewItem> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @Override
    public ReviewItemViewHolder onCreateViewHolder(
            final ViewGroup parent, final int viewType) {
        return new ReviewItemViewHolder(ItemReviewBinding
                .inflate(inflater, parent, false));
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

        private final ItemReviewBinding binding;

        public ReviewItemViewHolder(final ItemReviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setHolder(this);
        }

        public void bind(final ReviewItem review) {
            binding.setReview(review);
        }

        public void click() {
            if (listener != null) {
                listener.onClickReview(binding.getReview());
            }
        }
    }

}
