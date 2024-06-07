package controleur;

public class Intervention 
{
    private int idinter;
    private String description;
    private float prixInter;
    private String dateInter;
    private int intproduit, idtechnicien;

    public Intervention(int idinter, String description, float prixInter, String dateInter, int intproduit, int idtechnicien) 
    {
        this.idinter = idinter;
        this.description = description;
        this.prixInter = prixInter;
        this.dateInter = dateInter;
        this.intproduit = intproduit;
        this.idtechnicien = idtechnicien;
    }

    public Intervention(String description, float prixInter, String dateInter, int intproduit, int idtechnicien) 
    {
        this.description = description;
        this.prixInter = prixInter;
        this.dateInter = dateInter;
        this.intproduit = intproduit;
        this.idtechnicien = idtechnicien;
    }

    public int getIdinter() 
    {
        return idinter;
    }
    public void setIdinter(int idinter) 
    {
        this.idinter = idinter;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public float getPrixInter() 
    {
        return prixInter;
    }
    public void setPrixInter(float prixInter) 
    {
        this.prixInter = prixInter;
    }

    public String getDateInter() 
    {
        return dateInter;
    }
    public void setDateInter(String dateInter) 
    {
        this.dateInter = dateInter;
    }

    public int getIntproduit() 
    {
        return intproduit;
    }
    public void setIntproduit(int intproduit) 
    {
        this.intproduit = intproduit;
    }

    public int getIdtechnicien() 
    {
        return idtechnicien;
    }
    public void setIdtechnicien(int idtechnicien) 
    {
        this.idtechnicien = idtechnicien;
    }
    
}
