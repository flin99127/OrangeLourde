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

import controleur.Controleur;
import controleur.Intervention;
import controleur.Produit;
import controleur.Tableau;
import controleur.Technicien;

public class PanelInterventions extends PanelPrincipal implements ActionListener
{
    private JPanel panelForm = new JPanel();
    private JTextField txtDescription = new JTextField();
    private JTextField txtPrixInter = new JTextField();
    private JTextField txtDateInter = new JTextField();
    private JComboBox<String> txtIdProduit = new JComboBox<>();
    private static JComboBox<String> txtIdTechnicien = new JComboBox<>();
    private JButton btEnregistrer = new JButton("Enregistrer");
    private JButton btAnnuler = new JButton("Annuler");
    
    private JTable tableInter;
    private JScrollPane uneScroll;
    private Tableau unTableau;
    private JLabel txtNbIntervention = new JLabel();

    private JPanel panelFiltre = new JPanel();
    private JButton btFiltrer = new JButton("Filtrer");
    private JTextField txtFiltre = new JTextField();

    public PanelInterventions()
    {
        super("Gestion des interventions");

        this.panelForm.setBackground(Color.gray);
        this.panelForm.setBounds(40, 100, 350, 300);
        this.panelForm.setLayout(new GridLayout(6, 2));
        this.panelForm.add(new JLabel("Description : "));
        this.panelForm.add(this.txtDescription);
        this.panelForm.add(new JLabel("Prix intervention : "));
        this.panelForm.add(this.txtPrixInter);
        this.panelForm.add(new JLabel("Date intervention"));
        this.panelForm.add(this.txtDateInter);
        this.panelForm.add(new JLabel("Produit : "));
        this.panelForm.add(this.txtIdProduit);
        this.panelForm.add(new JLabel("Technicien concerné"));
        this.panelForm.add(txtIdTechnicien);
        this.panelForm.add(btEnregistrer);
        this.panelForm.add(btAnnuler);
        this.add(this.panelForm);

        //placement du panel filtre 
        this.panelFiltre.setBackground(Color.gray);
        this.panelFiltre.setBounds(420, 110, 360, 40);
        this.panelFiltre.setLayout(new GridLayout(1, 3));
        this.panelFiltre.add(this.txtFiltre);
        this.panelFiltre.add(this.btFiltrer);
        this.add(this.panelFiltre);
        this.btFiltrer.addActionListener(this);

        //construction du panel filtre
        String entetes[] = {"Id Intervention", "Description", "Prix intervention", "Date intervention", "Id produit", "Id technicien"};
        this.unTableau = new Tableau(this.obtenirDonnees(""), entetes);
        this.tableInter = new JTable(this.unTableau);
        this.uneScroll = new JScrollPane(this.tableInter);
        this.uneScroll.setBounds(420, 160, 350, 250);
        this.uneScroll.setBackground(Color.gray);
        this.add(this.uneScroll);

        this.remplirCBX();

        this.txtNbIntervention.setBounds(440, 440, 300, 20);
        this.txtNbIntervention.setText("Le nombre d'interventions est de : " + this.unTableau.getRowCount());
        this.add(this.txtNbIntervention);

        this.tableInter.addMouseListener(new MouseListener() 
        {
            @Override
            public void mouseReleased(MouseEvent e) 
            {}

            @Override
            public void mousePressed(MouseEvent e) 
            {}

            @Override
            public void mouseExited(MouseEvent e) 
            {}

            @Override
            public void mouseEntered(MouseEvent e) 
            {}

            @Override
            public void mouseClicked(MouseEvent e) 
            {
                int numLigne, idintervention;
                if (e.getClickCount() >= 2) 
                {
                    numLigne = tableInter.getSelectedRow();
                    idintervention = Integer.parseInt(unTableau.getValueAt(numLigne, 0).toString());
                    int reponse = JOptionPane.showConfirmDialog(null, "Voulez-vous supprimer l'intervention ?", "Suppression de l'intervention", JOptionPane.YES_NO_OPTION);
                    
                    if(reponse == 0) 
                    {
                        // Suppression en BDD
                        Controleur.deleteInter(idintervention);
                        // Actualiser affichage
                        unTableau.setDonnees(obtenirDonnees(""));
                    }
                } 
                else if(e.getClickCount() >= 1) 
                {
                    numLigne = tableInter.getSelectedRow();
                    txtDescription.setText(unTableau.getValueAt(numLigne, 1).toString());
                    txtPrixInter.setText(unTableau.getValueAt(numLigne, 2).toString());
                    txtDateInter.setText(unTableau.getValueAt(numLigne, 3).toString());
                    txtIdProduit.setSelectedItem(unTableau.getValueAt(numLigne, 4).toString());
                    txtIdTechnicien.setSelectedItem(unTableau.getValueAt(numLigne, 5).toString());
                    btEnregistrer.setText("Modifier");
                }
            }
        });

        btEnregistrer.addActionListener(this);
        btAnnuler.addActionListener(this);
    }

