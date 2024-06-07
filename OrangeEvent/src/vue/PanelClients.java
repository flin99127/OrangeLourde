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

import controleur.Client;
import controleur.Controleur;
import controleur.Tableau;

public class PanelClients extends PanelPrincipal implements ActionListener
{
    private JPanel panelForm = new JPanel();
    private JTextField txtNom = new JTextField();
    private JTextField txtPrenom = new JTextField();
    private JTextField txtEmail = new JTextField();
    private JTextField txtAdresse = new JTextField();
    private JButton btEnregistrer = new JButton("Enregistrer");
    private JButton btAnnuler = new JButton("Annuler");
    
    private JTable tableClients;
    private JScrollPane uneScroll;
    private Tableau unTableau;

    private JPanel panelFiltre = new JPanel();
    private JButton btFiltrer = new JButton("Filtrer");
    private JTextField txtFiltre = new JTextField();

    private JLabel txtNbClients = new JLabel();

    public PanelClients()
    {
        super("Gestion des clients");
        
        this.panelForm.setBackground(Color.gray);
        this.panelForm.setBounds(40, 100, 350, 300);
        this.panelForm.setLayout(new GridLayout(5, 2));
        this.panelForm.add(new JLabel("Nom user : "));
        this.panelForm.add(this.txtNom);
        this.panelForm.add(new JLabel("Prenom user : "));
        this.panelForm.add(this.txtPrenom);
        this.panelForm.add(new JLabel("Adresse user"));
        this.panelForm.add(this.txtAdresse);
        this.panelForm.add(new JLabel("Email user : "));
        this.panelForm.add(this.txtEmail);
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
        String entetes[] = {"Id Client", "Nom", "Prénom", "Adresse", "Email"};
        this.unTableau = new Tableau(this.obtenirDonnees(""), entetes);
        this.tableClients = new JTable(this.unTableau);
        this.uneScroll = new JScrollPane(this.tableClients);
        this.uneScroll.setBounds(420, 160, 350, 250);
        this.uneScroll.setBackground(Color.gray);
        this.add(this.uneScroll);

        //installer le label txtnbclient
        this.txtNbClients.setBounds(440, 440, 300, 20);
        this.txtNbClients.setText("le nombre client est de : " + this.unTableau.getRowCount());
        this.add(this.txtNbClients);

        //rendre les bouton ecoutable
        this.btAnnuler.addActionListener(this);
        this.btEnregistrer.addActionListener(this);

        this.tableClients.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                int numLigne, idclient;

                if(e.getClickCount() >= 2) 
                {
                    numLigne = tableClients.getSelectedRow();
                    idclient = Integer.parseInt(unTableau.getValueAt(numLigne, 0).toString());

                    int reponse = JOptionPane.showConfirmDialog(null, "voulez-vous supprimer le client ?", "Suppression idclient", JOptionPane.YES_NO_OPTION);

                    if(reponse == 0) 
                    {
                        //suppresion en BDD
                        Controleur.deleteClient(idclient);

                        //actualiser affichage
                        unTableau.setDonnees(obtenirDonnees(""));

                        //acutaliser la liste des clients dans le panel produits
                        PanelProduits.remplirCBXClients();

                        //actualiser le nombre client
                        txtNbClients.setText("le nombre client est de : " + unTableau.getRowCount());
                    }
                }
                else if(e.getClickCount() == 1) 
                {
                    numLigne = tableClients.getSelectedRow();
                    txtNom.setText(unTableau.getValueAt(numLigne, 1).toString());
                    txtPrenom.setText(unTableau.getValueAt(numLigne, 2).toString());
                    txtAdresse.setText(unTableau.getValueAt(numLigne, 3).toString());
                    txtEmail.setText(unTableau.getValueAt(numLigne, 4).toString());
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
        ArrayList<Client> lesClients = Controleur.selectAllClients(filtre);
        Object[][] matrice = new Object[lesClients.size()][6];

        int i = 0;

        for(Client unClient : lesClients)
        {
            matrice [i][0] = unClient.getIdClient();
            matrice [i][1] = unClient.getNom();
            matrice [i][2] = unClient.getPrenom();
            matrice [i][3] = unClient.getAdresse();
            matrice [i][4] = unClient.getEmail();
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
			this.txtEmail.setText("");
			this.txtAdresse.setText("");
            this.btEnregistrer.setText("Enregistrer");
		} 
        else if (e.getSource() == this.btEnregistrer && this.btEnregistrer.getText().contentEquals("Enregistrer")) 
        {
			String nom = this.txtNom.getText();
			String prenom = this.txtPrenom.getText();
			String email = this.txtEmail.getText();
			String adresse = this.txtAdresse.getText();
			Client unClient = new Client(nom, prenom, adresse, email);
			
            //on insere dans la base de donnee
            Controleur.insertClient(unClient);

            //on actualise affichage apres insection
            this.unTableau.setDonnees(this.obtenirDonnees(""));
			
            //on vide les champs
            this.txtNom.setText("");
			this.txtPrenom.setText("");
			this.txtAdresse.setText("");
			this.txtEmail.setText("");

            //affiche un message de confirmation
			JOptionPane.showMessageDialog(this, "insertion réussie");

            //actualisation de la liste de clients dans le panel produits
            PanelProduits.remplirCBXClients();

            //actualise le nombre de client
            this.txtNbClients.setText("le nombre client est de : " + this.unTableau.getRowCount());
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
			String email = this.txtEmail.getText();
			String adresse = this.txtAdresse.getText();

            int numLigne = this.tableClients.getSelectedRow();
            int idclient = Integer.parseInt(this.unTableau.getValueAt(numLigne, 0).toString());

            //instancier un client dant le bdd
            Client unClient = new Client(idclient, nom, prenom, adresse, email);

            //modification des donnee du clients affichage
            Controleur.updateClient(unClient);

            this.unTableau.setDonnees(this.obtenirDonnees(""));

            //actualiser la liste des clients dans le panel produits
            PanelProduits.remplirCBXClients();

            //on vide les champs et remet enregistrer
            this.txtNom.setText("");
			this.txtPrenom.setText("");
			this.txtEmail.setText("");
			this.txtAdresse.setText("");
            this.btEnregistrer.setText("Enregistrer");
            JOptionPane.showMessageDialog(this, "Modification reussie du client");
        }
    }
}
