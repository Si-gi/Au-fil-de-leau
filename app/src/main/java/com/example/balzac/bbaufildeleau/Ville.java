package com.example.balzac.bbaufildeleau;

public class Ville {
    private String Nom = null;
    private int nbPointInteret = 0;

    public Ville() {
        super();
        this.Nom = "";
        this.nbPointInteret = 0;
    }

    /*public Ville(String nom, int nbpointinteret) {
        super();
        Nom = nom;
        nbPointInteret = nbpointinteret;
    }*/

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public int getNbPointInteret() {
        return nbPointInteret;
    }

    public void setNbPointInteret(int nbPointInteret) {
        this.nbPointInteret = nbPointInteret;
    }
}
