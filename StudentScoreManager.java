package SchoolManager;



import static java.lang.System.out;

import java.util.Scanner;
import java.util.*;

public class StudentScoreManager{
    
    StudentScoreRepoManager rpm;
    
    Student student;
    
    Scanner sc;
    
    public StudentScoreManager( StudentScoreRepoManager rpm){
        this.rpm = rpm;
        sc = new Scanner(System.in);
        this.student = null;
        //this.sys_file_ops = sfo;//new SystemFileMech(new CrypterSymmetricMech());
    }
    
    
    public void setStudent(Student std){
        this.student = std;
    }
    
    
    public StudentScore createStudentScore(){
        StudentScore p = null;
        
        out.printf("STUDENT NAME: ");
        String p_name = sc.nextLine();
        
        out.println();
        out.printf("STUDENT ID: ");
        String p_id = sc.nextLine();
        
        out.println();
        out.printf("STUDENT GRADE: ");
        String p_grade = sc.nextLine();
        
        out.println();
        
        out.printf("SUBJECT: ");
        String p_subject = sc.nextLine();
        
        out.println();
        out.printf("SUBJECT CODE: ");
        String p_code = sc.nextLine();
        
        out.println();
        out.printf("STUDENT SCORE: ");
        double p_score = sc.nextDouble();
        
        out.println("\n\t CREATED NEW STUDENT");
         
        p = new StudentScore(p_name,p_id,p_grade,p_subject,p_code,p_score);
        
        rpm.appendStudentScoreRepo(p);
        
        return p;
    }
    
    public void deleteStudentScore(StudentScore p){
        out.println(String.format("\t SURE TO REMOVE STUDENT ?\n[%s]",p.infoString()));
        String option = sc.next();
        if((option == "YES") || (option == "yes") || (option == "y") || (option == "Y")){
            rpm.removeFromStudentScoreRepo(p);
        }
    }
    
    
    public StudentScore searchStudentScore(){
        
        //List<StudentScore>query_list = new ArrayList<>();
        
        out.print("\n1) BY NAME \n2) BY GRADE \n3) BY SUBJECT \n4) BY SUBJECT CODE \n5) BY ID \n6) BY SCORE \n\t => ");
        
        String e_option = sc.nextLine();
        String nw_val = "";
        
        StudentScore p = null;//new StudentScore(); 
        String attr = "";
        
        out.println("\t\t [QUERY OPERATION] \n\n");
        
        switch(e_option){
         case "1" :
            out.printf("PROVIDE NAME: ");
            nw_val = sc.nextLine();
            out.println();
            attr = "NAME"; break;
         case "2" :
            out.printf("PROVIDE GRADE: ");
            nw_val = sc.nextLine();
            out.println();
            attr = "GRADE";
            break;
         case "3" :
            out.printf("PROVIDE SUBJECT TITLE: ");
            nw_val = sc.nextLine();
            out.println();
            attr = "SUBJECT";
            break;   
         case "4" :
            out.printf("PROVIDE SUBJECT CODE: ");
            nw_val = sc.nextLine();
            out.println();
            attr = "CODE";
            break;
         case "5" :
            out.printf("PROVIDE ID: ");
            nw_val = sc.nextLine();
            out.println();
            attr = "ID";
            break;
         case "6" :
             out.printf("PROVIDE SCORE: ");
             nw_val = sc.nextLine();
             out.println();
             attr = "SCORE";
             break;
        }
        rpm.getStudentScoreByAttribute(attr,nw_val); 
        
        if(rpm.getQueryMatches().size() > 0) {
        	p = rpm.displayQueryMatches();
        	rpm.clearQueryMatches();
        }
        return p;
    }
    
    
    public StudentScore editStudentScore(StudentScore po){
        out.println("\n1) EDIT NAME \n2) EDIT GRADE \n3) EDIT SUBJECT \n4) EDIT CODE \n5) EDIT ID \n6) EDIT SCORE \n\t => ");
        
        String e_option = sc.nextLine();
        String nw_val = "";
        
        StudentScore p = po;//new StudentScore(); 
        
        out.println("\t [EDITING OPERATION] \n\n");
        
        out.printf("INITIAL STUDENT DATA: \n%s\n\n",po.infoString());
        
        switch(e_option){
         case "1" :
            out.printf("PROVIDE NAME: ");
            nw_val = sc.nextLine();
            out.println();
            p.setStudentName(nw_val); break;
         case "2" :
            out.printf("PROVIDE GRADE: ");
            nw_val = sc.nextLine();
            out.println();
            p.setStudentGrade(nw_val); break;
         case "3" :
            out.printf("PROVIDE SUBJECT: ");
            nw_val = sc.nextLine();
            out.println();
            p.setSubject(nw_val); break;   
         case "4" :
            out.printf("PROVIDE SUBJECT CODE: ");
            nw_val = sc.nextLine();
            out.println();
            p.setSubjectCode(nw_val); break;
         case "5" :
            out.printf("PROVIDE ID: ");
            nw_val = sc.nextLine();
            out.println();
            p.setStudentID(nw_val); break;
         case "6" :
            out.printf("PROVIDE SCORE: ");
            nw_val = sc.nextLine();
            out.println();
            p.setScore(Double.parseDouble(nw_val)); break;
        }
        
        out.printf("MODIFIED STUDENT DATA: \n%s\n\n",p.toString());
        rpm.replaceStudentScore(po,p);
        return p;
    }
}

