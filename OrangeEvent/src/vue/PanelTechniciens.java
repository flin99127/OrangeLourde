package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controleur.Controleur;
import controleur.Tableau;
import controleur.Technicien;

public class PanelTechniciens extends PanelPrincipal implements ActionListener
{
    private JPanel panelForm = new JPanel();
    private JTextField txtNom = new JTextField();
    private JTextField txtPrenom = new JTextField();
    private JTextField txtSpecialite = new JTextField();
    private JTextField txtDateEmbauche = new JTextField();
    private JButton btEnregistrer = new JButton("Enregistrer");
    private JButton btAnnuler = new JButton("Annuler");
    
    private JTable tableTechniciens;
    private JScrollPane uneScroll;
    private Tableau unTableau;

    private JPanel panelFiltre = new JPanel();
    private JButton btFiltrer = new JButton("Filtrer");
    private JTextField txtFiltre = new JTextField();

    private JLabel txtNbTechniciens = new JLabel();

    public PanelTechniciens()
    {
        super("Gestion des Techniciens");
        
        this.panelForm.setBackground(Color.GRAY);
        this.panelForm.setBounds(40, 100, 350, 300);
        this.panelForm.setLayout(new GridLayout(5, 2));
        this.panelForm.add(new JLabel("Nom user : "));
        this.panelForm.add(this.txtNom);
        this.panelForm.add(new JLabel("Prenom user : "));
        this.panelForm.add(this.txtPrenom);
        this.panelForm.add(new JLabel("Spécialité technicien"));
        this.panelForm.add(this.txtSpecialite);
        this.panelForm.add(new JLabel("Date embauche : "));
        this.panelForm.add(this.txtDateEmbauche);
        this.panelForm.add(this.btEnregistrer);
        this.panelForm.add(this.btAnnuler);
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
        String entetes[] = {"Id Technicien", "Nom", "Prénom", "Spécialité", "Date embauche"};
        this.unTableau = new Tableau(this.obtenirDonnees(""), entetes);
        this.tableTechniciens = new JTable(this.unTableau);
        this.uneScroll = new JScrollPane(this.tableTechniciens);
        this.uneScroll.setBounds(420, 160, 350, 250);
        this.uneScroll.setBackground(Color.gray);
        this.add(this.uneScroll);

        //installer le label txtnbtechnicien
        this.txtNbTechniciens.setBounds(440, 440, 300, 20);
        this.txtNbTechniciens.setText("le nombre technicien est de : " + this.unTableau.getRowCount());
        this.add(this.txtNbTechniciens);

        //rendre les bouton ecoutable
        this.btAnnuler.addActionListener(this);
        this.btEnregistrer.addActionListener(this);

        this.tableTechniciens.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                int numLigne, idtechnicien;

                if(e.getClickCount() >= 2) 
                {
                    numLigne = tableTechniciens.getSelectedRow();
                    idtechnicien = Integer.parseInt(unTableau.getValueAt(numLigne, 0).toString());

                    int reponse = JOptionPane.showConfirmDialog(null, "voulez-vous supprimer le technicien ?", "Suppression idtechnicien", JOptionPane.YES_NO_OPTION);

                    if(reponse == 0) 
                    {
                        //suppresion en BDD
                        Controleur.deleteTechnicien(idtechnicien);

                        //actualiser affichage
                        unTableau.setDonnees(obtenirDonnees(""));

                        //acutaliser la liste des clients dans le panel produits
                        PanelProduits.remplirCBXClients();

                        //actualiser le nombre client
                        txtNbTechniciens.setText("le nombre technicien est de : " + unTableau.getRowCount());
                    }
                }
                else if(e.getClickCount() == 1) 
                {
                    numLigne = tableTechniciens.getSelectedRow();
                    txtNom.setText(unTableau.getValueAt(numLigne, 1).toString());
                    txtPrenom.setText(unTableau.getValueAt(numLigne, 2).toString());
                    txtSpecialite.setText(unTableau.getValueAt(numLigne, 3).toString());
                    txtDateEmbauche.setText(unTableau.getValueAt(numLigne, 4).toString());
                    btEnregistrer.setText("Modifier");
                }
                
            }

