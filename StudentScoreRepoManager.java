package SchoolManager;

import java.util.*;
import java.util.Scanner;
import static java.lang.System.out;

import java.io.*;


public class StudentScoreRepoManager {
    
    public String students_scores_file = "student_course_scores.txt";
    
    private Crypter crypter;
    
    private Logger logger = new LoggerMech("REPO MANAGER");
    
    private List<StudentScore> student_score_repo;
    
    private List<StudentScore> query_matches;
    
    
    boolean student_score_repo_updated;
    boolean subject_repo_updated;
    
    public StudentScoreRepoManager(Crypter crypter){  
        this.student_score_repo_updated = false;
        this.subject_repo_updated = false;
        
        this.student_score_repo = this.loadStudentScoresDataList();//systemfileops
        
        this.query_matches = new ArrayList<>();
    }
    
    /////////////////////////////////////////////////////////////////////////////////////
    
    public int getRepoSize(){
        return this.student_score_repo.size();
    }
    
    public List<StudentScore> getStudentScoreRepo(){
    	return this.student_score_repo;
    }
    
    public List<StudentScore> getQueryMatches(){
    	return this.query_matches;
    }
    
    public void clearQueryMatches() {
    	this.query_matches = new ArrayList<>();
    }
    
    public void storeSaveRepoUpdate(){
        logger.logINFO("SAVING REPOSITORY UPDATES ");

        out.println("\n [SAVING REPOSITORY UPDATES]");
        if(student_score_repo_updated){
            out.println("\t Updating StudentScore Repo....");
            this.storeStudentScoresDataList(this.student_score_repo);
            logger.logINFO("StudentScore Repository Updates Stored");
            this.student_score_repo_updated = false;
        }
    }
    
    private List<StudentScore> turn(List<SchoolObject>sobj){
        List<StudentScore> student_scores = new ArrayList<>();
        for(SchoolObject so: sobj){
            student_scores.add((StudentScore)so);
        }return student_scores;
    }
    
    //COMMANDS FOR STUDENTS SCORE REPOSITORY
    
    public void createNewStudentScoresEntry(Student student,Map<String,List<String>>subjects){
        StudentScore sf = new StudentScore();
        
        sf.setStudent(student);
        
        String grd = student.getGrade();
        List<String> subs = subjects.get(grd);
        
        for(String s: subs){
            //ID|course|Score	
            sf.setSubjectCode(s);
            sf.setScore(0.0);
            
            this.student_score_repo.add(sf);
        }
        
        this.student_score_repo_updated = true;
        logger.logINFO("New Student Fee Entry Added To Repo");

    }
    
    public void appendStudentScoreRepo(StudentScore p){
        this.student_score_repo.add(p);
        this.student_score_repo_updated = true;
        logger.logINFO("New StudentScore Added To StudentScore Repo");
    }
    
    public void removeFromStudentScoreRepo(StudentScore p){
        if(this.student_score_repo.contains(p)){
            this.student_score_repo.remove(p);
            student_score_repo_updated = true;
            logger.logINFO("StudentScore Removed From StudentScore Repo");

        }
    }
    
    public List<StudentScore> getStudentScoreByAttribute(String attr, Object value){
        StudentScore match = null;
        
        for(StudentScore p: this.student_score_repo){
            switch(attr){
                case "NAME" : 
                if(Objects.equals(p.getStudentName(), value)){
                   match = p; 
                }; break;
                case "ID" :
                    if(Objects.equals(p.getStudentID(),value)){
                        match = p;
                    }; break;
                case "GRADE" :
                    if(Objects.equals(p.getStudentGrade(),value)){
                        match = p;
                    }; break;
                case "SUBJECT" :
                    if(Objects.equals(p.getSubject(),value)){
                        match = p;
                    }; break;
                case "CODE" :
                    if(Objects.equals(p.getSubjectCode(),value)){
                        match = p;
                    }; break;
                case "SCORE" :
                if(p.getScore() == Double.parseDouble(value.toString())){
                    match = p;
                }; break;
            }
            if(match != null) {
            	this.query_matches.add(match);
            	match = null;
            }
        }return this.query_matches;
    }
    
    public StudentScore displayQueryMatches () {
    	out.println("\t [DISPLAYING QUERY RESULTS] \n");
    	
    	 Map<String,StudentScore> list = new HashMap<>();
         StudentScore p = null;
         Scanner sc = new Scanner(System.in);
         
         int count = 1;
         for(StudentScore ps: this.query_matches){
                 out.println(String.format("%s) %s\n",count,ps.infoString()));
                 list.put(""+count,ps);
                 count += 1;
         }
         
         out.printf("\t SELECT STUDENTS SCORE: ");
         String option = sc.next();
         out.println();
         sc = null;
         
         if(list.containsKey(option)){
             p = list.get(option);
         }
         return p;
    }
    
    public void replaceStudentScore(StudentScore p, StudentScore pr){
        List<StudentScore> p_repo = new ArrayList<>();
        out.println("\n\t [REPLACING STUDENTS SCORE]\n");
        out.println("REPO SIZE: "+this.student_score_repo.size());

        for(StudentScore pl: this.student_score_repo){
            if(pl.equals(p)){
                out.println("found Target Object\nReplacing...\n");
                p_repo.add(pr);
            }else{
                p_repo.add(pl);
            }
        } this.student_score_repo = p_repo;
        out.println("REPO SIZE: "+this.student_score_repo.size());
        this.student_score_repo_updated = true;
        logger.logINFO(String.format("Performed Replacement Operation On StudentScore Repository"));
    }
    
