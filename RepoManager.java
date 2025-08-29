package SchoolManager;

import java.util.*;
import java.util.Scanner;
import static java.lang.System.out;

import java.io.*;


public class RepoManager {
    
    private final String staff_data_file = "staff_repo.txt";
    
    private Logger logger = new LoggerMech("REPO MANAGER");
    
    private List<Staff> staff_repo;
    private List<Staff> query_matches;
    
    private Crypter crypter;
    
    private boolean repo_updated = false;
    
    public RepoManager(Crypter crypter){
        this.crypter = crypter;
        this.staff_repo = this.loadStaffRepositoryData();
            
        this.query_matches = new ArrayList<>();
    }
    
    /////////////////////////////////////////////////////////////////////////////////////
    
    public int getRepoSize(){
        return this.staff_repo.size();
    }
    
    public List<Staff> getStaffRepo(){
    	return this.staff_repo;
    }
    
    public List<Staff> getQueryMatches(){
    	return this.query_matches;
    }
    
    public void clearQueryMatches() {
    	this.query_matches = new ArrayList<>();
    }
    
    public void staffRepoUpdated(){
        this.repo_updated = true;
    }
    
    public void saveRepo(){
        if(this.repo_updated){
           this.storeStaffRepositoryData(this.staff_repo); 
        }this.repo_updated = false;
    }
    
    
    //COMMANDS FOR PERSONNEL REPOSITORY
    
    public void appendStaffRepo(Staff p){
        this.staff_repo.add(p);
        this.staffRepoUpdated();
        logger.logINFO("New Staff Added To Staff Repo");
    }
    
    public void removeFromStaffRepo(Staff p){
        if(this.staff_repo.contains(p)){
            this.staff_repo.remove(p);
            this.staffRepoUpdated();
            logger.logINFO("Staff Removed From Staff Repo");
        }
    }
    
    public List<Staff> getStaffByAttribute(String attr, Object value){
        Staff match = null;
        
        for(Staff p: this.staff_repo){
            switch(attr){
                case "NAME" : 
                    if(Objects.equals(p.getName(),value)){
                      match = p; 
                    }; break;
                case "AGE" :
                    if(Objects.equals(p.getAge(),value)){
                        match = p;
                    }; break;
                case "GENDER" :
                    if(Objects.equals(p.getGender(),value)){
                        match = p;
                    }; break;
                case "DESIGNATION" :
                    if(Objects.equals(p.getDesignation(),value)){
                        match = p;
                    }; break;
                case "ADDRESS" :
                    if(Objects.equals(p.getAddress(),value)){
                        match = p;
                    }; break;
                case "AUTH" :
                if(Objects.equals(p.getAuth(),value)){
                    match = p;
                }; break;
                case "ID" :
                if(Objects.equals(p.getID(), value)){
                    match = p;
                }
            }
            if(match != null) {
            	this.query_matches.add(match);
            	match = null;
            }
        } return this.query_matches;
    }
    
    public Staff displayQueryMatches () {
    	out.println("\t [DISPLAYING QUERY RESULTS] \n");
    	
    	 Map<String,Staff> list = new HashMap<>();
         Staff p = null;
         Scanner sc = new Scanner(System.in);
         
         int count = 1;
         for(Staff ps: this.query_matches){
                 out.println(String.format("%s) %s\n",count,ps.infoString()));
                 list.put(""+count,ps);
                 count += 1;
         }
        
         out.printf("\t SELECT PERSONNEL: ");
         String option = sc.next();
         out.println();
         sc = null;
         
         if(list.containsKey(option)){
             p = list.get(option);
         }
         return p;
    }
    
    public void replaceStaff(Staff p, Staff pr){
        List<Staff> p_repo = new ArrayList<>();
        out.println("\n\t [REPLACING PERSONNEL]\n");
        
        for(Staff pl: this.staff_repo){
            if(pl.equals(p)){
                out.println("Found Target Object\nReplacing...\n");
                p_repo.add(pr);
            }else{
                p_repo.add(pl);
            }
        } this.staff_repo = p_repo;
        this.staffRepoUpdated(); 
        logger.logINFO(String.format("Performed Replacement Operation On Staff Repository"));
    }
    
