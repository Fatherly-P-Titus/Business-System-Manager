package SchoolManager;

import java.io.*;
import java.util.*;

public class ADMIN {
    
    
    private final String security_config_file = "security_config";
    private final String designated_auths_file = "designated_auths.txt";
    
    private String SYSTEM_PASS = "";
    private String ADMIN_PASS = "";
    private String ADMIN_AUTH = "";
    
    private Crypter crypter;
    private Logger logger;
    private SystemFileOps sfo;
    private AuthGenerator auther;
    
    
    private List<String> designated_auths;
    
    public ADMIN(){
        this.crypter = new CrypterSymmetricMech();
        this.logger = new LoggerMech(this.crypter);
        this.sfo = new SystemFileMech(this.crypter,this.logger);
        this.auther = new AuthGenerator(this.logger,this.crypter);
        
        this.designated_auths = new ArrayList<>();
        this.loadDesignatedAuths();
    }
    
    ////////////////////////////////////////////////////////////////
    
    public void setSystemPass(String p1,String pass){
        if(p1.equals(this.ADMIN_PASS)){
            this.SYSTEM_PASS = pass;
        }
    }
    
    public void setAdminPass(String p1,String pass){
        if(p1.equals(SYSTEM_PASS)){
            this.ADMIN_PASS = pass;
        }
    }
    
    
    
    public Crypter getSystemCrypter(){
        return this.crypter;
    }
    
    public Logger getSystemLogger(){
        return this.logger;
    }
    
    public AuthGenerator getSystemAuthGenerator(){
        return this.auther;
    }
    
    public SystemFileOps getSystemFileMech(){
        return this.sfo;
    }
    
    
    public boolean validateSystemAccess(String sys_pass,String admin_pass,String admin_auth){
        boolean stat = false;
        
        if(sys_pass.equals(this.SYSTEM_PASS)){
            if(admin_pass.equals(this.ADMIN_PASS)){
                if(this.designated_auths.contains(admin_auth)){
                    stat = true;
                }
            }
        }
        return stat;
    }
    
    
    public void addDesignatedAuth(String auth){
        this.designated_auths.add(auth);
        
        this.storeDesignatedAuths();
    }
    
    
    ////////////////////////////////////////////////////////
    private void loadDesignatedAuths(){
        
        try(BufferedReader br = new BufferedReader(new FileReader(designated_auths_file))){
            String line, data;
            while((line = br.readLine()) != null){
                if(!line.trim().isEmpty()){
                    data = this.crypter.decodeDecrypt(line);
                    this.designated_auths.add(data);
                }
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        
    }
    
    
    private void storeDesignatedAuths(){
        
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(this.designated_auths_file,false));
        PrintWriter pw = new PrintWriter(bw)){
            String data;
            
            for(String str: this.designated_auths){
                data = this.crypter.encryptEncode(str);
                pw.println(data);
            }pw.flush(); pw.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        
    }
    
    public void loadSecurityConfig(){
        try{
            BufferedReader br = new BufferedReader( new FileReader(this.security_config_file));
            String line, data;
            String[] dta;
            
            while((line = br.readLine()) != null){
                if(!line.trim().isEmpty()){
                    data = this.crypter.decodeDecrypt(line);
                    
                    dta = data.split(",");
                    this.SYSTEM_PASS = dta[0];
                    this.ADMIN_PASS = dta[1];
                    this.ADMIN_AUTH = dta[2];
                }
            } br.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    private void storeSecurityConfig(){
        
            String data = "";
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(this.security_config_file));
            PrintWriter pw = new PrintWriter(bw)){
                
                data = String.format("%s,%s,%s",this. SYSTEM_PASS,this.ADMIN_PASS,this.ADMIN_AUTH);
                pw.println(this.crypter.encryptEncode(data));
                
                pw.flush(); pw.close();
                this.logger.log("STORED INVENTORY RECORDS DATA SUCCESSFULLY");
            }catch(IOException ioe){
                ioe.printStackTrace();
            }
    }
    
    
}