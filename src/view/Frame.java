package view;

import diary.Controller;
import diary.Model;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public abstract class Frame extends JFrame {
    protected Model model;
    protected Controller controller;
    
    protected JPanel p;
    
    public Frame(String title, Model model, Controller controller){
        super(title);
        this.model = model;
        this.controller = controller;
        
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        this.initLookAndFeel();
        this.initPanel();
    }
    
    private void initPanel() {
        p = new JPanel();
        p.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(p);
    }
    
    private void initLookAndFeel(){
        try{
            javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            javax.swing.SwingUtilities.updateComponentTreeUI(this);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void errorDialog(String message){
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public void infoDialog(String message){
        JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}
