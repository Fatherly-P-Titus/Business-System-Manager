package SchoolManager;

import java.text.*;
import java.util.Date;

public class Log {
    
    String message;
    String date;
    String source; //type: INFO, WARNING, ERROR
    
    
    public Log(){
        this.message = "";
        this.source = "";
        this.date = "01/01 12:00";
    }
    
    public Log(String message){
        this.message = message;
        this.source = "INFO";
        this.date = (new SimpleDateFormat("dd/MM hh:mm").format(new Date()));//sdf.format(d);
    }
    
    public Log(String msg, String src){
        this.message = msg;
        this.source = src;
        this.date = (new SimpleDateFormat("dd/MM hh:mm").format(new Date()));//sdf.format(d);
    }
    
    public void setLog(String csv){
        String[]dta = csv.split(",");
        
        this.message = dta[0];
        this.source = dta[1];
        this.date = dta[2];
    }
    
    public String getMessage(){
        return this.message;
    }
    
    public String getSource(){
        return this.source;
    }
    
    
    @Override
    public String toString(){
        return String.format("%s,%s,%s",this.message,this.source,this.date);
    }
    
    
    
    /*
    
    return d.format(sdf)
    */
    
}