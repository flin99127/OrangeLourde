package modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controleur.Client;
import controleur.Intervention;
import controleur.Produit;
import controleur.Technicien;
import controleur.User;

public class Modele 
{
    private static BDD uneBdd = new BDD("localhost", "orange_efrei", "root", "");

    public static User verifConnexion(String email, String mdp)
    {
        User unUser = null;
        
        String req = "select * from user where email= '" + email + "' and mdp= '" + mdp + "'; ";
        
        try 
        {
            uneBdd.seConnecter();
            Statement unStat = uneBdd.getConnection().createStatement();
            ResultSet unRes = unStat.executeQuery(req);

            if(unRes.next()) 
            {
                unUser = new User(
                    unRes.getInt("iduser"), unRes.getString("nom"), unRes.getString("prenom"), unRes.getString("email"), unRes.getString("mdp"), unRes.getString("role")
                );
            }
            unStat.close();
            uneBdd.seDeconnecter();
        } 
        catch(SQLException exp) 
        {
            System.out.println("erreur de connexion" + req);
            exp.printStackTrace();
        }
        return unUser;
    }
    
    public static void updateUser(User unUser) 
    {
        String req = "UPDATE user SET nom = ?, prenom = ?, email = ?, mdp = ?, role = ? WHERE iduser = ?";

        try 
        {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getConnection().prepareStatement(req);
        
            unStat.setString(1, unUser.getNom());
            unStat.setString(2, unUser.getPrenom());
            unStat.setString(3, unUser.getEmail());
            unStat.setString(4, unUser.getMdp());
            unStat.setString(5, unUser.getRole());
            unStat.setInt(6, unUser.getIduser());

            unStat.executeUpdate();    
        }
        catch(Exception exp) 
        {
            System.out.println("Erreur execution requete : " + req);
            exp.printStackTrace();
        } 
        finally 
        {
            try 
            {
                if(uneBdd.getConnection() != null) 
                {
                    uneBdd.seDeconnecter();
                }
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
    }

    public static void insertClient(Client unClient) 
    {
        String req = "INSERT INTO client (nom, prenom, adresse, email) VALUES (?, ?, ?, ?)";

        try 
        {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getConnection().prepareStatement(req);

            unStat.setString(1, unClient.getNom());
            unStat.setString(2, unClient.getPrenom());
            unStat.setString(3, unClient.getAdresse());
            unStat.setString(4, unClient.getEmail());

            unStat.executeUpdate();
        } 
        catch(Exception exp) 
        {
            System.out.println("Erreur execution requete : " + req);
            exp.printStackTrace();
        } 
        finally 
        {
            try 
            {
                if (uneBdd.getConnection() != null) 
                {
                    uneBdd.seDeconnecter();
                }
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
    }


    public static ArrayList<Client> selectAllClients(String filtre) 
    {
        ArrayList<Client> lesClients = new ArrayList<Client>();
        String requete;
        boolean hasFiltre = !filtre.equals("");

        if(hasFiltre) 
        {
            requete = "SELECT * FROM client WHERE nom LIKE ? OR prenom LIKE ? OR adresse LIKE ?";
        } 
        else 
        {
            requete = "SELECT * FROM client";
        }

        try 
        {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getConnection().prepareStatement(requete);

            if(hasFiltre) 
            {
                String filterString = "%" + filtre + "%";
                unStat.setString(1, filterString);
                unStat.setString(2, filterString);
                unStat.setString(3, filterString);
            }

            ResultSet desRes = unStat.executeQuery();

            while(desRes.next()) 
            {
                Client unClient = new Client(
                    desRes.getInt("idclient"), 
                    desRes.getString("nom"),
                    desRes.getString("prenom"), 
                    desRes.getString("adresse"),
                    desRes.getString("email")
                );

                lesClients.add(unClient);
            }

            unStat.close();
            uneBdd.seDeconnecter();
            
        } 
        catch(SQLException exp) 
        {
            System.out.println("Erreur de connexion à la BDD : " + requete);
            exp.printStackTrace();
        }

            return lesClients;
    }


    public static void deleteClient(int idclient) 
    {
        String req = "DELETE FROM client WHERE idclient = ?";
    
        try 
        {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getConnection().prepareStatement(req);
    
            unStat.setInt(1, idclient);
    
            unStat.executeUpdate();
            unStat.close();
            uneBdd.seDeconnecter();
    
        } 
        catch(SQLException exp) 
        {
            System.out.println("Erreur execution requete: " + req);
            exp.printStackTrace();
        }
    }
    
    
    public static void updateClient(Client unClient) 
    {
        String req = "UPDATE client SET nom = ?, prenom = ?, adresse = ?, email = ? WHERE idclient = ?";
    
        try 
        {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getConnection().prepareStatement(req);
    
            unStat.setString(1, unClient.getNom());
            unStat.setString(2, unClient.getPrenom());
            unStat.setString(3, unClient.getAdresse());
            unStat.setString(4, unClient.getEmail());
            unStat.setInt(5, unClient.getIdClient());
    
            unStat.executeUpdate();
            unStat.close();
            uneBdd.seDeconnecter();
    
        } 
        catch(SQLException exp) 
        {
            System.out.println("Erreur execution requete: " + req);
            exp.printStackTrace();
        }
    }
    
    //////////////////////////////Produit
    public static void insertProduit(Produit unProduit) 
    {
        String req = "INSERT INTO produit (designation, prixAchat, dateAchat, categorie, idClient) VALUES (?, ?, ?, ?, ?)";
    
        try 
        {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getConnection().prepareStatement(req);
    
            unStat.setString(1, unProduit.getDesignation());
            unStat.setDouble(2, unProduit.getPrixAchat());
            unStat.setDate(3, java.sql.Date.valueOf(unProduit.getDateAchat())); // Assuming unProduit.getDateAchat() returns a LocalDate
            unStat.setString(4, unProduit.getCategorie());
            unStat.setInt(5, unProduit.getIdClient());
    
            unStat.executeUpdate();
            unStat.close();
            uneBdd.seDeconnecter();
    
        } 
        catch(SQLException exp) 
        {
            System.out.println("Erreur execution requete: " + req);
            exp.printStackTrace();
        }
    }
    
    public static ArrayList<Produit> selectAllProduits() 
    {
        ArrayList<Produit> lesProduits = new ArrayList<Produit>();
        String req = "SELECT * FROM produit";
    
        try 
        {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getConnection().prepareStatement(req);
            ResultSet desRes = unStat.executeQuery();
    
            while(desRes.next()) 
            {
                Produit unProduit = new Produit(
                    desRes.getInt("idproduit"),
                    desRes.getString("designation"),
                    desRes.getFloat("prixAchat"),
                    desRes.getString("dateAchat"),
                    desRes.getString("categorie"),
                    desRes.getInt("idclient")
                );
                lesProduits.add(unProduit);
            }
    
            unStat.close();
            uneBdd.seDeconnecter();
    
        } 
        catch(SQLException exp) 
        {
            System.out.println("Erreur execution requete: " + req);
            exp.printStackTrace();
        }
    
        return lesProduits;
    }

    public static ArrayList<Produit> selectAllProduits(String filtre) 
    {
        ArrayList<Produit> lesProduits = new ArrayList<>();
        String req;
    
        if(filtre == null || filtre.isEmpty()) 
        {
            req = "SELECT * FROM produit;";
        } 
        else 
        {
            req = "SELECT * FROM produit WHERE designation LIKE ? OR categorie LIKE ?";
        }
    
        try 
        {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getConnection().prepareStatement(req);
    
            if (filtre != null && !filtre.isEmpty()) 
            {
                String filterString = "" + filtre + "%";
                unStat.setString(1, filterString);
                unStat.setString(2, filterString);
            }
    
            ResultSet desRes = unStat.executeQuery();
    
            while(desRes.next()) 
            {
                Produit unProduit = new Produit(
                        desRes.getInt("idproduit"),
                        desRes.getString("designation"),
                        desRes.getFloat("prixAchat"),
                        desRes.getString("dateAchat"),
                        desRes.getString("categorie"),
                        desRes.getInt("idclient"));
    
                lesProduits.add(unProduit);
            }
    
            unStat.close();
            uneBdd.seDeconnecter();
    
        } catch (SQLException exp) {
            System.out.println("Erreur execution requete: " + req);
            exp.printStackTrace();
        }
    
        return lesProduits;
    }
    
    

    public static void updateProduit(Produit unProduit) 
    {
        String req = "UPDATE produit SET designation = ?, prixAchat = ?, dateAchat = ?, categorie = ?, idclient = ? WHERE idproduit = ?";
    
        try 
        {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getConnection().prepareStatement(req);
    
            unStat.setString(1, unProduit.getDesignation());
            unStat.setFloat(2, unProduit.getPrixAchat());
            unStat.setString(3, unProduit.getDateAchat()); 
            unStat.setString(4, unProduit.getCategorie());
            unStat.setInt(5, unProduit.getIdClient());
            unStat.setInt(6, unProduit.getIdproduit());
    
            unStat.executeUpdate();
            unStat.close();
            uneBdd.seDeconnecter();
    
        } 
        catch(SQLException exp) 
        {
            System.out.println("Erreur execution requete: " + req);
            exp.printStackTrace();
        }
    }

    public static void deleteProduit(int idproduit) {
        String req = "DELETE FROM produit WHERE idproduit = ?";
    
        try 
        {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getConnection().prepareStatement(req);
    
            unStat.setInt(1, idproduit);
    
            unStat.executeUpdate();
            unStat.close();
            uneBdd.seDeconnecter();
    
        } 
        catch(SQLException exp) 
        {
            System.out.println("Erreur execution requete: " + req);
            exp.printStackTrace();
        }
    }
    

    ///////////////////////////////////////////Technicien
    public static ArrayList<Technicien> selectAllTechniciens(String filtre) 
    {
        ArrayList<Technicien> lesTechniciens = new ArrayList<Technicien>();
        String requete;
        
        if(filtre.equals("")) 
        {
            requete = "SELECT * FROM technicien";
        } 
        else 
        {
            requete = "SELECT * FROM technicien WHERE nom LIKE ? OR prenom LIKE ? OR specialite LIKE ?";
        }
        
        try 
        {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getConnection().prepareStatement(requete);
            
            if(!filtre.equals("")) 
            {
                String filterString = "%" + filtre + "%";
                unStat.setString(1, filterString);
                unStat.setString(2, filterString);
                unStat.setString(3, filterString);
            }
            
            ResultSet desRes = unStat.executeQuery();
            
            while(desRes.next()) 
            {
                Technicien unTechnicien = new Technicien(
                    desRes.getInt("idtechnicien"),
                    desRes.getString("nom"),
                    desRes.getString("prenom"),
                    desRes.getString("specialite"),
                    desRes.getString("dateEmbauche")
                );
                
                lesTechniciens.add(unTechnicien);
            }
            
            unStat.close();
            uneBdd.seDeconnecter();
            
        } 
        catch (SQLException exp) 
        {
            System.out.println("Erreur de connexion à la BDD : " + requete);
            exp.printStackTrace();
        }
        
        return lesTechniciens;
    }
    

    public static void insertTechnicien(Technicien unTechnicien) 
    {
        String req = "INSERT INTO technicien (nom, prenom, specialite, dateEmbauche) VALUES (?, ?, ?, ?)";
    
        try 
        {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getConnection().prepareStatement(req);
    
            unStat.setString(1, unTechnicien.getNom());
            unStat.setString(2, unTechnicien.getPrenom());
            unStat.setString(3, unTechnicien.getSpecialite());
            unStat.setString(4, unTechnicien.getDateEmbauche()); // Assuming unTechnicien.getDateEmbauche() returns a string in the format expected by your database
    
            unStat.executeUpdate();
            unStat.close();
            uneBdd.seDeconnecter();
    
        } 
        catch (SQLException exp) 
        {
            System.out.println("Erreur execution requete : " + req);
            exp.printStackTrace();
        }
    }
    

    public static void updateTechnicien(Technicien unTechnicien) 
    {
        String req = "UPDATE technicien SET nom = ?, prenom = ?, specialite = ?, dateEmbauche = ? WHERE idtechnicien = ?";
    
        try 
        {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getConnection().prepareStatement(req);
    
            unStat.setString(1, unTechnicien.getNom());
            unStat.setString(2, unTechnicien.getPrenom());
            unStat.setString(3, unTechnicien.getSpecialite());
            unStat.setString(4, unTechnicien.getDateEmbauche()); // Assuming unTechnicien.getDateEmbauche() returns a string in the format expected by your database
            unStat.setInt(5, unTechnicien.getIdtechnicien());
    
            unStat.executeUpdate();
            unStat.close();
            uneBdd.seDeconnecter();
    
        } 
        catch(SQLException exp) 
        {
            System.out.println("Erreur execution requete: " + req);
            exp.printStackTrace();
        }
    }
    
    public static void deleteTechnicien(int idtechnicien) 
    {
        String req = "DELETE FROM technicien WHERE idtechnicien = ?";
    
        try 
        {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getConnection().prepareStatement(req);
    
            unStat.setInt(1, idtechnicien);
    
            unStat.executeUpdate();
            unStat.close();
            uneBdd.seDeconnecter();
    
        } 
        catch(SQLException exp) 
        {
            System.out.println("Erreur execution requete: " + req);
            exp.printStackTrace();
        }
    }
    

    ////////////////////////////////Intervention
    public static ArrayList<Intervention> selectAllInterventions() 
    {
        ArrayList<Intervention> lesInterventions = new ArrayList<>();
        String req = "SELECT * FROM intervention";
    
        try 
        {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getConnection().prepareStatement(req);
            ResultSet desRes = unStat.executeQuery();
    
            while(desRes.next()) 
            {
                Intervention unIntervention = new Intervention(
                    desRes.getInt("idinter"),
                    desRes.getString("description"),
                    desRes.getFloat("prixInter"),
                    desRes.getString("dateInter"),
                    desRes.getInt("idproduit"),
                    desRes.getInt("idtechnicien")
                );
    
                lesInterventions.add(unIntervention);
            }
    
            unStat.close();
            uneBdd.seDeconnecter();
    
        } 
        catch (SQLException exp) 
        {
            System.out.println("Erreur execution requete: " + req);
            exp.printStackTrace();
        }
    
        return lesInterventions;
    }

    public static void insertInter(Intervention unIntervention) 
    {
        String req = "INSERT INTO intervention (description, prixInter, dateInter, idproduit, idtechnicien) VALUES (?, ?, ?, ?, ?)";
    
        try 
        {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getConnection().prepareStatement(req);
    
            unStat.setString(1, unIntervention.getDescription());
            unStat.setFloat(2, unIntervention.getPrixInter());
            unStat.setString(3, unIntervention.getDateInter()); // Assuming getDateInter() returns a string in the format expected by your database
            unStat.setInt(4, unIntervention.getIdproduit());
            unStat.setInt(5, unIntervention.getIdtechnicien());
    
            unStat.executeUpdate();
            unStat.close();
            uneBdd.seDeconnecter();
    
        } 
        catch(SQLException exp) 
        {
            System.out.println("Erreur execution requete: " + req);
            exp.printStackTrace();
        }
    }
    
    public static void updateInter(Intervention unIntervention) 
    {
        String req = "UPDATE intervention SET description = ?, prixInter = ?, dateInter = ?, idproduit = ?, idtechnicien = ? WHERE idinter = ?";
    
        try 
        {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getConnection().prepareStatement(req);
    
            unStat.setString(1, unIntervention.getDescription());
            unStat.setFloat(2, unIntervention.getPrixInter());
            unStat.setString(3, unIntervention.getDateInter()); // Assuming getDateInter() returns a string in the format expected by your database
            unStat.setInt(4, unIntervention.getIdproduit());
            unStat.setInt(5, unIntervention.getIdtechnicien());
            unStat.setInt(6, unIntervention.getIdinter());
    
            unStat.executeUpdate();
            unStat.close();
            uneBdd.seDeconnecter();
    
        } 
        catch (SQLException exp) 
        {
            System.out.println("Erreur execution requete: " + req);
            exp.printStackTrace();
        }
    }

    public static void deleteInter(int idinter) 
    {
        String req = "DELETE FROM intervention WHERE idinter = ?";
    
        try 
        {
            uneBdd.seConnecter();
            PreparedStatement unStat = uneBdd.getConnection().prepareStatement(req);
    
            unStat.setInt(1, idinter);
    
            unStat.executeUpdate();
            unStat.close();
            uneBdd.seDeconnecter();
    
        } 
        catch(SQLException exp) 
        {
            System.out.println("Erreur execution requete: " + req);
            exp.printStackTrace();
        }
    }
    
}
