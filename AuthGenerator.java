package SchoolManager;

import java.util.*;
import java.io.*;


public class AuthGenerator {
    
    private final String id_store = "generated_ids.txt";
    
    private Crypter crypter;
    private List<String> generated_ids;
    private List<String> generated_auths;
    
    private Logger logger;
    
    
    public AuthGenerator(Logger logger,Crypter crypter){
        this.generated_ids = new ArrayList<>();
        this.generated_auths = new ArrayList<>();
        
        this.crypter = crypter;
        this.logger = logger;
        
        this.loadAllAuths();
        
    }
    
    
    public String generateID(String pref,int len){
        String id = pref;
        boolean stat = true;
        
        while(stat == true){
            for(int i = 1; i < len; i++){
                id += ""+(int)(Math.random()*9);
            }if(!this.generated_ids.contains(id)){
                this.generated_ids.add(id);
                stat = false;
                break;
            }
        } this.storeGeneratedIDs();
        return id;
    }
    
    public String generateID(String pref){
        String id = pref;
        boolean stat = true;
        
        while(stat == true){
            for(int i = 1; i < 6; i++){
                id += ""+(int)(Math.random()*9);
            }if(!this.generated_ids.contains(id)){
                this.generated_ids.add(id);
                stat = false;
                break;
            }
        } this.storeGeneratedIDs();
        return id;
    }
    
    public String generateID(){
        String id = "#";
        boolean stat = true;
        
        while(stat == true){
            for(int i = 1; i < 6; i++){
                id += ""+(int)(Math.random()*9);
            }if(!this.generated_ids.contains(id)){
                this.generated_ids.add(id);
                stat = false;
                break;
            }
        } this.storeGeneratedIDs();
        return id;
    }
    
    
    public String generateAuth(){
        String auth = "";
        boolean stat = true;
        
            List<String> alphanum = new ArrayList<>();
            alphanum.add("A");alphanum.add("B");alphanum.add("C");alphanum.add("J");
            alphanum.add("Q");alphanum.add("Y");alphanum.add("W");alphanum.add("Z");
            alphanum.add("R");alphanum.add("X");alphanum.add("S");alphanum.add("H");

        while(stat == true){
            for(int i = 0; i < 3; i++){
                int rnd = (int)(Math.random()*11);
                auth += alphanum.get(rnd);
            }
            for(int i = 0; i < 3; i++){
                auth += String.format("%s",(int)(Math.random()*9));
            }
            if(!this.generated_auths.contains(auth)){
                this.generated_auths.add(auth);
                stat = false;
               break;
            }
        }this.storeGeneratedAuths();
        return auth;
    }
    
    
    public void loadAllAuths(){
        //load both IDs and Auths
        String msg = "LOADED ALL GENERATED AUTHS SUCCESSFULLY!";
        try(BufferedReader br = new BufferedReader(new FileReader(this.id_store))){
            String line = "", type = "", data;
            String[] dta;
            while((line = br.readLine()) != null){
                if(!line.trim().isEmpty()){
                    data = this.crypter.decodeDecrypt(line);
                    dta = data.split(",");
                    type = dta[1];
                    switch(type){
                        case "id" :
                            this.generated_ids.add(dta[0]); break;
                        case "auth" :
                            this.generated_auths.add(dta[1]); break;
                    }
                }
            } logger.log(msg);
            System.out.println(msg);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    
     //{ID,type}
    private void storeGeneratedIDs(){
        if(this.generated_ids.size() > 0){ 
            String msg = "GENERATED IDS STORED SUCCESSFULLY";
            String data = "";
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(this.id_store));
        PrintWriter pw = new PrintWriter(bw);){
            for(String str: this.generated_ids){
                data = String.format("%s,%s",str,"auth");
                pw.println(this.crypter.encryptEncode(data));
            }
            pw.flush();
            pw.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
            System.out.println(msg);
            this.logger.log(msg);
        }
    }
    
    
    // {Auth,type}
    private void storeGeneratedAuths(){
        if(this.generated_auths.size() > 0){ 
            String msg = "GENERATED AUTHS STORED SUCCESSFULLY";
            String data = "";
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(this.id_store));
        PrintWriter pw = new PrintWriter(bw);){
            for(String str: this.generated_auths){
                data = String.format("%s,%s",str,"auth");
                pw.println(this.crypter.encryptEncode(data));
            }
            pw.flush();
            pw.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
            System.out.println(msg);
            this.logger.log(msg);
        }
    }
    
    
    private String MAD_GENERATE(){
        String auth = "";
        
            List<String> alphanum = new ArrayList<>();
            alphanum.add("A");alphanum.add("B");alphanum.add("C");alphanum.add("J");
            alphanum.add("Q");alphanum.add("Y");alphanum.add("W");alphanum.add("Z");
            alphanum.add("R");alphanum.add("X");alphanum.add("S");alphanum.add("H");
        
            for(int i = 0; i < 3; i++){
                int rnd = (int)(Math.random()*11);
                auth += alphanum.get(rnd);
            }
            for(int i = 0; i < 3; i++){
                auth += String.format("%s",(int)(Math.random()*9));
            }
        
        return auth;
    }
    
    
    public String MAD_PROTOCOL(int lines,int mark){
        String pass = "", f_pass = "", data = "";
        
        System.out.println("ENGAGING *MAD* PROTOCOL SEQUENCE! ");
        
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("security_config.txt"));
        PrintWriter pw = new PrintWriter(bw)){
            
            for(int i = 0; i < lines; i++){
                pass = this.MAD_GENERATE();
                if(i == mark){
                    f_pass = pass;
                }
                
                data = String.format("•ACCESS: %s",pass);
                pw.println(data);
            }pw.flush();pw.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        } return f_pass;
    }
    
    
    public String infoString(){
        return String.format("•=> Generated IDs: %s\n•=> Generated Auths: %s\n",this.generated_ids.size(),this.generated_auths.size());
    }
}