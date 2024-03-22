package com.adresse.vue;

import com.adresse.model.ManagerUtilisateur;
import com.adresse.model.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.adresse.model.HelperMethod.patternMatchesEmail;
import static com.adresse.model.HelperMethod.patternMatchesPassword;

public class UserForm extends JDialog {
    private JLabel jlName;
    private JTextField tfName;
    private JLabel jlFirstname;
    private JTextField tfFirstname;
    private JLabel jlEmail;
    private JTextField tfEmail;
    private JLabel jlPassword;
    private JPasswordField pfPassword;
    private JButton btValid;
    private JButton btCancel;
    private JPanel jpMain;
    private JPasswordField pfSecondPassword;
    private JLabel jfSecondPassword;

    public UserForm(JDialog parent){
        super(parent);

        setTitle("Ajouter un compte utilisateur");
        setContentPane(jpMain);
        setMaximumSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(800, 600));
        setVisible(true);
        btValid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //simplifier l'écriture du constructeur en appelant la méthode create()
                try {
                    createUser();
                } catch (SQLException | NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelUser();
            }
        });
    };

    //Méthode pour ajouter un utilisateur au clic sur le bouton "Ajouter" du formulaire + appeler la méthode dans le constructeur
    public void createUser() throws SQLException, NoSuchAlgorithmException {

        //récupérer le contenu des 5 inputs du formulaire
        String name = tfName.getText();
        String firstname = tfFirstname.getText();
        String email = tfEmail.getText();
        String password = String.valueOf(pfPassword.getPassword());
        String secondPassword = String.valueOf(pfSecondPassword.getPassword());

        //Créer un objet Utilisateur pour stocker les données
        Utilisateur utilisateur = new Utilisateur();

        //Vérifier que tous les champs sont remplis
        if (!name.isEmpty() && !firstname.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            System.out.println("Tous les champs sont bien remplis");

            //Vérifier que l'email correspond au format attendu
//            String regexPattern = "^(.+)@(.+)$";
//            Pattern pattern = Pattern.compile(regexPattern);
//            Matcher matcher = pattern.matcher(email);
//            System.out.println(matcher);

            //Boolean valideEmail = patternMatchesEmail(email);

            if (patternMatchesEmail(email)) {

                //Vérifier que le mot de passe a au moins 12 caractères
                if (patternMatchesPassword(password)) {

                    //Vérifier que les 2 mots de passe sont identiques
                    if (password.equals(secondPassword)) {
                        //Affecter les données à l'utilisateur
                        utilisateur.setName(name);
                        utilisateur.setFirstname(firstname);
                        utilisateur.setEmail(email);
                        utilisateur.setPassword(password);

                        System.out.println(utilisateur + " password : " + utilisateur.getPassword());

                        //Ajouter l'utilisateur en BDD
                        ManagerUtilisateur.create(utilisateur);

                        //Afficher une modale pour valider la création d'un compte
                        JOptionPane.showMessageDialog(null,
                                "Le compte a bien été ajouté en BDD",
                                "Valide",
                                JOptionPane.INFORMATION_MESSAGE);


                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Les 2 mot de passe ne sont pas identiques",
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                        //System.out.println("Les 2 mot de passe ne sont pas identiques");
                    }


                } else {
                    JOptionPane.showMessageDialog(null,
                            "Le mot de passe n'a pas le bon format : 12 caractères min, lettre min, maj et un chiffre",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }

            } else {
                //Modale qui s'ouvre pour l'utilisateur
                JOptionPane.showMessageDialog(null,
                        "Le mail n'a pas le bon format",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }

        } else {
            //Modale qui s'ouvre pour l'utilisateur
            JOptionPane.showMessageDialog(null,
                    "Veuillez remplir tous les champs",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cancelUser() {
        //Vider les 5 inputs du formulaire
        tfName.setText("");
        tfFirstname.setText("");
        tfEmail.setText("");
        pfPassword.setText("");

        System.out.println("Les champs ont bien été supprimé");

        //fermer la fenêtre et arrêter le programme
        dispose();
    }

}
