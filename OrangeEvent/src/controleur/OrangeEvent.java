package controleur;

import vue.VueConnexion;
import vue.vueGenerale;

public class OrangeEvent 
{
    private static VueConnexion uneVueConnexion;
    private static vueGenerale uneVueGenerale;
    public static void main(String[] args) 
    {
        uneVueConnexion = new VueConnexion();
    }

    public static void rendreVisibleGenerale(boolean action, User unUser)
    {
        if(action == true) 
        {
            uneVueGenerale = new vueGenerale(unUser);
            uneVueGenerale.setVisible(true);     
        }
        else
        {
            uneVueGenerale.dispose();
        }
    }

    public static void rendreVisibleConnexion(boolean action)
    {
        uneVueConnexion.setVisible(action);
    }
}
