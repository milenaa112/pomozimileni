package com.example.kolokvijum1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final List<Recipe> listaRecepata;

    public RecipeAdapter(List<Recipe> listaRecepata) {
        this.listaRecepata = listaRecepata;
    }

    public void dodajRecept(Recipe recept) {
        listaRecepata.add(recept);
        notifyItemInserted(listaRecepata.size() - 1);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View pogled = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(pogled);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recept = listaRecepata.get(position);
        holder.tekstNaziv.setText(recept.getNaziv());
        holder.tekstVreme.setText(recept.getVremePripreme() + " min");
        holder.tekstOmiljeno.setText(recept.isOmiljeno() ? "Omiljen recept" : "");
    }

    @Override
    public int getItemCount() {
        return listaRecepata.size();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView tekstNaziv;
        TextView tekstVreme;
        TextView tekstOmiljeno;

        RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            tekstNaziv = itemView.findViewById(R.id.tekstNaziv);
            tekstVreme = itemView.findViewById(R.id.tekstVreme);
            tekstOmiljeno = itemView.findViewById(R.id.tekstOmiljeno);
        }
    }
}
