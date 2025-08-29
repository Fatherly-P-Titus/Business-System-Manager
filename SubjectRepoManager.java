package SchoolManager;

import java.util.*;
import java.util.Scanner;
import static java.lang.System.out;

import java.io.*;

public class SubjectRepoManager {
    
    private final String subjects_repo_file = "subjects_repo_file.txt";
    
    private Crypter crypter;
    
    private Logger logger = new LoggerMech("REPO MANAGER");
    
    private List<Subject> subject_repo;
    
    private List<Subject> query_matches;
    
    private Map<String,List<String>> grade_subject_mapper;
    
    boolean subject_repo_updated;
        
    
    public SubjectRepoManager(Crypter crypter){
        this.crypter = crypter;
        
        this.subject_repo_updated = false;
                
        this.subject_repo = this.loadSubjectsDataList();
        
        this.grade_subject_mapper= new HashMap<>();
        
        this.query_matches = new ArrayList<>();
        this.loadGradeSubjects();
    }
    
    
    private List<Subject> turn(List<SchoolObject>sobj){
        List<Subject> subjects = new ArrayList<>();
        for(SchoolObject so: sobj){
            subjects.add((Subject)so);
        }
        return subjects;
    }
    
    /////////////////////////////////////////////////////////////////////////////////////
    
    public int getRepoSize(){
        return this.subject_repo.size();
    }
    
    public Map<String,List<String>> getSubjectsMapper(){
        return this.grade_subject_mapper;
    }
    
    public List<Subject> getSubjectRepo(){
    	return this.subject_repo;
    }
    
    public List<Subject> getQueryMatches(){
    	return this.query_matches;
    }
    
    public void clearQueryMatches() {
    	this.query_matches = new ArrayList<>();
    }
    
    public void storeSaveRepoUpdate(){
        logger.logINFO("SAVING REPOSITORY UPDATES ");

        out.println("\n [SAVING REPOSITORY UPDATES]");
        if(subject_repo_updated){
            out.println("\t Updating Subject Repo....");
            this.storeSubjectsDataList(this.subject_repo);
            logger.logINFO("Subject Repository Updates Stored");
            this.subject_repo_updated = false;
        }
    }
    
    //COMMANDS FOR SUBJECT REPOSITORY
    
    public void appendSubjectRepo(Subject p){
        this.subject_repo.add(p);
        this.subject_repo_updated = true;
        logger.logINFO("New Subject Added To Subject Repo");
    }
    
    public void removeFromSubjectRepo(Subject p){
        if(this.subject_repo.contains(p)){
            this.subject_repo.remove(p);
            subject_repo_updated = true;
            logger.logINFO("Subject Removed From Subject Repo");
        }
    }
    
    public List<Subject> getSubjectByAttribute(String attr, Object value){
        Subject match = null;
        
        for(Subject p: this.subject_repo){
            switch(attr){
                case "TITLE" : 
                    if(Objects.equals(p.getTitle(),value)){
                   match = p; 
                }; break;
                case "CODE" :
                    if(Objects.equals(p.getCode(),value)){
                        match = p;
                    }; break;
                case "CREDITS" :
                    if(Objects.equals(String.format("%s",p.getCredits()),value)){
                    match = p;
                }
            }
            if(match != null) {
            	this.query_matches.add(match);
            	match = null;
            }
        }
        return this.query_matches;
    }
    
    public Subject displayQueryMatches () {
    	out.println("\t [DISPLAYING QUERY RESULTS] \n");
    	
    	 Map<String,Subject> list = new HashMap<>();
         Subject p = null;
         Scanner sc = new Scanner(System.in);
         
         int count = 1;
         for(Subject ps: this.query_matches){
                 out.println(String.format("%s) %s\n",count,ps.infoString()));
                 list.put(""+count,ps);
                 count += 1;
         }
         
         out.printf("\t SELECT SUBJECT: ");
         String option = sc.next();
         out.println();
         sc = null;
         
         if(list.containsKey(option)){
             p = list.get(option);
         }
         return p;
    }
    
    public void replaceSubject(Subject p, Subject pr){
        List<Subject> p_repo = new ArrayList<>();
        out.println("\n\t [REPLACING SUBJECT]\n");
        
        for(Subject pl: this.subject_repo){
            if(pl.equals(p)){
                out.println("found Target Object\nReplacing...\n");
                p_repo.add(pr);
            }else{
                p_repo.add(pl);
            }
        } this.subject_repo = p_repo;
        out.println("REPO SIZE: "+this.subject_repo.size());
        this.subject_repo_updated = true;
        logger.logINFO(String.format("Performed Replacement Operation On Subject Repository"));

    }
    
