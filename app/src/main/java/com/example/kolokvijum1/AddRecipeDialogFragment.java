package com.example.kolokvijum1;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddRecipeDialogFragment extends DialogFragment {

    public interface NaRecepceDodatListener {
        void onRecepatDodat(Recipe noviRecept);
    }

    private NaRecepceDodatListener listener;

    public void setNaRecepceDodatListener(NaRecepceDodatListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View pogled = inflater.inflate(R.layout.dialog_add_recipe, null);

        EditText unosNaziva = pogled.findViewById(R.id.unosNaziva);
        EditText unosVremena = pogled.findViewById(R.id.unosVremena);
        CheckBox cekOmiljeno = pogled.findViewById(R.id.cekOmiljeno);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(R.string.novi_recept);
        builder.setView(pogled);

        AlertDialog dijalog = builder.create();

        pogled.findViewById(R.id.dugmeOtkazi).setOnClickListener(v -> dijalog.dismiss());

        pogled.findViewById(R.id.dugmePotvrdi).setOnClickListener(v -> {
            String naziv = unosNaziva.getText().toString().trim();
            String vremeTekst = unosVremena.getText().toString().trim();

            if (TextUtils.isEmpty(naziv) || TextUtils.isEmpty(vremeTekst)) {
                Toast.makeText(getContext(), R.string.popuni_polja, Toast.LENGTH_SHORT).show();
                return;
            }

            int vreme = Integer.parseInt(vremeTekst);
            Recipe noviRecept = new Recipe(naziv, vreme, cekOmiljeno.isChecked());

            if (listener != null) {
                listener.onRecepatDodat(noviRecept);
            }
            dijalog.dismiss();
        });

        return dijalog;
    }
}
