package SchoolManager;

import java.util.*;
import java.util.Scanner;
import static java.lang.System.out;

import java.io.*;



public class StudentFeeRepoManager {
    
    private final String students_fee_file = "student_fee_repo.txt";
    private final String school_grade_fee_file = "school_grade_fees.txt";
    
    private Crypter crypter;
    
    private Logger logger = new LoggerMech("REPO MANAGER");
    
    private List<StudentFee> student_fee_repo;
    
    private List<StudentFee> query_matches;
    
    private Map<String,Double> grade_fee_mapper;
    
    private boolean student_fee_repo_updated;
    
    
    public StudentFeeRepoManager(Crypter crypter){
        this.student_fee_repo_updated = false;
        this.crypter = crypter;
        this.student_fee_repo = this.loadStudentsDataList();
        this.query_matches = new ArrayList<>();
        this.grade_fee_mapper = new HashMap<>();
        this.loadGradeFees();
    }
    
    /////////////////////////////////////////////////////////////////////////////////////
    
    public int getRepoSize(){
        return this.student_fee_repo.size();
    }
    
    public Map<String,Double> getFeesMapper(){
        return this.grade_fee_mapper;
    }
    
    public List<StudentFee> getStudentFeeRepo(){
    	return this.student_fee_repo;
    }
    
    public List<Log> getLogsRepo(){
    	return this.logger.getAllLogs();
    }
    
    public List<StudentFee> getQueryMatches(){
    	return this.query_matches;
    }
    
    public void clearQueryMatches() {
    	this.query_matches = new ArrayList<>();
    }
    
    public void storeSaveRepoUpdate(){
        logger.logINFO("SAVING REPOSITORY UPDATES ");

        out.println("\n [SAVING REPOSITORY UPDATES]");
        if(student_fee_repo_updated){
            out.println("\t Updating Student Repo....");
            this.storeStudentsDataList(this.student_fee_repo);
            logger.logINFO("Student Repository Updates Stored");
            this.student_fee_repo_updated = false;
        }
    }
    
    private List<StudentFee> turn(List<SchoolObject>sobj){
        List<StudentFee> students = new ArrayList<>();
        for(SchoolObject so: sobj){
            students.add((StudentFee)so);
        }return students;
    }
    
    //COMMANDS FOR STUDENT REPOSITORY
    public void createNewStudentFeeEntry(Student student,double initial){
        StudentFee sf = new StudentFee();
        
        sf.setStudent(student);
        
        String grd = student.getGrade();
        double fees = this.grade_fee_mapper.get(grd);
        
        sf.setTotalFee(fees);
        sf.setPaidFee(initial);
        
        this.student_fee_repo.add(sf);
        this.student_fee_repo_updated = true;
        
        logger.logINFO("New Student Fee Entry Added To Repo");

    }
    
    
    public void appendStudentFeeRepo(StudentFee p){
        this.student_fee_repo.add(p);
        this.student_fee_repo_updated = true;
        logger.logINFO("New Student Added To Student Repo");
    }
    
    public void removeFromStudentFeeRepo(Student p){
        if(this.student_fee_repo.contains(p)){
            this.student_fee_repo.remove(p);
            student_fee_repo_updated = true;
            logger.logINFO("Student Removed From Student Repo");
        }
    }
    
    public List<StudentFee> getStudentFeeByAttribute(String attr, Object value){
        StudentFee match = null;
        
        for(StudentFee p: this.student_fee_repo){
            switch(attr){
                case "ID" : 
                if(Objects.equals(p.getStudentID(),value)){
                   match = p; 
                }; break;
                case "FEES-TOTAL" :
                    if(p.getFeeTotal() == (Double)(value)){
                        match = p;
                    }; break;
                case "FEES-PAID" :
                    if(p.getFeePaid() == (Double)(value)){
                        match = p;
                    }; break;
                
            }
            if(match != null) {
            	this.query_matches.add(match);
            	match = null;
            }
        }
        return this.query_matches;
    }
    
