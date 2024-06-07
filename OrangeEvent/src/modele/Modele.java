package modele;

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
        String req = "update user set nom = '" + unUser.getNom() +"', prenom = '" + unUser.getPrenom() +"', email = '" + unUser.getEmail() + "', mdp = '" + unUser.getMdp() + "', role = '" + unUser.getRole() + "' where iduser = " + unUser.getIduser();

        try 
        {
            uneBdd.seConnecter();
            Statement unStat = uneBdd.getConnection().createStatement();
            unStat.execute(req);    
        } 
        catch (Exception exp) 
        {
            System.out.println("Erreur excution requete : " + req);
            exp.printStackTrace();
        }
    }

    public static void insertClient(Client unClient)
    {
        String req = "insert into client values (null, '" + unClient.getNom() + "', '" + unClient.getPrenom() +"', '" + unClient.getAdresse() +"', '" + unClient.getEmail()+"');";

        try 
        {
            uneBdd.seConnecter();
            Statement unStat = uneBdd.getConnection().createStatement();
            unStat.execute(req);
        } 
        catch (Exception exp) 
        {
            System.out.println("Erreur excution requete : " + req);
            exp.printStackTrace();
        }
    }

    public static ArrayList<Client> selectAllClients (String filtre)
    {
        ArrayList<Client> lesClients = new ArrayList<Client>();
        String requete;
        
        if(filtre.equals("")) 
        {
            requete ="select * from client;";
        }
        else 
        {
            requete = "select * from client where nom like '%" + filtre + "%' or  " + " prenom like '%" + filtre + "%'  or adresse like '%" + filtre + "%';";
        }
        try 
        {
            uneBdd.seConnecter();
            Statement unStat = uneBdd.getConnection().createStatement();
            
            //quand on recupere un resultat ( SELECT)
             ResultSet desRes = unStat.executeQuery(requete);
             
             while (desRes.next()) 
             {
                 Client unClient = new Client(
                     desRes.getInt("idclient"), 
                     desRes.getString("nom"),
                     desRes.getString("prenom"), 
                     desRes.getString("adresse"),
                     desRes.getString("email"));

                     lesClients.add(unClient);
                 }
             
            unStat.close();
            uneBdd.seDeconnecter();
            
        }
        catch(SQLException exp) 
        {
            System.out.println("Erreur de connexion à la BDD :" + requete);
            exp.printStackTrace();
        }
        
        return lesClients;
    }

    public static void deleteClient (int idclient)
    {
        String req = "delete from client where idclient= " + idclient + ";";
        try 
        {
            uneBdd.seConnecter();
            Statement unStat= uneBdd.getConnection().createStatement();
            unStat.execute(req);
            unStat.close();
            uneBdd.seDeconnecter();
            
        } 
        catch(SQLException exp) 
        {
            System.out.println("Erreur execution requete: "+ req);
            exp.printStackTrace();
        }
    }
    
    public static void updateClient (Client unclient)
    {
        String req = "update client set nom='" + unclient.getNom() + "', prenom = '" + unclient.getPrenom() + "', adresse = '" + unclient.getAdresse() + "', email='" + unclient.getEmail() + "' where idclient=" +unclient.getIdClient()+";";
        try 
        {
            uneBdd.seConnecter();
            Statement unStat= uneBdd.getConnection().createStatement();
            unStat.execute(req);
        } 
        catch(SQLException exp) 
        {
            System.out.println("Erreur execution requete: "+ req);
            exp.printStackTrace();
        }

    }

    public static void insertProduit(Produit unProduit)
    {
        String req ="insert into produit values (null, '" + unProduit.getDesignation() + "', '" + unProduit.getPrixAchat() + "', '" + unProduit.getDateAchat() + "', '" + unProduit.getCategorie() + "', '" + unProduit.getIdClient() + "');";
		try {
			uneBdd.seConnecter();
			Statement unStat= uneBdd.getConnection().createStatement();
			unStat.execute(req);
		} catch(SQLException exp) {
			System.out.println("Erreur execution requete: "+ req);
			exp.printStackTrace();
		}
    }

    public static ArrayList<Produit> selectAllProduits()
    {
        ArrayList<Produit> lesProduits = new ArrayList<Produit>();
		String req = "select * from produit;";
		try {
			uneBdd.seConnecter();
			Statement unStat= uneBdd.getConnection().createStatement();
			ResultSet desRes= unStat.executeQuery(req);
			while (desRes.next()) {
				Produit unProduit = new Produit (desRes.getInt("idproduit"), desRes.getString("designation"), desRes.getFloat("prixAchat"), desRes.getString("dateAchat"), desRes.getString("categorie"), desRes.getInt("idclient"));
				lesProduits.add(unProduit);
			}
			unStat.close();
			uneBdd.seDeconnecter();
		} 
        catch(SQLException exp) 
        {
			System.out.println("Erreur execution requete: "+ req);
			exp.printStackTrace();
		}
		return lesProduits;
    }

    public static ArrayList<Technicien> selectAllTechniciens(String filtre)
    {
        ArrayList<Technicien> lesTechniciens = new ArrayList<Technicien>();
        String requete;
        
        if(filtre.equals("")) 
        {
            requete ="select * from technicien;";
        }
        else 
        {
            requete = "select * from technicien where nom like '%" + filtre + "%' or  " + " prenom like '%" + filtre + "%'  or specialite like '%" + filtre + "%';";
        }
        try 
        {
            uneBdd.seConnecter();
            Statement unStat = uneBdd.getConnection().createStatement();
            
            //quand on recupere un resultat ( SELECT)
             ResultSet desRes = unStat.executeQuery(requete);
             
             while (desRes.next()) 
             {
                 Technicien unTechnicien = new Technicien(
                     desRes.getInt("idtechnicien"), 
                     desRes.getString("nom"),
                     desRes.getString("prenom"), 
                     desRes.getString("specialite"),
                     desRes.getString("dateEmbauche"));

                     lesTechniciens.add(unTechnicien);
                 }
             
            unStat.close();
            uneBdd.seDeconnecter();
            
        }
        catch(SQLException exp) 
        {
            System.out.println("Erreur de connexion à la BDD :" + requete);
            exp.printStackTrace();
        }
        
        return lesTechniciens;
    }

    public static void insertTechnicien(Technicien unTechnicien)
    {
        String req = "insert into technicien values (null, '" + unTechnicien.getNom() + "', '" + unTechnicien.getPrenom() +"', '" + unTechnicien.getSpecialite() +"', '" + unTechnicien.getDateEmbauche()+"');";

        try 
        {
            uneBdd.seConnecter();
            Statement unStat = uneBdd.getConnection().createStatement();
            unStat.execute(req);
        } 
        catch (Exception exp) 
        {
            System.out.println("Erreur excution requete : " + req);
            exp.printStackTrace();
        }
    }

    public static void updateTechnicien(Technicien unTechnicien)
    {
        String req = "update technicien set nom='" + unTechnicien.getNom() + "', prenom = '" + unTechnicien.getPrenom() + "', specialite = '" + unTechnicien.getSpecialite() + "', dateEmbauche='" + unTechnicien.getDateEmbauche() + "' where idtechnicien=" +unTechnicien.getIdtechnicien()+";";
        try 
        {
            uneBdd.seConnecter();
            Statement unStat= uneBdd.getConnection().createStatement();
            unStat.execute(req);
        } 
        catch(SQLException exp) 
        {
            System.out.println("Erreur execution requete: "+ req);
            exp.printStackTrace();
        }
    }

    public static void deleteTechnicien(int idtechnicien)
    {
        String req = "delete from technicien where idtechnicien= " + idtechnicien + ";";
        try 
        {
            uneBdd.seConnecter();
            Statement unStat= uneBdd.getConnection().createStatement();
            unStat.execute(req);
            unStat.close();
            uneBdd.seDeconnecter();
            
        } 
        catch(SQLException exp) 
        {
            System.out.println("Erreur execution requete: "+ req);
            exp.printStackTrace();
        }
    }

    public static ArrayList<Intervention> selectAllInterventions()
    {
        ArrayList<Intervention> lesinterventions = new ArrayList<Intervention>();
		String req = "select * from intervention;";
		try 
        {
			uneBdd.seConnecter();
			Statement unStat= uneBdd.getConnection().createStatement();
			ResultSet desRes= unStat.executeQuery(req);
			while (desRes.next()) {
				Intervention unIntervention = new Intervention (desRes.getInt("idinter"), desRes.getString("description"), desRes.getFloat("prixInter"), desRes.getString("dateInter"), desRes.getInt("idproduit"), desRes.getInt("idtechnicien"));
				lesinterventions.add(unIntervention);
			}
			unStat.close();
			uneBdd.seDeconnecter();
		} 
        catch(SQLException exp) 
        {
			System.out.println("Erreur execution requete: "+ req);
			exp.printStackTrace();
		}
		return lesinterventions;
    }
}
