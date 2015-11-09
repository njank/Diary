package diary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener{
    private Model model;
    
    public Controller(Model model) {
        this.model = model;
    }
    
    public void actionPerformed(ActionEvent e) {        
        switch(e.getActionCommand()){
            case "New Entry": model.createEntryDialog(); break;
            case "Edit Entry": model.editEntry(); break;
            case "Delete Entry": model.deleteEntry(); break;
            case "Save": model.saveLog(true); break;
            case "Search": model.focusSearch(); break;
            case "Logout": model.logOut(); break;
            case "Exit": model.exit(); break;
            case "Info": model.info(); break;
        }
    }
}