    public List<Subject> querySubjectRepo(String attribute, String value){
        List<Subject> matches = new ArrayList<>();
        
        logger.logINFO(String.format("Querying Subject Repo: [%s = '%s']l",attribute,value));
        
        for(Subject p: this.subject_repo){
            switch(attribute){
                case "TITLE" : 
                if(Objects.equals(p.getTitle(),value)){
                    matches.add(p);
                } break;
                case "CODE" :
                if(Objects.equals(p.getCode(),value)){
                    matches.add(p);
                }break;
                case "CREDITS" :
                if(p.getCredits() == Double.parseDouble(value)){
                    matches.add(p);
                }break;
            }
        }
        if(matches.size() > 0){
            logger.logINFO(String.format("\t- Query Matches Found: %s",matches.size()));
        }
        return matches;
    }
    
    
    public List<Subject> querySubjectRepo(String value){
        List<Subject> matches = new ArrayList<>();
        
        logger.logINFO(String.format("Querying Subject Repo: ['%s']",value));
        
        for(Subject p: this.subject_repo){
            if((Objects.equals(p.getTitle(),value)) || 
                ((Objects.equals(p.getCode(),value)))){
                    matches.add(p);
                } 
        }
        logger.logINFO(String.format("/t- Query Matches: %s",matches.size()));
        return matches;
    }
    
    
    public Subject listSelectSubjectRepo(){
        Subject p = null;
        
        out.println("\n\t [SUBJECT REPOSITORY LIST] \n");
        
        Map<String,Subject> list = new HashMap<>();
        
        Scanner sc = new Scanner(System.in);
        
        int count = 1;
        for(Subject ps: this.subject_repo){
                out.println(String.format("%s) %s\n",count,ps.infoString()));
                list.put(""+count,ps);
                count += 1;
        }
        out.printf("\t SELECT SUBJECT: ");
        String option = sc.next();
        out.println();
        sc = null;
        if(list.containsKey(option)){
            p = list.get(option);
        }
        return p;
    }
    
    public void listRecords(){
        out.println("\n\t [SUBJECT REPOSITORY LIST] \n");
        
        out.println("REPO SIZE: "+this.subject_repo.size());
        
        Map<String,Subject> list = new HashMap<>();
        
        int count = 1;
        for(Subject ps: this.subject_repo){
                out.println(String.format("%s) %s\n",count,ps.infoString()));
                list.put(""+count,ps);
                count += 1;
        }
    }
    
    public Subject editSubject(Subject p,String attribute,String value){
        if(this.subject_repo.contains(p)){
            switch(attribute){
                case "TITLE" : 
                p.setTitle(value); break;
                case "CODE" :
                p.setCode(value); break;
                case "CREDITS" :
                p.setCredits(Double.parseDouble(value)); break;
            }
        }
        return p;
    }
    
    public void displayLogs(){
        out.println(this.logger.report().toString());
    }
    
    
    
    private void loadGradeSubjects(){
        Map<String,List<String>> subjects = new HashMap<>();
        
        try(BufferedReader br = new BufferedReader(new FileReader(this.subjects_repo_file))){
            String line=""; String[]dta;
            while((line = br.readLine())!=null){
                if(!line.trim().isEmpty()){
                    dta = line.split(",");
                    String grade = dta[0];
                    List<String>subs = new ArrayList<>();
                    
                    for(int i=1; i< dta.length; i++){
                        subs.add(dta[i]);
                    }dta = null;
                    
                    subjects.put(grade,subs);
                }
            } this.grade_subject_mapper = subjects;
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        
    }
    
     ///FOR SUBJECT DATA REPOSITORY
    public void storeSubjectsDataList(List<Subject> obj_list){
        //ENCRYPT, ENCODE, PERSIST
        try(FileWriter fw = new FileWriter(subjects_repo_file,false);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);){ 
            String data = ""; String encoded_data = "";
            for(Subject obj: obj_list){
                data = obj.toString();
                byte[] encrypted_data = crypter.encrypt(data);
                encoded_data = crypter.encode64(encrypted_data);
                pw.println(encoded_data);
            }pw.flush(); pw.close();
            this.subject_repo = obj_list;
            out.println("\t DATA LIST STORED TO FILE! \n");
        }catch(IOException ioe){ioe.printStackTrace();}
    }
    public List<Subject> loadSubjectsDataList(){
        List<Subject> data_list = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(subjects_repo_file));
            String line = "";
            while((line = br.readLine()) != null){
                if(!line.trim().isEmpty()){
                    byte[] encryptedData = crypter.decode64(line);
                    String decrypted = crypter.decrypt(encryptedData);
                    Subject so = new Subject(decrypted);
                    data_list.add(so);
                }
            }br.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        } return data_list;
    }
    public void storeToSubjectsDataList(Subject obj){
        //ENCRYPT, ENCODE, PERSIST
        try(FileWriter fw = new FileWriter(subjects_repo_file,false);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);){ 
            String data = ""; String encoded_data = "";
            
                data = obj.toString();
                byte[] encrypted_data = crypter.encrypt(data);
                encoded_data = crypter.encode64(encrypted_data);
                pw.println(encoded_data);
            pw.flush(); pw.close();
            out.println("\t DATA LIST STORED TO FILE! \n");
        }catch(IOException ioe){ioe.printStackTrace();}
    }
    ///////////////////////////////////////////////////////////////////////////////////////
    
     
}