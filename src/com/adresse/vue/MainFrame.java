package com.adresse.vue;

import com.adresse.model.ManagerUtilisateur;
import com.adresse.model.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    private JPanel jpMain;

    public MainFrame(JFrame parent) throws SQLException {
        super();

        setTitle("Liste des utilisateurs");
        setContentPane(jpMain);
        setMaximumSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(800, 600));
        setVisible(true);


        ArrayList<Utilisateur> utilisateurs = ManagerUtilisateur.findAll();
        for(Utilisateur user:utilisateurs) {
            CardUser cardUser = new CardUser(user.getName()+" "+user.getFirstname());
            jpMain.add(cardUser);
            System.out.println(user.getFirstname() + user.getName());
        }
    }
}
