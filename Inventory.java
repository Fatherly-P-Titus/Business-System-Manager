package SchoolManager;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class Inventory {
    
    private String item;
    private int amount;
    private String date_entered;
    private String date_updated;
    
    private String ID;
    
    private Date d = new Date();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    
    public Inventory(String item, int amount){
        this.item = item;
        this.amount = amount;
        
        this.date_entered = this.sdf.format(d);
        this.date_updated = this.date_entered;
        this.ID = "ITEM_ID";
    }
    
    public Inventory(String csv){
        String[] dta = csv.split(",");
        
        this.ID = dta[0];
        this.item = dta[1];
        this.amount = Integer.parseInt(dta[2]);
        this.date_entered = dta[3];
        this.date_updated = dta[4];
    }
    
    
    
    public void setID(String id){
        this.ID = id;
    }
    
    
    
    public String getID(){
        return this.ID;
    }
    
    public String getItem (){
        return this.item;
    }
    
    public String getDateStored (){
        return this.date_entered;
    }
    
    public int getItemCount(){
        return this.amount;
    }
    
    public String getDateUpdated (){
        return this.date_updated;
    }
    
    public String infoString(){
        return String.format("ITEM: %s\nITEM COUNT: %s\nDATE OF ENTRY: %s\nDATE OF LAST UPDATE: \n",
            this.item,this.amount,this.date_entered,this.date_updated);
    }
    
    public String toString(){
        return String.format("%s,%s,%s,%s,%s,%s",this.ID,this.item,this.amount,this.date_entered,this.date_updated);
    }
}

/*
_______ |_______|
|______|
*/