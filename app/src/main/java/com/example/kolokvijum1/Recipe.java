package com.example.kolokvijum1;

public class Recipe {

    private String naziv;
    private int vremePripreme;
    private boolean omiljeno;

    public Recipe(String naziv, int vremePripreme, boolean omiljeno) {
        this.naziv = naziv;
        this.vremePripreme = vremePripreme;
        this.omiljeno = omiljeno;
    }

    public String getNaziv() {
        return naziv;
    }

    public int getVremePripreme() {
        return vremePripreme;
    }

    public boolean isOmiljeno() {
        return omiljeno;
    }
}