    public void remplirCBX()
    {
        this.txtIdProduit.removeAllItems();
        
        ArrayList<Produit> lesProduits = Controleur.selectAllProduits();

        for(Produit unProduit : lesProduits)
        {
            this.txtIdProduit.addItem(unProduit.getIdproduit() + "-" + unProduit.getDesignation());
        }
        this.txtIdTechnicien.removeAllItems();

        ArrayList<Technicien> lesTechniciens = Controleur.selectAllTechniciens("");

        for(Technicien unTechnicien : lesTechniciens)
        {
            this.txtIdTechnicien.addItem(unTechnicien.getIdtechnicien() + "-" + unTechnicien.getNom() + " " + unTechnicien.getPrenom());
        }
    }

    public Object[][] obtenirDonnees(String filtre)
    {
        ArrayList<Intervention> lesInterventions = Controleur.selectAllInterventions();
        Object[][] matrice = new Object[lesInterventions.size()][6];

        int i = 0;

        for(Intervention uneIntervention : lesInterventions)
        {
            matrice[i][0] = uneIntervention.getIdinter();
            matrice[i][1] = uneIntervention.getDescription();
            matrice[i][2] = uneIntervention.getPrixInter();
            matrice[i][3] = uneIntervention.getDateInter();
            matrice[i][4] = uneIntervention.getIdproduit();
            matrice[i][5] = uneIntervention.getIdtechnicien();
            i++;
        }
        return matrice;
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == this.btAnnuler) 
        {
            this.txtDescription.setText("");
            this.txtPrixInter.setText("");
            this.txtDateInter.setText("");
            this.txtIdProduit.setSelectedItem("");
            this.txtIdTechnicien.setSelectedItem("");
            this.btEnregistrer.setText("Enregistrer");
        } 
        else if(e.getSource() == this.btEnregistrer && this.btEnregistrer.getText().equals("Enregistrer"))
        {
            try {
                String description = this.txtDescription.getText();
                Float prixInter = Float.parseFloat(this.txtPrixInter.getText());
                String dateInter = this.txtDateInter.getText();
                String chaine = txtIdProduit.getSelectedItem().toString();
                String tab[] = chaine.split("-");
                int idProduit = Integer.parseInt(tab[0]);
                String chaine2 = txtIdTechnicien.getSelectedItem().toString();
                String tab2[] = chaine2.split("-");
                int idTechnicien = Integer.parseInt(tab2[0]);
                Intervention uneIntervention = new Intervention(description, prixInter, dateInter, idProduit, idTechnicien);
                
                //on insere dans la base de donnee
                Controleur.insertInter(uneIntervention);

                //on actualise affichage apres inserction
                this.unTableau.setDonnees(this.obtenirDonnees(""));

                //on vide les champs
                this.txtDescription.setText("");
                this.txtPrixInter.setText("");
                this.txtDateInter.setText("");

                //affiche un message de confirmation
                JOptionPane.showMessageDialog(this, "Insertion réussie");

                //actualisation de la liste de clients dans le panel produits
                PanelProduits.remplirCBXClients();

                //actualise le nombre de client
                this.txtNbIntervention.setText("Le nombre d'interventions est de : " + this.unTableau.getRowCount());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'insertion : " + ex.getMessage());
            }
        }
        else if(e.getSource() == this.btEnregistrer && this.btEnregistrer.getText().equals("Modifier"))
        {
            try {
                int numLigne = tableInter.getSelectedRow();
                int idIntervention = Integer.parseInt(unTableau.getValueAt(numLigne, 0).toString());
                String description = this.txtDescription.getText();
                Float prixInter = Float.parseFloat(this.txtPrixInter.getText());
                String dateInter = this.txtDateInter.getText();
                String chaineProd = txtIdProduit.getSelectedItem().toString();
                String tabProd[] = chaineProd.split("-");
                int idProduit = Integer.parseInt(tabProd[0]);
                String chaineTech = txtIdTechnicien.getSelectedItem().toString();
                String tabTech[] = chaineTech.split("-");
                int idTechnicien = Integer.parseInt(tabTech[0]);
                Intervention uneIntervention = new Intervention(idIntervention, description, prixInter, dateInter, idProduit, idTechnicien);
                
                //on met a jour dans la base de donnee
                Controleur.updateInter(uneIntervention);

                //on actualise affichage apres mise a jour
                this.unTableau.setDonnees(this.obtenirDonnees(""));

                //on vide les champs
                this.txtDescription.setText("");
                this.txtPrixInter.setText("");
                this.txtDateInter.setText("");
                this.txtIdProduit.setSelectedItem("");
                this.txtIdTechnicien.setSelectedItem("");
                this.btEnregistrer.setText("Enregistrer");

                //affiche un message de confirmation
                JOptionPane.showMessageDialog(this, "Modification réussie");

                //actualisation de la liste de clients dans le panel produits
                //PanelProduits.remplirCBXClients();

                //actualise le nombre de client
                this.txtNbIntervention.setText("Le nombre d'interventions est de : " + this.unTableau.getRowCount());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la modification : " + ex.getMessage());
            }
        }
        else if(e.getSource() == this.btFiltrer) {
            this.unTableau.setDonnees(this.obtenirDonnees(this.txtFiltre.getText()));
        }
    }
}