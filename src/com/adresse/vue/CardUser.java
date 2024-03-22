package com.adresse.vue;

import javax.swing.*;
import java.awt.*;

public class CardUser extends JDialog {
    private JPanel jpCardUser;
    private JLabel jlNameFirstname;
    private JButton btUpdate;
    private JButton btDelete;

    public CardUser(String name) {
        super();

        setContentPane(jpCardUser);
        setMaximumSize(new Dimension(800, 50));
        setMinimumSize(new Dimension(800, 50));

        jlNameFirstname.setText(name);

        setVisible(true);

    }
}
