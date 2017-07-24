package ua.meugen.android.popularmovies.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.databinding.ItemReviewBinding;
import ua.meugen.android.popularmovies.model.dto.ReviewItemDto;
import ua.meugen.android.popularmovies.viewmodel.MovieReviewItemViewModel;
import ua.meugen.android.popularmovies.viewmodel.listeners.OnClickReviewListener;

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
    public ReviewItemViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new ReviewItemViewHolder(ItemReviewBinding.inflate(inflater, parent, false));
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
        }

        public void bind(final ReviewItemDto dto) {
            if (binding.getModel() == null) {
                binding.setModel(new MovieReviewItemViewModel(dto, listener));
            } else {
                binding.getModel().setReview(dto);
            }
        }
    }

}
