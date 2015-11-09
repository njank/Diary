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
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class CreateUserDialog extends Frame {    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField passwordField2;
    private JButton infoButton;
    private JButton createButton;

    public CreateUserDialog(Model m) {
        super("Create User", m, null);
        
        p.setLayout(new GridLayout(4, 2));

        p.add(new JLabel("Username"));
        usernameField = new JTextField("");
        p.add(usernameField);

        p.add(new JLabel("Password"));
        passwordField = new JPasswordField("");
        passwordField.setEchoChar('*');
        passwordField.addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent e) {
                String p = new String(passwordField.getPassword());
                createButton.setEnabled(p.length() > 0 && p.equals(new String(passwordField2.getPassword())));
            }
        });
        p.add(passwordField);

        p.add(new JLabel("Confirm Password"));
        passwordField2 = new JPasswordField();
        passwordField2.setEchoChar('*');
        passwordField2.addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent e) {
                createButton.setEnabled(new String(passwordField.getPassword()).equals(new String(passwordField2.getPassword())));
            }
        });
        p.add(passwordField2);

        infoButton = new JButton("Info");
        infoButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                infoDialog("For best security choose a password with 16 characters!");
        }});
        p.add(infoButton);

        createButton = new JButton("Create");
        createButton.setEnabled(false);
        createButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                model.createUser(usernameField.getText(), new String(passwordField.getPassword()));
        }});
        getRootPane().setDefaultButton(createButton);
        p.add(createButton);
        
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
}