    public StudentFee displayQueryMatches () {
    	out.println("\t [DISPLAYING QUERY RESULTS] \n");
    	
    	 Map<String,StudentFee> list = new HashMap<>();
         StudentFee p = null;
         Scanner sc = new Scanner(System.in);
         
         int count = 1;
         for(StudentFee ps: this.query_matches){
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
    
    public void replaceStudentFee(StudentFee p, StudentFee pr){
        List<StudentFee> p_repo = new ArrayList<>();
        out.println("\n\t [REPLACING STUDENT]\n");
        out.println("REPO SIZE: "+this.student_fee_repo.size());

        for(StudentFee pl: this.student_fee_repo){
            if(pl.equals(p)){
                out.println("found Target Object\nReplacing...\n");
                p_repo.add(pr);
            }else{
                p_repo.add(pl);
            }
        } this.student_fee_repo = p_repo;
        out.println("REPO SIZE: "+this.student_fee_repo.size());
        this.student_fee_repo_updated = true;
        logger.logINFO(String.format("Performed Replacement Operation On Student Repository"));
    }
    
    public List<StudentFee> queryStudentFeeRepo(String attribute, String value){
        List<StudentFee> matches = new ArrayList<>();
        
        logger.logINFO(String.format("Querying Student Repo: [%s = '%s']l",attribute,value));
        StudentFee match = null;
        
        for(StudentFee p: this.student_fee_repo){
            switch(attribute){
                case "NAME" : 
                if(p.getStudent().equals(value)){
                   match = p; 
                }; break;
                case "FEES-TOTAL" :
                    if(p.getFeeTotal() == Double.parseDouble(value)){
                        match = p;
                    }; 
                break;
                case "FEES-PAID" :
                    if(p.getFeePaid() == Double.parseDouble(value)){
                        match = p;
                    };break;
                case "FEES-OWED" :
                    if(p.getFeeOwed() == Double.parseDouble(value)){
                        match = p;
                    }; 
                break;
            } if(match != null){
                matches.add(p);
            }
        }
        if(matches.size() > 0){
            logger.logINFO(String.format("\t- Query Matches Found: %s",matches.size()));
        }
        return matches;
    }
    
    
    public List<StudentFee> queryStudentFeeRepo(String value){
        List<StudentFee> matches = new ArrayList<>();
        
        logger.logINFO(String.format("Querying Student Repo: ['%s']",value));
        
        for(StudentFee p: this.student_fee_repo){
            if((p.getStudent() == value) || 
                (p.getFeeTotal() == Double.parseDouble(value)) ||
                (p.getFeePaid() == Double.parseDouble(value))){
                    matches.add(p);
                } 
        }
        logger.logINFO(String.format("/t- Query Matches: %s",matches.size()));
        return matches;
    }
    
    
    public StudentFee listSelectStudentFeeRepo(){
        StudentFee p = null;
        
        out.println("\n\t [STUDENT REPOSITORY LIST] \n");
        
        Map<String,StudentFee> list = new HashMap<>();
        
        Scanner sc = new Scanner(System.in);
        
        int count = 1;
        for(StudentFee ps: this.student_fee_repo){
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
        
        out.println("REPO SIZE: "+this.student_fee_repo.size());
        
        Map<String,StudentFee> list = new HashMap<>();
        
        int count = 1;
        for(StudentFee ps: this.student_fee_repo){
                out.println(String.format("%s) %s\n",count,ps.infoString()));
                list.put(""+count,ps);
                count += 1;
        }
    }
    
    public StudentFee editStudentFee(StudentFee ps,String attribute,String value){
        for(StudentFee p: this.student_fee_repo){
            if(p.equals(ps)){
            switch(attribute){
                case "STUDENT" :
                p.setStudent(value); break;
                case "FEES-TOTAL" :
                p.setTotalFee(Double.parseDouble(value)); break;
                case "FEES-PAID" :
                p.setPaidFee(Double.parseDouble(value)); break;
            }
                ps = p;
                student_fee_repo_updated = true;
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
    
    
    private void loadGradeFees(){
        Map<String,Double> fees = new HashMap<>();
        
        try(BufferedReader br = new BufferedReader(new FileReader(this.school_grade_fee_file))){
            String line=""; String[]dta;
            while((line = br.readLine())!=null){
                if(!line.trim().isEmpty()){
                    dta = line.split(",");
                    String grade = dta[0];
                    double amnt = Double.parseDouble(dta[1]);
                    fees.put(grade,amnt);
                }
            } this.grade_fee_mapper = fees;
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        
    }
     
    ///FOR STUDENT DATA REPOSITORY
    public void storeStudentsDataList(List<StudentFee> obj_list){
        //ENCRYPT, ENCODE, PERSIST
        try(FileWriter fw = new FileWriter(students_fee_file,false);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);){ 
            String data = ""; String encoded_data = "";
            for(StudentFee obj: obj_list){
                data = obj.toString();
                byte[] encrypted_data = crypter.encrypt(data);
                encoded_data = crypter.encode64(encrypted_data);
                pw.println(encoded_data);
            }pw.flush(); pw.close();
            this.student_fee_repo = obj_list;
            out.println("\t DATA LIST STORED TO FILE! \n");
        }catch(IOException ioe){ioe.printStackTrace();}
    }
    public List<StudentFee> loadStudentsDataList(){
        List<StudentFee> data_list = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(students_fee_file));
            String line = "";
            while((line = br.readLine()) != null){
                if(!line.trim().isEmpty()){
                    byte[] encryptedData = crypter.decode64(line);
                    String decrypted = crypter.decrypt(encryptedData);
                    StudentFee so = new StudentFee(decrypted);
                    data_list.add(so);
                }
            }br.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        } return data_list;
    }
    ///////////////////////////////////////////////////////////////////////////////////////
    
}