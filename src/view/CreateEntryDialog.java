package view;

import diary.Model;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class CreateEntryDialog extends Frame {
    private JTextField title;
    private JTextArea text;
    private JButton createButton;

    public CreateEntryDialog(Model m) {
        super("Create new Entry", m, null);
        
        p.setLayout(new BorderLayout());
        
        JPanel northPanel = new JPanel(new GridLayout(3, 1));
        p.add(northPanel, BorderLayout.NORTH);
        
        northPanel.add(new JLabel("Title"));
        title = new JTextField("Title");
        northPanel.add(title);
        northPanel.add(new JLabel("Text"));
        
        text = new JTextArea("Text");
        text.setPreferredSize(new Dimension(320,240));
        p.add(text, BorderLayout.CENTER);

        createButton = new JButton("Create");
        createButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                dispose();
                model.createEntry(title.getText(), text.getText());
        }});
        getRootPane().setDefaultButton(createButton);
        p.add(createButton, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
}
