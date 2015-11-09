package diary;

import java.util.ArrayList;
import java.util.ListIterator;
import javax.swing.table.AbstractTableModel;
import utils.DateUtils;

public class Log extends AbstractTableModel {
    private ArrayList<String[]>entries; // Title, Text, Date
    private ArrayList<String[]>entriesFiltered = null;
    
    private static final String columnSeperator = "%X";
    private static final String rowSeperator = "%Y";
    
    public Log() {
        entries = new ArrayList<>();
        entries.add(new String[]{
            "New Log created!",
            "You've successfully created your Log.",
            DateUtils.toDateTime(System.currentTimeMillis()) });
    }
    
    public Log(String logString) {
        String[]values = logString.split(columnSeperator);
        entries = new ArrayList<>();
        for(int i=0; i<values.length; i++)
            entries.add(values[i].split(rowSeperator));
        entriesFiltered = null; // nothing filtered yet
    }
    
    public String toString(){
        String ret = "";
        ListIterator it = entries.listIterator();
        while(it.hasNext()){
            String[]entry = (String[])it.next();
            for(int i=0; i<entry.length; i++)
                ret += entry[i]+rowSeperator;
            ret += columnSeperator;
        }
        return ret;
    }
    
    public ArrayList<String[]> getEntries(){
        return entriesFiltered==null ? entries : entriesFiltered;
    }
    
    public String[] getEntry(int row){
        return getEntries().get(row);
    }

    public void createEntry(String title, String text) {
        text = text.replaceAll(columnSeperator+"", "").replaceAll(rowSeperator+"", "");
        String[]entry = new String[]{ title, text, DateUtils.toDateTime(System.currentTimeMillis()) };
        entries.add(entry);
        if(entriesFiltered!=null)
            entriesFiltered.add(entry);
    }
    
    public void deleteEntry(int row){
        if(entriesFiltered!=null) {
            entries.remove(getEntries().get(row));
            entriesFiltered.remove(row);
        } else {
            entries.remove(row);
        }
    }
    
    public void search(String filterString){
        ArrayList<String[]> entriesTemp = (ArrayList<String[]>)entries.clone();
        ListIterator it = entriesTemp.listIterator();
        String[]entry;
        filterString = filterString.toLowerCase();
        entriesFiltered = new ArrayList<>();
        
        while(it.hasNext()){
            entry = (String[])it.next();
            if(entry[0].toLowerCase().contains(filterString)){ // if filterString is in title
                entriesFiltered.add(entry);
                it.remove();
            }
        }
        it = entriesTemp.listIterator();
        while(it.hasNext()){
            entry = (String[])it.next();
            if(entry[1].toLowerCase().contains(filterString)) // if filterString is in text
                entriesFiltered.add(entry);
        }
    }

    @Override
    public int getRowCount() {
        return getEntries().size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int row, int column) {
        String[]entry = getEntries().get(row);
        try {
            switch(column) {
                case 0: return row+1;
                case 1: return entry[0];
                case 2: return entry[2];
            }
        } catch(ArrayIndexOutOfBoundsException e) {
            System.err.println("Corrupted Log file!");
            System.exit(1);
        }
        return null;
    }
    
    @Override
    public String getColumnName(int column) {
        switch(column) {
            case 0: return "";
            case 1: return "Title";
            case 2: return "Date";
            default: return null;
        }
    }
}