    public List<StudentScore> queryStudentScoreRepo(String attribute, String value){
        List<StudentScore> matches = new ArrayList<>();
        
        logger.logINFO(String.format("Querying StudentScore Repo: [%s = '%s']l",attribute,value));
        StudentScore match = null;
        
        for(StudentScore p: this.student_score_repo){
            switch(attribute){
                case "NAME" : 
                    if(Objects.equals(p.getStudentName(),value)){
                   match = p; 
                }; break;
                case "SUBJECT" :
                    if(Objects.equals(p.getSubject(),value)){
                        match = p;
                    }; break;
                case "CODE" :
                    if(Objects.equals(p.getSubjectCode(),value)){
                        match = p;
                    }; break;
                case "GRADE" :
                    if(Objects.equals(p.getStudentGrade(),value)){
                        match = p;
                    }; break;
                case "SCORE" :
                if(p.getScore() == Double.parseDouble(value.toString())){
                    match = p;
                }; break;
                case "ID" :
                if(Objects.equals(p.getStudentID(),value)){
                    match = p;
                }
            } if(match != null){
                matches.add(p);
            }
        }
        if(matches.size() > 0){
            logger.logINFO(String.format("\t- Query Matches Found: %s",matches.size()));
        }
        return matches;
    }
    
    
    public List<StudentScore> queryStudentScoreRepo(String value){
        List<StudentScore> matches = new ArrayList<>();
        
        logger.logINFO(String.format("Querying StudentScore Repo: ['%s']",value));
        
        for(StudentScore p: this.student_score_repo){
            if((Objects.equals(p.getStudentName(),value)) || 
                (Objects.equals(p.getStudentGrade(),value)) ||
                (Objects.equals(p.getSubject(),value)) ||
                (Objects.equals(p.getSubjectCode(),value)) ||
                (p.getScore() == Double.parseDouble(value))){
                    matches.add(p);
                } 
        }
        logger.logINFO(String.format("/t- Query Matches: %s",matches.size()));
        return matches;
    }
    
    
    public StudentScore listSelectStudentScoreRepo(){
        StudentScore p = null;
        
        out.println("\n\t [STUDENTS SCORE REPOSITORY LIST] \n");
        
        Map<String,StudentScore> list = new HashMap<>();
        
        Scanner sc = new Scanner(System.in);
        
        int count = 1;
        for(StudentScore ps: this.student_score_repo){
                out.println(String.format("%s) %s\n",count,ps.infoString()));
                list.put(""+count,ps);
                count += 1;
        }
        out.printf("\t SELECT STUDENTS SCORE: ");
        String option = sc.next();
        out.println();
        sc = null;
        if(list.containsKey(option)){
            p = list.get(option);
        }
        return p;
    }
    
    public void listRecords(){
        out.println("\n\t [STUDENTS SCORE REPOSITORY LIST] \n");
        
        out.println("REPO SIZE: "+this.student_score_repo.size());
        
        Map<String,StudentScore> list = new HashMap<>();
        
        int count = 1;
        for(StudentScore ps: this.student_score_repo){
                out.println(String.format("%s) %s\n",count,ps.infoString()));
                list.put(""+count,ps);
                count += 1;
        }
    }
    
    public StudentScore editStudentScore(StudentScore p,String attribute,String value){
        if(this.student_score_repo.contains(p)){
            switch(attribute){
                case "NAME" :
                p.setStudentName(value); break;
                case "GRADE" :
                p.setStudentGrade(value); break;
                case "SUBJECT" :
                p.setSubject(value); break;
                case "CODE" :
                p.setSubjectCode(value); break;
                case "ID" :
                p.setStudentID(value); break;
                case "SCORE" :
                p.setScore(Double.parseDouble(value)); break;
            }
        } return p;
    }
    
    public void displayLogs(){
        out.println(this.logger.report().toString());
    }
    
    
      ///FOR STUDENT DATA REPOSITORY
    public void storeStudentScoresDataList(List<StudentScore> obj_list){
        //ENCRYPT, ENCODE, PERSIST
        try(FileWriter fw = new FileWriter(students_scores_file,false);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);){ 
            String data = ""; String encoded_data = "";
            for(StudentScore obj: obj_list){
                data = obj.toString();
                byte[] encrypted_data = crypter.encrypt(data);
                encoded_data = crypter.encode64(encrypted_data);
                pw.println(encoded_data);
            }pw.flush(); pw.close();
            this.student_score_repo = obj_list;
            out.println("\t DATA LIST STORED TO FILE! \n");
        }catch(IOException ioe){ioe.printStackTrace();}
    }
    public List<StudentScore> loadStudentScoresDataList(){
        List<StudentScore> data_list = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(students_scores_file));
            String line = "";
            while((line = br.readLine()) != null){
                if(!line.trim().isEmpty()){
                    byte[] encryptedData = crypter.decode64(line);
                    String decrypted = crypter.decrypt(encryptedData);
                    StudentScore so = new StudentScore(decrypted);
                    data_list.add(so);
                }
            }br.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        } return data_list;
    }
    ///////////////////////////////////////////////////////////////////////////////////////

     
}