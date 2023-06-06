package com.example.balzac.bbaufildeleau;

public class PointInteret {
    private String recordid = null;
    private int identifiant = 0;
    private int objectid = 0;
    private String elemPatri = null;
    private String comunne = null;
    private String ensem2 = null;
    private String dateConstruc = null;
    private String commentaire = null;
    private String elemPrincip = null;
    private int favorite = 0;
    private String locationX = null;
    private String locationY = null;
    private String PictureLink = null;

    public PointInteret() {
        super();
        this.recordid = "";
        this.identifiant = 0;
        this.objectid = 0;
        this.elemPatri = "";
        this.comunne = "";
        this.ensem2 = "";
        this.dateConstruc = "";
        this.commentaire = "";
        this.elemPrincip = "";
        this.favorite = 0;
    }

    public String getRecordid() {
        return recordid;
    }

    public void setRecordid(String recordid) {
        this.recordid = recordid;
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    public int getObjectid() {
        return objectid;
    }

    public void setObjectid(int objectid) {
        this.objectid = objectid;
    }

    public String getElemPatri() {
        return elemPatri;
    }

    public void setElemPatri(String elemPatri) {
        this.elemPatri = elemPatri;
    }

    public String getComunne() {
        return comunne;
    }

    public void setComunne(String comunne) {
        this.comunne = comunne;
    }

    public String getEnsem2() {
        return ensem2;
    }

    public void setEnsem2(String ensem2) {
        this.ensem2 = ensem2;
    }

    public String getDateConstruc() {
        return dateConstruc;
    }

    public void setDateConstruc(String dateConstruc) {
        this.dateConstruc = dateConstruc;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getElemPrincip() {
        return elemPrincip;
    }

    public void setElemPrincip(String elemPrincip) {
        this.elemPrincip = elemPrincip;
    }

    public int getFavorite() { return favorite; }

    public void setFavorite(int favorite) { this.favorite = favorite; }

    public String getLocationX() { return locationX; }

    public void setLocationX(String locationX) { this.locationX = locationX; }

    public String getLocationY() { return locationY; }

    public void setLocationY(String locationY) { this.locationY = locationY; }

    public String getPictureLink() { return PictureLink; }

    public void setPictureLink(String pictureLink) { PictureLink = pictureLink; }
}