            @Override
            public void mousePressed(MouseEvent e) 
            {
            }

            @Override
            public void mouseReleased(MouseEvent e) 
            {
            }

            @Override
            public void mouseEntered(MouseEvent e) 
            {
            }

            @Override
            public void mouseExited(MouseEvent e) 
            {
            }
        });
    }

    public Object[][] obtenirDonnees(String filtre)
    {
        ArrayList<Technicien> lesTechniciens = Controleur.selectAllTechniciens(filtre);
        Object[][] matrice = new Object[lesTechniciens.size()][6];

        int i = 0;

        for(Technicien unTechnicien : lesTechniciens)
        {
            matrice [i][0] = unTechnicien.getIdtechnicien();
            matrice [i][1] = unTechnicien.getNom();
            matrice [i][2] = unTechnicien.getPrenom();
            matrice [i][3] = unTechnicien.getSpecialite();
            matrice [i][4] = unTechnicien.getDateEmbauche();
            i++;
        }
        return matrice;
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == this.btAnnuler) 
        {
			this.txtNom.setText("");
			this.txtPrenom.setText("");
			this.txtSpecialite.setText("");
			this.txtDateEmbauche.setText("");
            this.btEnregistrer.setText("Enregistrer");
		} 
        else if (e.getSource() == this.btEnregistrer && this.btEnregistrer.getText().contentEquals("Enregistrer")) 
        {
			String nom = this.txtNom.getText();
			String prenom = this.txtPrenom.getText();
			String specialite = this.txtSpecialite.getText();
			String dateEmbauche = this.txtDateEmbauche.getText();
			Technicien unTechnicien = new Technicien(nom, prenom, specialite, dateEmbauche);
			
            //on insere dans la base de donnee
            Controleur.insertTechnicien(unTechnicien);

            //on actualise affichage apres insection
            this.unTableau.setDonnees(this.obtenirDonnees(""));
			
            //on vide les champs
            this.txtNom.setText("");
			this.txtPrenom.setText("");
			this.txtSpecialite.setText("");
			this.txtDateEmbauche.setText("");

            //affiche un message de confirmation
			JOptionPane.showMessageDialog(this, "insertion réussie");

            //actualisation de la liste de clients dans le panel produits
            PanelProduits.remplirCBXClients();

            //actualise le nombre de client
            this.txtNbTechniciens.setText("le nombre technicien est de : " + this.unTableau.getRowCount());
		}
        else if(e.getSource() == btFiltrer) 
        {
            String filtre = this.txtFiltre.getText();

            //actualiser
            this.unTableau.setDonnees(this.obtenirDonnees(filtre));
        }
        else if(e.getSource() == this.btEnregistrer && this.btEnregistrer.getText().equals("Modifier")) 
        {
            String nom = this.txtNom.getText();
			String prenom = this.txtPrenom.getText();
			String specialite = this.txtSpecialite.getText();
			String dateEmbauche = this.txtDateEmbauche.getText();

            int numLigne = this.tableTechniciens.getSelectedRow();
            int idtechnicien = Integer.parseInt(this.unTableau.getValueAt(numLigne, 0).toString());

            //instancier un client dant le bdd
            Technicien unTechnicien = new Technicien(idtechnicien, nom, prenom, specialite, dateEmbauche);

            //modification des donnee du clients affichage
            Controleur.updateTechnicien(unTechnicien);

            this.unTableau.setDonnees(this.obtenirDonnees(""));

            //actualiser la liste des clients dans le panel produits
            PanelProduits.remplirCBXClients();

            //on vide les champs et remet enregistrer
            this.txtNom.setText("");
			this.txtPrenom.setText("");
			this.txtSpecialite.setText("");
			this.txtDateEmbauche.setText("");
            this.btEnregistrer.setText("Enregistrer");
            JOptionPane.showMessageDialog(this, "Modification reussie du client");
        }
    }
}
