package com.example.pet_care;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private List<Pet> searchResults;

    public void setSearchResults(List<Pet> searchResults) {
        this.searchResults = searchResults;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pet pet = searchResults.get(position);
        holder.textViewPetName.setText(pet.getName());
        holder.textViewPetType.setText(pet.getType());
        holder.textViewPetSex.setText(pet.getSex());
        holder.textViewPetAge.setText(pet.getAge());
        holder.textViewPetNote.setText(pet.getNote());

    }

    @Override
    public int getItemCount() {
        return searchResults != null ? searchResults.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewPetName;
        TextView textViewPetType;
        TextView textViewPetSex;
        TextView textViewPetAge;
        TextView textViewPetNote;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPetName = itemView.findViewById(R.id.textPetName);
            textViewPetType = itemView.findViewById(R.id.textPetType);
            textViewPetSex = itemView.findViewById(R.id.textPetSex);
            textViewPetAge = itemView.findViewById(R.id.textPetAge);
            textViewPetNote = itemView.findViewById(R.id.textPetNote);

        }
    }
}
