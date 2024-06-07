package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controleur.Client;
import controleur.Controleur;
import controleur.Produit;
import controleur.Tableau;

public class PanelProduits extends PanelPrincipal implements ActionListener
{
    private JPanel panelForm = new JPanel();
    private JTextField txtDesignation = new JTextField();
    private JTextField txtPrixAchat = new JTextField();
    private JTextField txtDateAchat = new JTextField();
    private JComboBox<String> txtCategorie = new JComboBox<String>();
    private static JComboBox<String> txtIdClient = new JComboBox<String>();
    private JButton btEnregistrer = new JButton("Enregistrer");
    private JButton btAnnuler = new JButton("Annuler");
    
    private JTable tableProduits;
    private JScrollPane uneScroll;
    private Tableau unTableau;
    private JLabel txtNbProduits = new JLabel();
    
    public PanelProduits()
    {
        super("Gestion des infos des produits");

        this.txtCategorie.addItem("Téléphone");
        this.txtCategorie.addItem("Informatique");
        this.txtCategorie.addItem("Télévision");
        
        this.panelForm.setBackground(Color.gray);
        this.panelForm.setBounds(40, 100, 350, 300);
        this.panelForm.setLayout(new GridLayout(6, 2));
        this.panelForm.add(new JLabel("Désignation produits : "));
        this.panelForm.add(this.txtDesignation);
        this.panelForm.add(new JLabel("Prix d'achat produit : "));
        this.panelForm.add(this.txtPrixAchat);
        this.panelForm.add(new JLabel("Date d'achat produit"));
        this.panelForm.add(this.txtDateAchat);
        this.panelForm.add(new JLabel("Catégorie : "));
        this.panelForm.add(this.txtCategorie);
        this.panelForm.add(new JLabel("Client concerné"));
        this.panelForm.add(txtIdClient);
        this.panelForm.add(btEnregistrer);
        this.panelForm.add(btAnnuler);
        this.add(this.panelForm);

       

        String entetes[] = {"Id Produit", "Désignation", "Prix d'achat", "Date d'achat", "Catégorie", "Client concerné"};
        this.unTableau = new Tableau(this.obtenirDonnees(), entetes);
        this.tableProduits = new JTable(unTableau);
        this.uneScroll = new JScrollPane(this.tableProduits);
        this.uneScroll.setBounds(420, 160, 350, 250);
        this.uneScroll.setBackground(Color.gray);
        this.add(this.uneScroll);
        this.btAnnuler.addActionListener(this);
        this.btEnregistrer.addActionListener(this);
        remplirCBXClients(); 
        
        //installer le label txtnbprodui
        this.txtNbProduits.setBounds(440, 440, 300, 20);
        this.txtNbProduits.setText("le nombre produit est de : " + this.unTableau.getRowCount());
        this.add(this.txtNbProduits);


    }

    public Object[][] obtenirDonnees()
    {
        ArrayList<Produit> lesProduits = Controleur.selectAllProduits();
        Object[][] matrice = new Object[lesProduits.size()][6];

        int i = 0;

        for(Produit unProduit : lesProduits)
        {
            matrice [i][0] = unProduit.getIdproduit();
            matrice [i][1] = unProduit.getDesignation();
            matrice [i][2] = unProduit.getPrixAchat();
            matrice [i][3] = unProduit.getDateAchat();
            matrice [i][4] = unProduit.getCategorie();
            matrice [i][5] = unProduit.getIdClient();
            i++;
        }
        return matrice;
    }

    public static void remplirCBXClients()
    {
        txtIdClient.removeAllItems();

        ArrayList<Client> lesClients = Controleur.selectAllClients("");

        for(Client unClient : lesClients)
        {
            txtIdClient.addItem(unClient.getIdClient() + "-" + unClient.getNom() + " " + unClient.getPrenom());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == this.btEnregistrer) 
        {
            String designation = this.txtDesignation.getText();
            Float prixAchat = Float.parseFloat(this.txtPrixAchat.getText());
            String dateAchat = this.txtDateAchat.getText();
            String Categorie = this.txtCategorie.getSelectedItem().toString();
            String chaine = txtIdClient.getSelectedItem().toString();
            String tab[] = chaine.split("-");
            int idclient = Integer.parseInt(tab[0]);
            Produit unProduit = new Produit(designation, idclient, dateAchat, Categorie, idclient);
            
            Controleur.insertProduit(unProduit);
            
            this.txtDesignation.setText("");
            this.txtPrixAchat.setText("");
            this.txtDateAchat.setText("");

            //affiche la confirmation
            JOptionPane.showMessageDialog(this, "insertion réussie");

            //actuelise le nombre produit
            this.txtNbProduits.setText("le nombre client est de : " + this.unTableau.getRowCount());

            this.unTableau.setDonnees(this.obtenirDonnees());
        }
        else if(e.getSource() == this.btAnnuler) 
        {
            this.txtDesignation.setText("");
            this.txtPrixAchat.setText("");
            this.txtDateAchat.setText("");
        }
    }
}