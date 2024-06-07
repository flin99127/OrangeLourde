package controleur;

public class Produit 
{
    private int idproduit;
    private String designation;
    private float prixAchat;
    private String dateAchat, categorie;
    private int idClient;

    public Produit(int idproduit, String designation, float prixAchat, String dateAchat, String categorie, int idClient) 
    {
        this.idproduit = idproduit;
        this.designation = designation;
        this.prixAchat = prixAchat;
        this.dateAchat = dateAchat;
        this.categorie = categorie;
        this.idClient = idClient;
    }

    public Produit(String designation, float prixAchat, String dateAchat, String categorie, int idClient) 
    {
        this.designation = designation;
        this.prixAchat = prixAchat;
        this.dateAchat = dateAchat;
        this.categorie = categorie;
        this.idClient = idClient;
    }

    public int getIdproduit() 
    {
        return idproduit;
    }
    public void setIdproduit(int idproduit) 
    {
        this.idproduit = idproduit;
    }

    public String getDesignation() 
    {
        return designation;
    }
    public void setDesignation(String designation) 
    {
        this.designation = designation;
    }

    public float getPrixAchat() {
        return prixAchat;
    }
    public void setPrixAchat(float prixAchat) 
    {
        this.prixAchat = prixAchat;
    }

    public String getDateAchat() 
    {
        return dateAchat;
    }
    public void setDateAchat(String dateAchat) 
    {
        this.dateAchat = dateAchat;
    }

    public String getCategorie() 
    {
        return categorie;
    }
    public void setCategorie(String categorie) 
    {
        this.categorie = categorie;
    }

    public int getIdClient() 
    {
        return idClient;
    }
    public void setIdClient(int idClient) 
    {
        this.idClient = idClient;
    }
}
