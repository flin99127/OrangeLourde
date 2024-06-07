package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controleur.Controleur;
import controleur.User;

public class PanelProfil extends PanelPrincipal implements ActionListener
{
    private User unUser;
    private JTextArea txtInfos = new JTextArea();
    private JButton btModifier = new JButton("Modifier");

    private JPanel panelForm = new JPanel();
    private JTextField txtNom = new JPasswordField();
    private JTextField txtPrenom = new JPasswordField();
    private JTextField txtEmail = new JPasswordField();
    private JTextField txtMdp = new JPasswordField();
    private JTextField txtRole = new JPasswordField();
    private JButton btEnregistrer = new JButton("Enregistrer");
    private JButton btAnnuler = new JButton("Annuler");


    public PanelProfil(User unUser)
    {
        super("Gestion des infos du profil");
        this.unUser = unUser;

        this.txtInfos.setText(
            "_______ Les informations de votre profil_____"
            + "\n\n Votre nom : " + unUser.getNom() 
            + "\n\n Votre Prénom : " + unUser.getPrenom() 
            + "\n\n Votre Email : " + unUser.getEmail()
            + "\n\n Votre Role" + unUser.getRole()
        );
        this.txtInfos.setBounds(50, 80, 300, 200);
        this.txtInfos.setBackground(Color.gray);
        this.add(this.txtInfos);

        this.btModifier.setBounds(100, 360, 50, 30);
        this.add(btModifier);

        //installation du formulaire 
        this.panelForm.setBackground(Color.gray);
        this.panelForm.setBounds(300, 120, 300, 300);
        this.setLayout(new GridLayout(6, 2));
        
        this.panelForm.add(new JLabel("nom user : "));
        this.panelForm.add(this.txtNom);

        this.panelForm.add(new JLabel("Prenom user : "));
        this.panelForm.add(this.txtPrenom);

        this.panelForm.add(new JLabel("email : "));
        this.panelForm.add(this.txtEmail);

        this.panelForm.add(new JLabel("mdp : "));
        this.panelForm.add(this.txtMdp);

        this.panelForm.add(new JLabel("role : "));
        this.panelForm.add(this.txtRole);

        this.panelForm.add(this.btAnnuler);
        this.panelForm.add(btEnregistrer);
        this.add(this.panelForm);
        this.panelForm.setVisible(false);

        //rendre ecoutable
        this.btAnnuler.addActionListener(this);
        this.btEnregistrer.addActionListener(this);
        this.btModifier.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == btModifier) 
        {
            this.txtNom.setText(this.unUser.getNom());
            this.txtPrenom.setText(this.unUser.getPrenom());
            this.txtEmail.setText(this.unUser.getNom());
            this.txtMdp.setText(this.unUser.getNom());
            this.txtRole.setText(this.unUser.getNom());
            this.panelForm.setVisible(true);
        }
        else if(e.getSource() == this.btAnnuler) 
        {
            this.panelForm.setVisible(false);
        }
        else if(e.getSource() == this.btEnregistrer) 
        {
            String nom = this.txtNom.getText();
            String prenom = this.txtPrenom.getText();
            String email = this.txtEmail.getText();
            String mdp = new String(((JPasswordField)this.txtMdp).getPassword());
            String role = this.txtRole.getText();

            this.unUser.setNom(nom);
            this.unUser.setPrenom(prenom);
            this.unUser.setEmail(email);
            this.unUser.setMdp(mdp);
            this.unUser.setRole(role);

            Controleur.updateUser(this.unUser);
            this.txtInfos.setText(
                "Informations de votre profil_____"
                + "\n\n Votre nom : " + unUser.getNom()
                + "\n\n Votre nom : " + unUser.getPrenom()
                + "\n\n Votre nom : " + unUser.getEmail()
                + "\n\n Votre nom : " + unUser.getRole()
            );

            this.panelForm.setVisible(false);
            JOptionPane.showMessageDialog(this, "Modification effectués");
        }
    }
}
