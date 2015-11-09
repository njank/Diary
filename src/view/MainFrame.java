package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.WindowConstants;
import diary.Controller;
import diary.Model;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import static java.awt.event.KeyEvent.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import utils.IOUtils;


public class MainFrame extends Frame {    
    public static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static final String TITLE = "Diary";
    
    public JTable table;
    public JScrollPane scrollPane = null;
    public JTextField filterTextField;
    public String filterString = "";
    
    public MainFrame(Model m, Controller c) {
        super(TITLE, m, c);
        
        this.initMenuBar();
        this.initPanels();
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setSize(new Dimension(800,600));
        this.setLocationRelativeTo(null);
        
        this.setVisible(true);
    }

    private void initPanels(){
        p.setLayout(new BorderLayout());
        
        // buttonPanel
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.LEFT);
        fl.setHgap(0);
        fl.setVgap(0);
        JPanel buttonPanel = new JPanel(fl);
        buttonPanel.add(new JLabel());
        
        // Buttons
        JButton addButton = new ImageTextButton("New Entry", "add.png", controller);
        getRootPane().setDefaultButton(addButton);
        buttonPanel.add(addButton);
        
        buttonPanel.add(new ImageTextButton("Edit Entry", "edit.png", controller));
        buttonPanel.add(new ImageTextButton("Delete Entry", "delete.png", controller));
        buttonPanel.add(new JLabel("  |  "));
        buttonPanel.add(new ImageTextButton("Save", "save.png", controller));
        buttonPanel.add(new JLabel("  |  "));
        
        JLabel filterLabel = new JLabel("Search: ");
        filterLabel.setIcon(IOUtils.readImage("search.png"));
        buttonPanel.add(filterLabel);
        filterTextField = new JTextField("Text");
        filterTextField.setPreferredSize(new Dimension(150,26));
        filterTextField.addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent e) {
                model.search(filterTextField.getText());
            }
        });
        buttonPanel.add(filterTextField);
        
        p.add(buttonPanel, BorderLayout.NORTH);
        
        // Table
        refreshTable();
    }
    
    public void refreshTable(){        
        if(scrollPane != null)
            p.remove(scrollPane);
        table = new JTable(model.log);
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                if(me.getClickCount() == 2) //convertRowIndexToModel because if sorted the index is different
                    model.editEntry(table.convertRowIndexToModel(table.rowAtPoint(me.getPoint())));
            }
        });
        table.setAutoCreateRowSorter(true);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        scrollPane = new JScrollPane(table);
        p.add(scrollPane, BorderLayout.CENTER);
        
//        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model.log);
//        sorter.setRowFilter(RowFilter.regexFilter(".*"+filterString+".*"));
//        table.setRowSorter(sorter);
        
        validate();
    }
    
    public void initMenuBar(){
        JMenuBar mb=new JMenuBar();
        JMenuItem mi;
        JMenu m;
        
        m=new JMenu("File");
            mi=new JMenuItem("Save");
            mi.addActionListener(controller);
            mi.setAccelerator(KeyStroke.getKeyStroke('S', CTRL_DOWN_MASK));
            m.add(mi);
            mi=new JMenuItem("Search");
            mi.addActionListener(controller);
            mi.setAccelerator(KeyStroke.getKeyStroke('F', CTRL_DOWN_MASK));
            m.add(mi);
            mi=new JMenuItem("Logout");
            mi.addActionListener(controller);
            mi.setAccelerator(KeyStroke.getKeyStroke('W', CTRL_DOWN_MASK));
            m.add(mi);
            mi=new JMenuItem("Exit");
            mi.addActionListener(controller);
            m.add(mi);
        mb.add(m);
        
        m=new JMenu("Entry");
            mi=new JMenuItem("Create Entry");
            mi.addActionListener(controller);
            mi.setAccelerator(KeyStroke.getKeyStroke('N', CTRL_DOWN_MASK));
            m.add(mi);
            mi=new JMenuItem("Edit Entry");
            mi.addActionListener(controller);
            m.add(mi);
            mi=new JMenuItem("Delete Entry");
            mi.addActionListener(controller);
            mi.setAccelerator(KeyStroke.getKeyStroke(VK_DELETE,0));
            m.add(mi);
        mb.add(m);
        
        m=new JMenu("Help");
            mi=new JMenuItem("Info");
            mi.addActionListener(controller);
            m.add(mi);
        mb.add(m);
        
        setJMenuBar(mb);
    }
    
    public void setVisible(boolean visible){
        if(visible)
            super.setVisible(true);
        else {
            model.exit();
        }
    }
}

class ImageTextButton extends JButton{
    public ImageTextButton(String name, String filename, ActionListener actionListener) {
        super(name);
        this.addActionListener(actionListener);
        this.setIcon(IOUtils.readImage(filename));
        //this.setPreferredSize(new Dimension(26, 26));
        //this.setToolTipText(name);
    }
}

class ImageButton extends JButton{
    public ImageButton(String name, String filename, ActionListener actionListener) {
        super(name);
        this.setText("");
        this.addActionListener(actionListener);
        this.setIcon(IOUtils.readImage(filename));
        //this.setPreferredSize(new Dimension(26, 26));
        //this.setToolTipText(name);
    }
}