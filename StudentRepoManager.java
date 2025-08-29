package SchoolManager;

import java.util.*;
import java.util.Scanner;
import static java.lang.System.out;

import java.io.*;



public class StudentRepoManager {
    
    private final String students_repo_file = "student_repo.txt";
    
    private Crypter crypter;
    
    private Logger logger = new LoggerMech("REPO MANAGER");
    
    private List<Student> student_repo;
    
    private List<Student> query_matches;
    
    private boolean student_repo_updated;
    
    
    public StudentRepoManager(Crypter crypter){
        this.student_repo_updated = false;
        this.crypter = crypter;
        this.student_repo = this.loadStudentsDataList();
        this.query_matches = new ArrayList<>();
    }
    
    /////////////////////////////////////////////////////////////////////////////////////
    
    public int getRepoSize(){
        return this.student_repo.size();
    }
    
    public List<Student> getStudentRepo(){
    	return this.student_repo;
    }
    
    public List<Log> getLogsRepo(){
    	return this.logger.getAllLogs();
    }
    
    public List<Student> getQueryMatches(){
    	return this.query_matches;
    }
    
    public void clearQueryMatches() {
    	this.query_matches = new ArrayList<>();
    }
    
    public void storeSaveRepoUpdate(){
        logger.logINFO("SAVING REPOSITORY UPDATES ");

        out.println("\n [SAVING REPOSITORY UPDATES]");
        if(student_repo_updated){
            out.println("\t Updating Student Repo....");
            this.storeStudentsDataList(this.student_repo);
            logger.logINFO("Student Repository Updates Stored");
            this.student_repo_updated = false;
        }
    }
    
    private List<Student> turn(List<SchoolObject>sobj){
        List<Student> students = new ArrayList<>();
        for(SchoolObject so: sobj){
            students.add((Student)so);
        }return students;
    }
    
    //COMMANDS FOR STUDENT REPOSITORY
    
    public void appendStudentRepo(Student p){
        this.student_repo.add(p);
        this.student_repo_updated = true;
        logger.logINFO("New Student Added To Student Repo");
    }
    
    public void removeFromStudentRepo(Student p){
        if(this.student_repo.contains(p)){
            this.student_repo.remove(p);
            student_repo_updated = true;
            logger.logINFO("Student Removed From Student Repo");
        }
    }
    
