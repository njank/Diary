package diary;

import java.io.File;
import utils.DateUtils;
import utils.EncryptUtils;
import utils.IOUtils;
import view.*;

public class Model {
    private Controller controller;
    public MainFrame mainFrame = null;
    private LoginFrame loginFrame = null;
    private CreateUserDialog createUserDialog = null;
    
    public final int MIN_PWD_LENGTH = 3;
    
    private User user = null;
    public Log log;
    
    
    public Model() {
        controller = new Controller(this);
        loginFrame = new LoginFrame(this);
        
        
        
    }

    public void login(String username, String password) {
        user = new User(username, validatePassword(password));
        login();
    }
    public void login() {
        if((log = loadLog()) != null) {
            printMessage("Login.");
            loginFrame.dispose();
            mainFrame = new MainFrame(this, controller);
        }
    }

    public void logOut() {
        saveLog(true);
        printMessage("Logout.");
        mainFrame.dispose();
        loginFrame = new LoginFrame(this);
    }

    public void exit() {
        saveLog(true);
        printMessage("Exit.");
        System.exit(0);
    }

    public void createUserDialog() {
        createUserDialog = new CreateUserDialog(this);
    }

    public void createUser(String username, String password) {
        user = new User(username, validatePassword(password));
        log = new Log();
        if(!saveLog(false))
            loginFrame.errorDialog("Username already in use!");
        else {
            printMessage("User created.");
            createUserDialog.dispose();
            login();
        }
    }
    
    private String validatePassword(String password) {
        return (password+="                ").substring(0, 16);
    }

    public void createEntryDialog() {
        new CreateEntryDialog(this);
    }

    public void createEntry(String title, String text) {
        log.createEntry(title, text);
        mainFrame.refreshTable();
        saveLog(true);
    }
    
    public void editEntry(int row){
        new EditEntryDialog(this, log.getEntry(row));
    }
    
    public void editEntry(){
        if(mainFrame.table.getSelectedRow() != -1)
            editEntry(mainFrame.table.convertRowIndexToModel(mainFrame.table.getSelectedRow()));
    }
    
    public void deleteEntry(int row){
        log.deleteEntry(row);
        mainFrame.refreshTable();
        saveLog(true);
    }

    public void deleteEntry(){
        int[] selection = mainFrame.table.getSelectedRows();
        for(int i=selection.length-1; i>=0; i--) { // delete last first, don't move the indexes
            //System.out.println("Selection: "+selection[i]+", Modelrow: "+mainFrame.table.convertRowIndexToModel(selection[i]));
            deleteEntry(mainFrame.table.convertRowIndexToModel(selection[i]));
        }
    }
    
    
    private Log loadLog(){
        File f = user.getPath();
        if(!f.exists()){
            loginFrame.errorDialog("User not found!");
            return null;
        }
        String logString = EncryptUtils.decrypt(user.getPassword(), IOUtils.readFile(f));
        if(logString == null){
            loginFrame.errorDialog("Wrong Password\nor corrupted Log!");
            return null;
        }
        return new Log(logString);
    }

    public boolean saveLog(boolean override) { // if(override) ? save : create new
        new File("data").mkdir();
        File f = user.getPath();
        if(f.exists() && !override)
            return false;
        printMessage(f.exists()?"Log saved.":"Log created.");
        IOUtils.writeFile(f, EncryptUtils.encrypt(user.getPassword(), log.toString()));
        return true;
    }

    public void printMessage(String message) {
        System.out.println(DateUtils.toLongDateTime(System.currentTimeMillis())+" "+message);
    }

    public void info() {
        mainFrame.infoDialog("Diary v1.0\n(c) by Niklas Jank");
    }

    public void focusSearch() {
        mainFrame.filterTextField.selectAll();
        mainFrame.filterTextField.requestFocus();
    }

    public void search(String text) {
        log.search(text);
        mainFrame.refreshTable();
    }
}