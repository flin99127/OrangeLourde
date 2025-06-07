package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

public class PanelProduits extends PanelPrincipal implements ActionListener {
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

    private JPanel panelFiltre = new JPanel();
    private JButton btFiltrer = new JButton("Filtrer");
    private JTextField txtFiltre = new JTextField();

    private JLabel txtNbProduits = new JLabel();

    public PanelProduits() {
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
        this.panelForm.add(new JLabel("Date d'achat produit :"));
        this.panelForm.add(this.txtDateAchat);
        this.panelForm.add(new JLabel("Catégorie : "));
        this.panelForm.add(this.txtCategorie);
        this.panelForm.add(new JLabel("Client concerné :"));
        this.panelForm.add(txtIdClient);
        this.panelForm.add(btEnregistrer);
        this.panelForm.add(btAnnuler);
        this.add(this.panelForm);

        // Placement du panel filtre
        this.panelFiltre.setBackground(Color.gray);
        this.panelFiltre.setBounds(420, 110, 360, 40);
        this.panelFiltre.setLayout(new GridLayout(1, 3));
        this.panelFiltre.add(this.txtFiltre);
        this.panelFiltre.add(this.btFiltrer);
        this.add(this.panelFiltre);
        this.btFiltrer.addActionListener(this);

        String entetes[] = { "Id Produit", "Désignation", "Prix d'achat", "Date d'achat", "Catégorie", "Client concerné" };
        this.unTableau = new Tableau(this.obtenirDonnees(""), entetes);
        this.tableProduits = new JTable(this.unTableau);
        this.uneScroll = new JScrollPane(this.tableProduits);
        this.uneScroll.setBounds(420, 160, 350, 250);
        this.uneScroll.setBackground(Color.gray);
        this.add(this.uneScroll);
        this.btAnnuler.addActionListener(this);
        this.btEnregistrer.addActionListener(this);
        remplirCBXClients();

        // Installer le label txtNbProduits
        this.txtNbProduits.setBounds(440, 440, 300, 20);
        this.txtNbProduits.setText("Le nombre de produits est de : " + this.unTableau.getRowCount());
        this.add(this.txtNbProduits);

        this.tableProduits.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                int numLigne, idproduit;
                if (e.getClickCount() >= 2) {
                    numLigne = tableProduits.getSelectedRow();
                    idproduit = Integer.parseInt(unTableau.getValueAt(numLigne, 0).toString());
                    int reponse = JOptionPane.showConfirmDialog(null, "Voulez-vous supprimer le produit ?",
                            "Suppression du produit", JOptionPane.YES_NO_OPTION);

                    if (reponse == 0) {
                        // Suppression en BDD
                        Controleur.deleteProduit(idproduit);
                        // Actualiser affichage
                        unTableau.setDonnees(obtenirDonnees(""));
                    }
                } else if (e.getClickCount() >= 1) {
                    numLigne = tableProduits.getSelectedRow();
                    txtDesignation.setText(unTableau.getValueAt(numLigne, 1).toString());
                    txtPrixAchat.setText(unTableau.getValueAt(numLigne, 2).toString());
                    txtDateAchat.setText(unTableau.getValueAt(numLigne, 3).toString());
                    txtCategorie.setSelectedItem(unTableau.getValueAt(numLigne, 4).toString());
                    txtIdClient.setSelectedItem(unTableau.getValueAt(numLigne, 5).toString());
                    btEnregistrer.setText("Modifier");
                }
            }
        });
    }

    public Object[][] obtenirDonnees(String filtre) {
        ArrayList<Produit> lesProduits = Controleur.selectAllProduits();
        Object[][] matrice = new Object[lesProduits.size()][6];

        int i = 0;

        for (Produit unProduit : lesProduits) 
        {
            matrice[i][0] = unProduit.getIdproduit();
            matrice[i][1] = unProduit.getDesignation();
            matrice[i][2] = unProduit.getPrixAchat();
            matrice[i][3] = unProduit.getDateAchat();
            matrice[i][4] = unProduit.getCategorie();
            matrice[i][5] = unProduit.getIdClient();
            i++;
        }
        return matrice;
    }

    public static void remplirCBXClients() 
    {
        txtIdClient.removeAllItems();

        ArrayList<Client> lesClients = Controleur.selectAllClients("");

        for (Client unClient : lesClients) {
            txtIdClient.addItem(unClient.getIdClient() + "-" + unClient.getNom() + " " + unClient.getPrenom());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btEnregistrer && this.btEnregistrer.getText().contentEquals("Enregistrer")) {
            try {
                String designation = this.txtDesignation.getText();
                float prixAchat = Float.parseFloat(this.txtPrixAchat.getText());
                String dateAchat = this.txtDateAchat.getText();
                String categorie = this.txtCategorie.getSelectedItem().toString();
                String chaine = txtIdClient.getSelectedItem().toString();
                String tab[] = chaine.split("-");
                int idClient = Integer.parseInt(tab[0]);
                Produit unProduit = new Produit(designation, prixAchat, dateAchat, categorie, idClient);

                Controleur.insertProduit(unProduit);

                this.txtDesignation.setText("");
                this.txtPrixAchat.setText("");
                this.txtDateAchat.setText("");

                // Affiche la confirmation
                JOptionPane.showMessageDialog(this, "Insertion réussie");

                // Actualise le nombre de produits
                this.txtNbProduits.setText("Le nombre de produits est de : " + this.unTableau.getRowCount());

                this.unTableau.setDonnees(this.obtenirDonnees(""));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un prix valide", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == btFiltrer) {
            String filtre = this.txtFiltre.getText();

            this.unTableau.setDonnees(this.obtenirDonnees(filtre));
        } 
        else if(e.getSource() == this.btEnregistrer && this.btEnregistrer.getText().equals("Modifier")) 
        {
            try 
            {
                String designation = this.txtDesignation.getText();
                float prixAchat = Float.parseFloat(this.txtPrixAchat.getText());
                String dateAchat = this.txtDateAchat.getText();
                String categorie = this.txtCategorie.getSelectedItem().toString();
                String chaine = txtIdClient.getSelectedItem().toString();
                String tab[] = chaine.split("-");
                int idClient = Integer.parseInt(tab[0]);

                int numLigne = this.tableProduits.getSelectedRow();
                int idproduit = Integer.parseInt(this.unTableau.getValueAt(numLigne, 0).toString());

                Produit unProduit = new Produit(idproduit, designation, prixAchat, dateAchat, categorie, idClient);

                Controleur.updateProduit(unProduit);

                this.unTableau.setDonnees(this.obtenirDonnees(""));

                PanelProduits.remplirCBXClients();

                this.txtDesignation.setText("");
                this.txtPrixAchat.setText("");
                this.txtDateAchat.setText("");
                this.txtCategorie.setSelectedItem("");
                this.txtIdClient.setSelectedItem("");
                this.btEnregistrer.setText("Enregistrer");
                JOptionPane.showMessageDialog(this, "Modification réussie du produit");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un prix valide", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == this.btAnnuler) {
            this.txtDesignation.setText("");
            this.txtPrixAchat.setText("");
            this.txtDateAchat.setText("");
        }
    }
}