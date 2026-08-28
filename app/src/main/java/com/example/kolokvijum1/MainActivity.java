package com.example.kolokvijum1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<String> pokretacDozvoleKamere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbarGlavni);
        setSupportActionBar(toolbar);

        pokretacDozvoleKamere = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    // servis periodicno proverava status, nista dodatno ovde nije potrebno
                }
        );

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            pokretacDozvoleKamere.launch(Manifest.permission.CAMERA);
        }

        Intent servisIntent = new Intent(this, CameraStatusService.class);
        startService(servisIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.stavkaRecept) {
            prikaziRecipeFragment();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void prikaziRecipeFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.kontejnerFragmenta, new RecipeFragment())
                .addToBackStack(null)
                .commit();
    }
}
