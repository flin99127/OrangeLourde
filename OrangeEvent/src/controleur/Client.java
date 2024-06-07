package controleur;

public class Client 
{
    private int idClient;
    private String nom, prenom, adresse, email;

    public Client(int idClient, String nom, String prenom, String adresse, String email) 
    {
        this.idClient = idClient;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
    }
    
    public Client(String nom, String prenom, String adresse, String email) 
    {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
    }

    public int getIdClient() 
    {
        return idClient;
    }
    public void setIdClient(int idClient) 
    {
        this.idClient = idClient;
    }

    public String getNom() 
    {
        return nom;
    }
    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public String getPrenom() 
    {
        return prenom;
    }
    public void setPrenom(String prenom) 
    {
        this.prenom = prenom;
    }

    public String getAdresse() 
    {
        return adresse;
    }
    public void setAdresse(String adresse) 
    {
        this.adresse = adresse;
    }

    public String getEmail() 
    {
        return email;
    }
    public void setEmail(String email) 
    {
        this.email = email;
    }
}