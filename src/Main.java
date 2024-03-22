import com.adresse.model.ManagerUtilisateur;
import com.adresse.model.Utilisateur;
import com.adresse.vue.UserForm;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException {
        //Utilisateur user = new Utilisateur("oceane", "hebrard", "julie@test.com", "123456");
        //test si le compte exist

        /* ------ Ajouter un utilisateur et vérifier si l'email existe déjà ------*/
//        if(Objects.equals(user.getEmail(), ManagerUtilisateur.findByMail(user).getEmail())){
//            System.out.println("Le compte existe déja");
//        }
//        else{
//            //ajouter le compte en BDD
//            System.out.println(ManagerUtilisateur.create(user));
//        }

        /* ------- Modifier les infos d'un utilisateur -------- */
//        if(Objects.equals(user.getEmail(), ManagerUtilisateur.findByMail(user).getEmail())) {
//            System.out.println("Le compte suivant a bien été modifié :" + ManagerUtilisateur.update(user));
//        } else {
//            System.out.println("Aucun compte n'existe");
//        }

        /* ------ Supprimer un utilisateur ---------- */
//        if(Objects.equals(user.getEmail(), ManagerUtilisateur.findByMail(user).getEmail())) {
//            System.out.println("Le compte suivant a bien été supprimé : " + ManagerUtilisateur.delete(user));
//        } else {
//            System.out.println("Le compte n'existe pas");
//        }

        /* ------ Afficher la liste de tous les utilisateurs ------ */
        //System.out.println(ManagerUtilisateur.findAll());

        UserForm formulaire = new UserForm(null);
    }
}
