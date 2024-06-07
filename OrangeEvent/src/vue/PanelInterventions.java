package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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
    private JComboBox<String> txtIdProduit = new JComboBox<String>();
    private static JComboBox<String> txtIdTechnicien = new JComboBox<String>();
    private JButton btEnregistrer = new JButton("Enregistrer");
    private JButton btAnnuler = new JButton("Annuler");
    
    private JTable tableInter;
    private JScrollPane uneScroll;
    private Tableau unTableau;
    private JLabel txtNbInter = new JLabel();

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

        //construction du panel filtre
        String entetes[] = {"Id Intervention", "Description", "Prix intervention", "Date intervention", "Id produit", "Id technicien"};
        this.unTableau = new Tableau(this.obtenirDonnees(""), entetes);
        this.tableInter = new JTable(this.unTableau);
        this.uneScroll = new JScrollPane(this.tableInter);
        this.uneScroll.setBounds(420, 160, 350, 250);
        this.uneScroll.setBackground(Color.gray);
        this.add(this.uneScroll);

        this.remplirCBX();

        //installer le label txtnbprodui
        this.txtNbInter.setBounds(440, 440, 300, 20);
        this.txtNbInter.setText("le nombre produit est de : " + this.unTableau.getRowCount());
        this.add(this.txtNbInter);
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

        for(Intervention unIntervention : lesInterventions)
        {
            matrice [i][0] = unIntervention.getIdinter();
            matrice [i][1] = unIntervention.getDescription();
            matrice [i][2] = unIntervention.getPrixInter();
            matrice [i][3] = unIntervention.getDateInter();
            matrice [i][4] = unIntervention.getIntproduit();
            matrice [i][5] = unIntervention.getIdtechnicien();
            i++;
        }
        return matrice;
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
         this.unTableau.setDonnees(this.obtenirDonnees(""));

            //actualiser la liste des clients dans le panel produits
            PanelProduits.remplirCBXClients();
    }
}
