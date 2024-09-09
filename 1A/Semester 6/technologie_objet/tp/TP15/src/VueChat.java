import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class VueChat {

    /** Modèle du Chat. */
    private Chat modele;

    /** Fenêtre textuelle. */
    private JTextArea fenetreTextuelle;

    public VueChat(Chat modele, String nomUtilisateur) {
        this.modele = modele;

        // Création de la fenêtre principale
        JFrame fenetre = new JFrame("Chat de " + nomUtilisateur);
        Container contenu = fenetre.getContentPane();
        contenu.setLayout(new BorderLayout());

        // Ajout du bouton pour fermer le chat
        JButton boutonFermer = new JButton("Fermer");
        contenu.add(boutonFermer, BorderLayout.NORTH);

        // Ajout de la fenêtre textuelle
        fenetreTextuelle = new JTextArea();
        contenu.add(fenetreTextuelle, BorderLayout.CENTER);
        fenetreTextuelle.setEditable(false);
        fenetreTextuelle.append("Bienvenue sur le chat !");

        // Ajout de l'interaction utilisateur
        Container contenuInteractionUtilisateur = new Container();
        contenuInteractionUtilisateur.setLayout(new FlowLayout());

        JLabel nomUtilisateurLabel = new JLabel(nomUtilisateur);
        contenuInteractionUtilisateur.add(nomUtilisateurLabel);

        JTextField zoneText = new JTextField("Ecrire un message ici");
        contenuInteractionUtilisateur.add(zoneText);

        JButton boutonOK = new JButton("OK");
        contenuInteractionUtilisateur.add(boutonOK);

        contenu.add(contenuInteractionUtilisateur);

        // Afficher la fenêtre
        fenetre.pack();			    // redimmensionner la fenêtre
        fenetre.setVisible(true);	// l'afficher
    }


}
