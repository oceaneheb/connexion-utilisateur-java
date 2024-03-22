package com.adresse.model;
import java.security.MessageDigest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class ManagerUtilisateur {
    private static final Connection connexion = Database.getConnexion();

    /* ---------- Ajouter un utilisateur ---------------*/
    public static Utilisateur create(Utilisateur user) throws SQLException, NoSuchAlgorithmException {
        //NoSuchAlgorithmException -> message d'erreur si problème de hâchage du mdp

        //créer un objet Utilisateur
        Utilisateur userAdd = new Utilisateur();

        //hacher le mot de passe
        String password = user.getPassword();

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());

        byte byteData[] = md.digest();
        //convertir en tableau de bits en un format hexadécimal
        StringBuffer sb  = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        System.out.println("En format hexa : " + sb.toString());

        //try la requête
        try {
            //connecter à la bdd
            Statement smt = connexion.createStatement();
            //préparation de la requête
            String req = "INSERT INTO users(name,firstname,email,password) VALUE(?,?,?,?)";
            PreparedStatement preparedStatement = connexion.prepareStatement(req);
            //binder les paramètres
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getFirstname());
            preparedStatement.setString(3, user.getEmail());
            //preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(4, sb.toString());

            //exécuter la requête
            int row = preparedStatement.executeUpdate();

            //tester si la requête à réussi
            if(row > 0) {
                userAdd.setName(user.getName());
                userAdd.setFirstname(user.getFirstname());
                userAdd.setEmail(user.getEmail());
                //userAdd.setPassword(user.getPassword());
                userAdd.setPassword(sb.toString());
            }
            //recupérer l'enregistrement
        }
        //lever l'erreur SQL
        catch (SQLException e){
            e.printStackTrace();
        }
        //retourne un objet utilisateur complet
        return userAdd;
    }

    /* -------------- Trouver un utilisateur par son email --------------*/
    public static Utilisateur findByMail(Utilisateur user) throws SQLException{
        Utilisateur userRecup = new Utilisateur();
        try {
            //connexion à la bdd
            Statement smt = connexion.createStatement();
            //requête
            String req = "SELECT id, name, firstname, email, password FROM users WHERE email = ?";
            //préparer la requête
            PreparedStatement preparedStatement = connexion.prepareStatement(req);
            //binder le paramètre
            preparedStatement.setString(1, user.getEmail());
            //exécuter la requête
            ResultSet reponse = preparedStatement.executeQuery();
            //boucle pour parcourir le résultat
            while (reponse.next()) {
                if(reponse.getString(1) !=null){
                    //setter les nouvelles valeurs
                    userRecup.setId(reponse.getInt(1));
                    userRecup.setName(reponse.getString("name"));
                    userRecup.setFirstname(reponse.getString("firstname"));
                    userRecup.setEmail(reponse.getString("email"));
                    userRecup.setPassword(reponse.getString("password"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //retourne l'objet Utilisateur
        return userRecup;
    }

    /* --------------- Retourner l'ensemble des utilisateurs ----------------- */
    public static ArrayList<Utilisateur> findAll() throws SQLException {
        //Utilisateur[] utilisateurs[] = {};
        ArrayList<Utilisateur> utilisateurs = new ArrayList<>();

        try {
            //Connexion à la BDD
            Statement smt = connexion.createStatement();
            //préparation de la requête
            String req = "SELECT name, firstname, email FROM users";
            PreparedStatement preparedStatement = connexion.prepareStatement(req);

            //exécuter la requête
            ResultSet reponse = preparedStatement.executeQuery();
            System.out.println(reponse);



            while (reponse.next()) {
                Utilisateur utilisateur = new Utilisateur();

                utilisateur.setName(reponse.getString("name"));
                utilisateur.setFirstname(reponse.getString("firstname"));
                utilisateur.setEmail(reponse.getString("email"));

                utilisateurs.add(utilisateur);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateurs;
    }

    /* ----------------- Modifier nom et prénom de l'utilisateur ------------*/
    public static Utilisateur update(Utilisateur user) throws  SQLException {
        //créer un objet Utilisateur
        Utilisateur userUpdate = new Utilisateur();

        try {
            //connecter à la bdd
            Statement smt = connexion.createStatement();
            //préparation de la requête
            String req = "UPDATE users SET name = ?, firstname = ? WHERE email = ?";
            PreparedStatement preparedStatement = connexion.prepareStatement(req);
            //binder le paramètre
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getFirstname());
            preparedStatement.setString(3, user.getEmail());
            //exécuter la requête
            int row = preparedStatement.executeUpdate();
            //tester si la requête fonctionne
            if(row > 0) {
                userUpdate.setName(user.getName());
                userUpdate.setFirstname(user.getFirstname());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //retourne un objet Utilisateur
        return userUpdate;
    }


    /* --------------- Supprimer un utilisateur ------------------ */
    public static Utilisateur delete(Utilisateur user) throws SQLException {
        Utilisateur userDelete = new Utilisateur();

        try {
            //connexion à la bdd
            Statement smt = connexion.createStatement();
            //préparer la requête
            String req = "DELETE FROM users WHERE email = ?";
            PreparedStatement preparedStatement = connexion.prepareStatement(req);
            //binder le param
            preparedStatement.setString(1, user.getEmail());
            //exécuter la requête
            int row = preparedStatement.executeUpdate();
            //tester si la requête fonctionne
            if (row > 0) {
                userDelete.setEmail(user.getEmail());
                userDelete.setName(user.getName());
                userDelete.setFirstname(user.getFirstname());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //retourne un objet Utilisateur
        return userDelete;
    }
}
