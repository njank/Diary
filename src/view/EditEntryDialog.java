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

public class EditEntryDialog extends Frame {
    private JTextField title;
    private JTextArea text;
    private JButton createButton;

    public EditEntryDialog(Model m, final String[]entry) {
        super("Edit Entry", m, null);
        
        p.setLayout(new BorderLayout());
        
        JPanel northPanel = new JPanel(new GridLayout(3, 1));
        p.add(northPanel, BorderLayout.NORTH);
        
        northPanel.add(new JLabel("Title"));
        title = new JTextField(entry[0]);
        northPanel.add(title);
        northPanel.add(new JLabel("Text"));
        
        text = new JTextArea(entry[1]);
        text.setPreferredSize(new Dimension(320,240));
        p.add(text, BorderLayout.CENTER);

        createButton = new JButton("Save");
        createButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                dispose();
                entry[0] = title.getText();
                entry[1] = text.getText();
                model.mainFrame.refreshTable();
                model.saveLog(true);
        }});
        getRootPane().setDefaultButton(createButton);
        p.add(createButton, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
}
