package com.melroy.smartpantrymanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.melroy.smartpantrymanager.R;
import com.melroy.smartpantrymanager.model.PantryItem;

import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private final List<PantryItem> pantryItems;

    public PantryAdapter(List<PantryItem> pantryItems) {
        this.pantryItems = pantryItems;
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);
        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {
        PantryItem item = pantryItems.get(position);

        holder.textName.setText(item.getName());

        String details = item.getQuantity() + " " + item.getUnit();
        if (item.getExpiryDate() != null && !item.getExpiryDate().isEmpty()) {
            details += ", expires " + item.getExpiryDate();
        }
        holder.textDetails.setText(details);
    }

    @Override
    public int getItemCount() {
        return pantryItems.size();
    }

    // Holds references to the views inside a single pantry item row.
    public static class PantryViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textDetails;

        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textPantryItemName);
            textDetails = itemView.findViewById(R.id.textPantryItemDetails);
        }
    }
}
