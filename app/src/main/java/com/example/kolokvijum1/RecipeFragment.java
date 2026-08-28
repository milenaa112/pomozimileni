package com.example.kolokvijum1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecipeFragment extends Fragment implements AddRecipeDialogFragment.NaRecepceDodatListener {

    private RecipeAdapter adapterRecepata;
    private final List<Recipe> listaRecepata = new ArrayList<>();
    private ImageView slikaKamera;

    private final BroadcastReceiver kameraReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (slikaKamera != null) {
                slikaKamera.setVisibility(View.VISIBLE);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerRecepti = view.findViewById(R.id.recyclerRecepti);
        slikaKamera = view.findViewById(R.id.slikaKamera);
        Button dugmeDodaj = view.findViewById(R.id.dugmeDodaj);

        adapterRecepata = new RecipeAdapter(listaRecepata);
        recyclerRecepti.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerRecepti.setAdapter(adapterRecepata);

        dugmeDodaj.setOnClickListener(v -> otvoriFormuZaDodavanje());
    }

    private void otvoriFormuZaDodavanje() {
        AddRecipeDialogFragment dijalog = new AddRecipeDialogFragment();
        dijalog.setNaRecepceDodatListener(this);
        dijalog.show(getParentFragmentManager(), "formaZaRecept");
    }

    @Override
    public void onRecepatDodat(Recipe noviRecept) {
        adapterRecepata.dodajRecept(noviRecept);
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(requireContext())
                .registerReceiver(kameraReceiver,
                        new IntentFilter(CameraStatusService.AKCIJA_KAMERA_DOZVOLJENA));
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(requireContext())
                .unregisterReceiver(kameraReceiver);
    }
}
