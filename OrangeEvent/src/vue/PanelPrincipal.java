package vue;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class PanelPrincipal extends JPanel
{
    public PanelPrincipal(String message)
    {
        this.setBounds(10, 80, 860, 540);
        this.setLayout(null);
        this.setBackground(Color.gray);
        JLabel lbTitre = new JLabel(message);
        lbTitre.setBounds(300, 40, 300, 40);

        //creation une police ecriture
        Font unePolice = new Font("", Font.BOLD, 20);
        lbTitre.setFont(unePolice);

        this.add(lbTitre);

        this.setVisible(false);
    }
}