    public List<Staff> queryStaffRepo(String attribute, String value){
        List<Staff> matches = new ArrayList<>();
        
        logger.logINFO(String.format("Querying Staff Repo: [%s = '%s']l",attribute,value));
        
        for(Staff p: this.staff_repo){
            switch(attribute){
                case "NAME" : 
                if(p.getName().equals(value)){
                    matches.add(p);
                } break;
                case "DESIGNATION" :
                if(p.getDesignation().equals(value)){
                    matches.add(p);
                }break;
                case "PHONE" :
                if(p.getPhone().equals(value)){
                    matches.add(p);
                }break;
                case "ADDRESS" :
                if(p.getAddress().equals(value)){
                    matches.add(p);
                }break;
                case "GENDER" :
                if(p.getGender().equals(value)){
                    matches.add(p);
                }break;
                case "AGE" :
                if(p.getAge().equals(value)){
                    matches.add(p);
                }break;
            }
        }
        if(matches.size() > 0){
            logger.logINFO(String.format("\t- Query Matches Found: %s",matches.size()));
        }
        return matches;
    }
    
    
    public List<Staff> queryStaffRepo(String value){
        List<Staff> matches = new ArrayList<>();
        
        logger.logINFO(String.format("Querying Staff Repo: ['%s']",value));
        
        for(Staff p: this.staff_repo){
            if((p.getName().equals(value)) || 
                (p.getDesignation().equals(value)) ||
                (p.getPhone().equals(value)) ||
                (p.getAddress().equals(value)) ||
                (p.getGender().equals(value)) ||
                (p.getAge().equals(value))){
                    matches.add(p);
                } 
        }logger.logINFO(String.format("/t- Query Matches: %s",matches.size()));
        return matches;
    }
    
    
    public Staff listSelectStaffRepo(){
        Staff p = null;
        
        out.println("\n\t [PERSONNEL REPOSITORY LIST] \n");
        
        Map<String,Staff> list = new HashMap<>();
        
        Scanner sc = new Scanner(System.in);
        
        int count = 1;
        for(Staff ps: this.staff_repo){
                out.println(String.format("%s) %s\n",count,ps.infoString()));
                list.put(""+count,ps);
                count += 1;
        }
        out.printf("\t SELECT PERSONNEL: ");
        String option = sc.next();
        out.println();
        sc = null;
        if(list.containsKey(option)){
            p = list.get(option);
        }
        return p;
    }
    
    public void listRecords(){
        out.println("\n\t [PERSONNEL REPOSITORY LIST] \n");
        
        out.println("REPO SIZE: "+this.staff_repo.size());
        
        Map<String,Staff> list = new HashMap<>();
        
        int count = 1;
        for(Staff ps: this.staff_repo){
                out.println(String.format("%s) %s\n",count,ps.infoString()));
                list.put(""+count,ps);
                count += 1;
        }
    }
    
    public Staff editStaff(Staff p,String attribute,String value){
        if(this.staff_repo.contains(p)){
            switch(attribute){
                case "NAME" :
                p.setName(value); break;
                case "DESIGNATION" :
                p.setDesignation(value); break;
                case "PHONE" :
                p.setPhone(value); break;
                case "ADDRESS" :
                p.setAddress(value); break;
                case "GENDER" :
                p.setGender(value); break;
                case "AGE" :
                p.setAge(value); break;
            }
        }
        return p;
    }
    
    public void displayLogs(){
        out.println(this.logger.report().toString());
    }
    
    public void storeStaffRepositoryData(List<Staff> pdb){
        //Normal file storage
        System.out.println("\n\tSAVING PERSONNEL REPOSITORY!\n");
        try (FileWriter fw = new FileWriter(staff_data_file, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
                 String data = ""; String base64Data = "";
                 for(Staff p: pdb){
                     data = p.toString();
                      byte[] encryptedData = this.crypter.encrypt(data);
                      base64Data = this.crypter.encode64(encryptedData);
                      pw.println(base64Data);
                 }
                 pw.flush(); pw.close();
                 this.staff_repo = pdb;
                 logger.logINFO("PERSONNEL DATA STORAGE OPERATION COMPLETE");
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    public List<Staff> loadStaffRepositoryData(){
        System.out.println("\n\tLOADING REPOSITORY\n");
        try (BufferedReader br = new BufferedReader(new FileReader(staff_data_file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    byte[] encryptedData = this.crypter.decode64(line);
                    String decrypted = this.crypter.decrypt(encryptedData);
                    Staff p = new Staff(decrypted);
                    this.staff_repo.add(p);
                }
            }br.close();
          logger.logINFO("LOADING PERSONNEL DATA OPERATION COMPLETE	");  
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        return this.staff_repo;
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////

}