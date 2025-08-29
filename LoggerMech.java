package SchoolManager;

import java.util.*;
import java.util.Stack;
import java.io.*;

public class LoggerMech implements Logger{
    
    private final String logs_file = "logger_logs.txt";
    
    private Crypter crypter;
    
    private String source; //The source class logging
    
    //LOGS SHOULD BE STACK COLLECTION
    private Stack<Log> info_logs;
    private Stack<Log> warning_logs;
    private Stack<Log> error_logs;
    
    private Stack<Log> all_logs = new Stack<>();
    
    public LoggerMech(Crypter crypter){
        this.info_logs = new Stack<>();
        this.warning_logs = new Stack<>();
        this.error_logs = new Stack<>();
        
        this.source = "GENERAL";
        
        this.crypter = crypter;
        
        this.loadAllLogs();
        
        logINFO("LOGGER INITIALIZED !");
    }
    
    public LoggerMech(String source){
        this.info_logs = new Stack<>();
        this.warning_logs = new Stack<>();
        this.error_logs = new Stack<>();
        
        this.source = source;
        
        this.loadAllLogs();
        
        logINFO("LOGGER INITIALIZED !");
    }
    
    public void log(String msg){
        Log lg = new Log(msg,"INFO");
        all_logs.add(lg);
        info_logs.add(lg);
        this.storeLogsData();
    }
    
    public void logINFO(String msg){
        Log lg = new Log(msg,"INFO");
        all_logs.add(lg);
        info_logs.add(lg);
        this.storeLogsData();
    }
    
    public void logWARNING(String msg){
        Log lg = new Log(msg,"WARNING");
        all_logs.add(lg);
        warning_logs.add(lg);
        this.storeLogsData();
    }
    
    public void logERROR(String msg){
        Log lg = new Log(msg,"ERROR");
        all_logs.add(lg);
        error_logs.add(lg);
        this.storeLogsData();
    }
    
    public void setAllLogs(Stack<Log>logs){
        this.all_logs = logs;
        
        //distribute logs by specificity
        for(Log l: logs){
            if(l.getSource().equals("INFO")){
                this.info_logs.add(l);
            }
            if(l.getSource().equals("WARNING")){
                this.warning_logs.add(l);
            }
            if(l.getSource().equals("ERROR")){
                this.error_logs.add(l);
            }
        }
    }
    
    
    
    public void saveInfoLogs(){
        
    }
    
    public void saveWarningLogs(){
        
    }
    
    public void saveErrorLogs(){
        
    }
    
    
    public Stack<Log> getAllLogs(){
        return this.all_logs;
    }
    
    public List<Log> getRecentLogs(){
        //get last 6 logs (2 from each log db)
        List<Log> rcnt_logs = new ArrayList<>();
        Stack<Log> source_list = this.info_logs;
        int count = 0, lap = 1;
        
        for(int i = 1; i < 7; i++){
            rcnt_logs.add(source_list.pop());
            count += 1;
            
            if(count == 2){
                switch(lap){
                    case 2: source_list = warning_logs; break;
                    case 3: source_list = error_logs; break;
                }
                lap += 1;
                count = 0;
            }
        }
        return rcnt_logs;
    }
    
    
    public String report(){
        StringBuilder sb = new StringBuilder();
        
        sb.append(String.format("\n\t [LOG REPORTS FROM: %s]\n\n",this.source));
        
        for(Log lg: this.all_logs){
            sb.append(String.format("• %s",lg.toString()));
            sb.append("\n");
        } return sb.toString();
    }
    
    
    
    //FILE OPERATIONS FOR LOGS	
    private void storeLogsData(){
        //Save all three logs 
        try{
            String data;
            BufferedWriter bw = new BufferedWriter(new FileWriter(logs_file));
            PrintWriter pw = new PrintWriter(bw);
            
            for(Log lgs: this.all_logs){
                data = lgs.toString();
                pw.println(this.crypter.encryptEncode(data));
            }pw.flush(); pw.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    private Stack<Log> loadAllLogs(){
        Stack<Log> logs = new Stack<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(logs_file));
            String line, data; Log l = null;
            while((line = br.readLine()) != null){
                if(!line.trim().isEmpty()){
                    data = this.crypter.decodeDecrypt(line);
                    l = new Log();
                    l.setLog(data);
                
                switch(l.getSource()){
                    case "INFO":
                        this.info_logs.add(l); break;
                    case "WARNING":
                        this.warning_logs.add(l); break;
                    case "ERROR":
                        this.error_logs.add(l); break;
                } this.all_logs.add(l);
                }
            }
            br.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        return logs;
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    
    
}