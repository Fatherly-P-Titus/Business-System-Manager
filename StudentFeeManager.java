package SchoolManager;



import static java.lang.System.out;

import java.util.Scanner;
import java.util.*;

public class StudentFeeManager{
    
    StudentFeeRepoManager rpm;
    
    Student student;
    
    Scanner sc;
    
    public StudentFeeManager( StudentFeeRepoManager rpm){
        this.rpm = rpm;
        sc = new Scanner(System.in);
        this.student = null;
        //this.sys_file_ops = sfo;//new SystemFileMech(new CrypterSymmetricMech());
    }
    
    
    public void setStudent(Student std){
        this.student = std;
    }
    
    
    public StudentFee createStudentFee(){
        StudentFee p = null;
        
        out.printf("STUDENT NAME: ");
        String p_name = sc.nextLine();
        
        out.println();
        out.printf("STUDENT ID: ");
        String p_id = sc.nextLine();
        
        out.println();
        out.printf("STUDENT TUITION: ");
        double p_tuition = sc.nextDouble();
        
        out.println();
        
        out.printf("AMOUNT PAID: ");
        double p_amnt_paid = sc.nextDouble();
        
       // out.println("\n\t CREATED NEW STUDENT");
         
        p = new StudentFee(p_name,p_id,p_tuition,p_amnt_paid);
        
        rpm.appendStudentFeeRepo(p);
        
        return p;
    }
    
    /*public void deleteStudentFee(StudentFee p){
        out.println(String.format("\t SURE TO REMOVE STUDENT ?\n[%s]",p.infoString()));
        String option = sc.next();
        if((option == "YES") || (option == "yes") || (option == "y") || (option == "Y")){
            rpm.removeFromStudentFeeRepo(p);
        }
    }*/
    
    
    public StudentFee searchStudentFee(){
        
        //List<StudentFee>query_list = new ArrayList<>();
        
        out.print("\n1) BY ID \n2) BY TOTAL AMOUNT \n3) BY AMOUNT PAID \n4) BY AMOUNT OWED \n\t => ");
        
        String e_option = sc.nextLine();
        String nw_val = "";
        
        StudentFee p = null;//new StudentFee(); 
        String attr = "";
        
        out.println("\t\t [QUERY OPERATION] \n\n");
        
        switch(e_option){
         case "1" :
            out.printf("PROVIDE ID: ");
            nw_val = sc.nextLine();
            out.println();
            attr = "ID"; break;
         case "2" :
            out.printf("PROVIDE TOTAL AMOUNT: ");
            nw_val = sc.nextLine();
            out.println();
            attr = "FEE-TOTAL";
            break;
         case "3" :
            out.printf("PROVIDE AMOUNT PAID: ");
            nw_val = sc.nextLine();
            out.println();
            attr = "FEE-PAID";
            break;   
         case "4" :
            out.printf("PROVIDE AMOUNT OWED: ");
            nw_val = sc.nextLine();
            out.println();
            attr = "FEE-OWED";
            break;
        }
        rpm.getStudentFeeByAttribute(attr,nw_val); 
        
        if(rpm.getQueryMatches().size() > 0) {
        	p = rpm.displayQueryMatches();
        	rpm.clearQueryMatches();
        }
        return p;
    }
    
    
    public StudentFee editStudentFee(StudentFee po){
        out.println("\n1) EDIT FEES TOTAL\n2) ADD FEES PAID \n\t => ");
        
        String e_option = sc.nextLine();
        String nw_val = "";
        
        StudentFee p = po;//new StudentFee(); 
        
        out.println("\t [EDITING OPERATION] \n\n");
        
        out.printf("INITIAL STUDENT DATA: \n%s\n\n",po.infoString());
        
        switch(e_option){
         case "1" :
            out.printf("PROVIDE TOTAL AMOUNT: ");
            nw_val = sc.nextLine();
            out.println();
            p.setTotalFee(Double.parseDouble(nw_val)); break;
         case "2" :
            out.printf("PROVIDE PAID AMOUNT: ");
            nw_val = sc.nextLine();
            out.println();
            
            p.addAmountPaid(Double.parseDouble(nw_val)); break;
        }
        
        out.printf("MODIFIED STUDENT DATA: \n%s\n\n",p.toString());
        rpm.replaceStudentFee(po,p);
        return p;
    }
}

