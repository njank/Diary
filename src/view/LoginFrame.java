package view;

import diary.Model;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class LoginFrame extends Frame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createButton;

    public LoginFrame(Model m) {
        super("User Login", m, null);
        
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        p.setLayout(new GridLayout(3, 2));
        
        p.add(new JLabel("Username"));
        usernameField = new JTextField("");
        p.add(usernameField);

        p.add(new JLabel("Password"));
        passwordField = new JPasswordField("");
        passwordField.setEchoChar('*');
        p.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                model.login(usernameField.getText(), new String(passwordField.getPassword()));
        }});
        getRootPane().setDefaultButton(loginButton);
        p.add(loginButton);

        createButton = new JButton("Create new User");
        createButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                model.createUserDialog();
        }});
        p.add(createButton);
        
        pack();
        setVisible(true);
    }
}
