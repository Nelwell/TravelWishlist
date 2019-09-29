package android.bignerdranch.travelwishlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListViewHolder> {

    // Adapter's internal data store
    private List<Place> data;

    // Click and long-click listener
    private WishListClickListener listener;

    // Constructor to set data
    public WishListAdapter(List<Place> data, WishListClickListener listener) {
        this.listener = listener;
        this.data = data;
    }

    // Objects of this class represent the view for one data item
    static class WishListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener{

        LinearLayout layout;
        TextView nameTextView;
        TextView dateCreatedTextView;
        TextView reasonTextView;

        WishListClickListener listener;

        WishListViewHolder(LinearLayout layout, WishListClickListener listener) {
            super(layout);
            this.listener = listener;
            this.layout = layout;
            nameTextView = layout.findViewById(R.id.placeNameTextView);
            dateCreatedTextView = layout.findViewById(R.id.dateCreatedTextView);
            reasonTextView = layout.findViewById(R.id.reasonToGoTextView);
            layout.setOnClickListener(this);
            layout.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Notify the listener of the event, and which item was clicked
            listener.onListClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            // Notify listener of the event and which item was long-clicked
            listener.onListLongClick(getAdapterPosition());
            return true; // Indicates event is consumed, no further processing.
            // If this is false, in this app, the click event is fired too.
        }
    }

    @NonNull
    @Override
    public WishListAdapter.WishListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Get a reference to the wish_list_element LinearLayout container and inflate in, in this context
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wish_list_element, parent, false);
        // Create a new viewHolder, to contain this LinearLayour
        WishListViewHolder viewHolder = new WishListViewHolder(layout, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.WishListViewHolder holder, int position) {
        // Configures a ViewHolder to display the dta for he given position
        // In Android terminology, bind the view and its data
        Place place = data.get(position);
        holder.nameTextView.setText(place.getName());
        holder.dateCreatedTextView.setText("Created on " + place.getDateCreated());
        holder.reasonTextView.setText(place.getReason());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