    public List<Student> getStudentByAttribute(String attr, Object value){
        Student match = null;
        
        for(Student p: this.student_repo){
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
                case "GRADE" :
                    if(Objects.equals(p.getGrade(),value)){
                        match = p;
                    }; break;
                case "ADDRESS" :
                    if(Objects.equals(p.getAddress(),value)){
                        match = p;
                    }; break;
                case "CGPA" :
                if(Objects.equals(p.getCGPA() == Double.parseDouble(value.toString())){
                    match = p;
                }; break;
                case "ID" :
                if(Objects.equals(p.getID(),value)){
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
    
    public Student displayQueryMatches () {
    	out.println("\t [DISPLAYING QUERY RESULTS] \n");
    	
    	 Map<String,Student> list = new HashMap<>();
         Student p = null;
         Scanner sc = new Scanner(System.in);
         
         int count = 1;
         for(Student ps: this.query_matches){
                 out.println(String.format("%s) %s\n",count,ps.infoString()));
                 list.put(""+count,ps);
                 count += 1;
         }
         out.printf("\t SELECT STUDENT: ");
         String option = sc.next();
         out.println();
         sc = null;
         
         if(list.containsKey(option)){
             p = list.get(option);
         }
         return p;
    }
    
    public void replaceStudent(Student p, Student pr){
        List<Student> p_repo = new ArrayList<>();
        out.println("\n\t [REPLACING STUDENT]\n");
        out.println("REPO SIZE: "+this.student_repo.size());

        for(Student pl: this.student_repo){
            if(pl.equals(p)){
                out.println("found Target Object\nReplacing...\n");
                p_repo.add(pr);
            }else{
                p_repo.add(pl);
            }
        } this.student_repo = p_repo;
        out.println("REPO SIZE: "+this.student_repo.size());
        this.student_repo_updated = true;
        logger.logINFO(String.format("Performed Replacement Operation On Student Repository"));
    }
    
    public List<Student> queryStudentRepo(String attribute, String value){
        List<Student> matches = new ArrayList<>();
        
        logger.logINFO(String.format("Querying Student Repo: [%s = '%s']l",attribute,value));
        Student match = null;
        
        for(Student p: this.student_repo){
            switch(attribute){
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
                case "GRADE" :
                    if(Objects.equals(p.getGrade(),value)){
                        match = p;
                    }; break;
                case "ADDRESS" :
                    if(Objects.equals(p.getAddress(),value)){
                        match = p;
                    }; break;
                case "CGPA" :
                if(Objects.equals(p.getCGPA() == Double.parseDouble(value.toString())){
                    match = p;
                }; break;
                case "ID" :
                if(Objects.equals(p.getID(),value)){
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
    
    
    public List<Student> queryStudentRepo(String value){
        List<Student> matches = new ArrayList<>();
        
        logger.logINFO(String.format("Querying Student Repo: ['%s']",value));
        
        for(Student p: this.student_repo){
            if((Objects.equals(p.getName(),value)) || 
                (Objects.equals(p.getGrade(),value)) ||
                (Objects.equals(p.getPhone(),value)) ||
                (Objects.equals(p.getAddress(),value)) ||
                (Objects.equals(p.getGender(),value)) ||
                (Objects.equals(p.getAge(),value)) ||
                (Objects.equals(p.getCGPA(),value))){
                    matches.add(p);
                } 
        }
        logger.logINFO(String.format("/t- Query Matches: %s",matches.size()));
        return matches;
    }
    
    
    public Student listSelectStudentRepo(){
        Student p = null;
        
        out.println("\n\t [STUDENT REPOSITORY LIST] \n");
        
        Map<String,Student> list = new HashMap<>();
        
        Scanner sc = new Scanner(System.in);
        
        int count = 1;
        for(Student ps: this.student_repo){
                out.println(String.format("%s) %s\n",count,ps.infoString()));
                list.put(""+count,ps);
                count += 1;
        }
        out.printf("\t SELECT STUDENT: ");
        String option = sc.next();
        out.println();
        sc = null;
        if(list.containsKey(option)){
            p = list.get(option);
        }
        return p;
    }
    
    public void listRecords(){
        out.println("\n\t [STUDENT REPOSITORY LIST] \n");
        
        out.println("REPO SIZE: "+this.student_repo.size());
        
        Map<String,Student> list = new HashMap<>();
        
        int count = 1;
        for(Student ps: this.student_repo){
                out.println(String.format("%s) %s\n",count,ps.infoString()));
                list.put(""+count,ps);
                count += 1;
        }
    }
    
    public Student editStudent(Student ps,String attribute,String value){
        for(Student p: this.student_repo){
            if(p.equals(ps)){
            switch(attribute){
                case "name" :
                p.setName(value); break;
                case "grade" :
                p.setGrade(value); break;
                case "phone" :
                p.setPhone(value); break;
                case "address" :
                p.setAddress(value); break;
                case "gender" :
                p.setGender(value); break;
                case "age" :
                p.setAge(value); break;
                case "cgpa" :
                p.setCGPA(Double.parseDouble(value)); break;
            }
                ps = p;
                student_repo_updated = true;
            }
        } return ps;
    }
    
    
    public String generateStudentAuth(){
        String auth = "";
        
        try{
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
        }catch(Exception e){
            e.printStackTrace();
        }
        logger.logINFO(String.format("Generated Student Auth: %s",auth));
        return auth;
    }
    
    
    public void displayLogs(){
        out.println(this.logger.report().toString());
    }
     
    ///FOR STUDENT DATA REPOSITORY
    public void storeStudentsDataList(List<Student> obj_list){
        //ENCRYPT, ENCODE, PERSIST
        try(FileWriter fw = new FileWriter(students_repo_file,false);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);){ 
            String data = ""; String encoded_data = "";
            for(Student obj: obj_list){
                data = obj.toString();
                //byte[] encrypted_data = crypter.encrypt(data);
                encoded_data = crypter.encryptEncode(data);
                pw.println(encoded_data);
            }pw.flush(); pw.close(); 
            this.student_repo = obj_list;
            out.println("\t DATA LIST STORED TO FILE! \n");
        }catch(IOException ioe){ioe.printStackTrace();}
    }
    public List<Student> loadStudentsDataList(){
        List<Student> data_list = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(students_repo_file));
            String line = "";
            while((line = br.readLine()) != null){
                if(!line.trim().isEmpty()){
                    //byte[] encryptedData = crypter.decode64(line);
                    String decrypted = crypter.decodeDecrypt(line);
                    Student so = new Student(decrypted);
                    data_list.add(so);
                }
            }br.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        } return data_list;
    }
    ///////////////////////////////////////////////////////////////////////////////////////
    
}