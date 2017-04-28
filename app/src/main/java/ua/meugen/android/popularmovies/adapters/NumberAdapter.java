package ua.meugen.android.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.NumberViewHolder> {

    private final Context context;
    private final int number;

    public NumberAdapter(final Context context, final int number) {
        this.context = context;
        this.number = number;
    }

    @Override
    public NumberViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new NumberViewHolder(LayoutInflater.from(context)
                .inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    @Override
    public void onBindViewHolder(final NumberViewHolder holder, final int position) {
        holder.bind(number);
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public static class NumberViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        public NumberViewHolder(final View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(android.R.id.text1);
        }

        public void bind(final int number) {
            textView.setText("" + number + " - " + getAdapterPosition());
        }
    }
}
