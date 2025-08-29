package SchoolManager;



import static java.lang.System.out;

import java.util.Scanner;
import java.util.*;

public class StudentManager{
    
    StudentRepoManager rpm;
    
    Scanner sc;
    SystemFileOps sys_file_ops;

    List<Student> repo;
    
    public StudentManager(StudentRepoManager rpm){
        this.rpm = rpm;
        sc = new Scanner(System.in);
    }
    
    
    public Student createStudent(){
        Student p = null;
        
        out.printf("STUDENT NAME: ");
        String p_name = sc.nextLine();
        
        out.println();
        out.printf("STUDENT GENDER: ");
        String p_gender = sc.nextLine();
        
        out.println();
        out.printf("STUDENT AGE: ");
        String p_age = sc.nextLine();
        
        out.println();
        
        out.printf("STUDENT GRADE: ");
        String p_grade = sc.nextLine();
        
        out.println();
        out.printf("STUDENT PHONE: ");
        String p_phone = sc.nextLine();
        
        out.println();
        out.printf("STUDENT ADDRESS: ");
        String p_address = sc.nextLine();
        
        out.println("\n\t CREATED NEW STUDENT");
         
        p = new Student(p_name,p_gender,p_age,p_grade,p_phone,p_address);
        
        rpm.appendStudentRepo(p);
        
        return p;
    }
    
    public void deleteStudent(Student p){
        out.println(String.format("\t SURE TO REMOVE STUDENT ?\n[%s]",p.infoString()));
        String option = sc.next();
        if((option == "YES") || (option == "yes") || (option == "y") || (option == "Y")){
            rpm.removeFromStudentRepo(p);
        }
    }
    
    
    public Student searchStudent(){
        out.print("\n1) BY NAME \n2) BY GENDER \n3) BY AGE \n4) BY PHONE \n5) BY ADDRESS \n6) BY GRADE \n7) BY CGPA \n8) BY ID \n\t => ");
        
        String e_option = sc.nextLine();
        String nw_val = "";
        
        Student p = null;//new Student(); 
        String attr = "";
        
        out.println("\t\t [QUERY OPERATION] \n\n");
        
        switch(e_option){
         case "1" :
            out.printf("PROVIDE NAME: ");
            nw_val = sc.nextLine();
            out.println();
            attr = "NAME"; break;
         case "2" :
            out.printf("PROVIDE GENDER: ");
            nw_val = sc.nextLine();
            out.println();
            attr = "GENDER";
            break;
         case "3" :
            out.printf("PROVIDE AGE: ");
            nw_val = sc.nextLine();
            out.println();
            attr = "AGE";
            break;   
         case "4" :
            out.printf("PROVIDE PHONE: ");
            nw_val = sc.nextLine();
            out.println();
            attr = "PHONE";
            break;
         case "5" :
            out.printf("PROVIDE ADDRESS: ");
            nw_val = sc.nextLine();
            out.println();
            attr = "ADDRESS";
            break;
         case "6" :
             out.printf("PROVIDE GRADE: ");
             nw_val = sc.nextLine();
             out.println();
             attr = "GRADE";
             break;
         case "7" :
             out.printf("PROVIDE CGPA: ");
             nw_val = sc.nextLine();
             out.println();
             attr = "CGPA";
             break;
         case "8" :
             out.printf("PROVIDE ID: ");
             nw_val = sc.nextLine();
             out.println();
             attr = "ID";
             break;
        }
        rpm.getStudentByAttribute(attr,nw_val); 
        
        if(rpm.getQueryMatches().size() > 0) {
        	p = rpm.displayQueryMatches();
        	rpm.clearQueryMatches();
        }
        return p;
    }
    
    
    public Student editStudent(Student po){
        out.println("\n1) EDIT NAME \n2) EDIT GENDER \n3) EDIT AGE \n4) EDIT PHONE \n5) EDIT ADDRESS \n6) EDIT GRADE \n7) EDIT CGPA\n\t => ");
        
        String e_option = sc.nextLine();
        String nw_val = "";
        
        Student p = po;//new Student(); 
        
        out.println("\t [EDITING OPERATION] \n\n");
        
        out.printf("INITIAL STUDENT DATA: \n%s\n\n",po.infoString());
        
        switch(e_option){
         case "1" :
            out.printf("PROVIDE NAME: ");
            nw_val = sc.nextLine();
            out.println();
            p.setName(nw_val); break;
         case "2" :
            out.printf("PROVIDE GENDER: ");
            nw_val = sc.nextLine();
            out.println();
            p.setGender(nw_val); break;
         case "3" :
            out.printf("PROVIDE AGE: ");
            nw_val = sc.nextLine();
            out.println();
            p.setAge(nw_val); break;   
         case "4" :
            out.printf("PROVIDE PHONE: ");
            nw_val = sc.nextLine();
            out.println();
            p.setPhone(nw_val); break;
         case "5" :
            out.printf("PROVIDE ADDRESS: ");
            nw_val = sc.nextLine();
            out.println();
            p.setAddress(nw_val); break;
         case "6" :
            out.printf("PROVIDE GRADE: ");
            nw_val = sc.nextLine();
            out.println();
            p.setGrade(nw_val); break;
         case "7" :
            out.printf("PROVIDE CGPA: ");
            nw_val = sc.nextLine();
            out.println();
            p.setCGPA(Double.parseDouble(nw_val)); break;
        }
        
        out.printf("MODIFIED STUDENT DATA: \n%s\n\n",p.toString());
        rpm.replaceStudent(po,p);
        return p;
    }
}

