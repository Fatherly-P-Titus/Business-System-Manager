package SchoolManager;

import static java.lang.System.out;

import java.util.Scanner;
import java.util.*;

public class StaffManager{
    
    RepoManager rpm;
    
    Scanner sc;

    List<Staff> repo;
    
    
    public StaffManager(RepoManager rpm){
        this.rpm = rpm;
        sc = new Scanner(System.in);
        
    }
    
    
    public Staff assignStaffDesignation(Staff p){
        out.println("\n\t [ASSIGNING Staff DESIGNATION]\n");
        
        out.println(String.format("\n•) %s\n",p.toString()));
        
        Staff po = p;
        
        out.printf("ASSIGN AUTH DESIGNATION: \n1) ADMIN\n2) SNR\n3) JNR\n\t=> ");
        //Scanner sc = new Scanner(System.in);
        String auth = sc.nextLine();
        
        switch(auth){
            case "1" : 
            p.setDesignation("ADMIN");
            //Set Auth_Pass
            p.setAuth(rpm.generateStaffAuth());
            rpm.appendAuthRepo(p);
            break;
            case "2" :
            p.setDesignation("SNR");
            p.setAuth(rpm.generateStaffAuth());
            break;
            case "3" :
            p.setDesignation("JNR");
            p.setAuth("NONE");
            break;
        }
        rpm.replaceStaff(po,p);
        rpm.replaceStaffAuth(po,p);
        return p;
    }
    
    public Staff createStaff(){
        Staff p = null;
        
        out.printf("Staff NAME: ");
        String p_name = sc.nextLine();
        
        out.println();
        out.printf("Staff GENDER: ");
        String p_gender = sc.nextLine();
        
        out.println();
        out.printf("STAFF AGE: ");
        String p_age = sc.nextLine();
        
        out.println();
        out.printf("STAFF PHONE: ");
        String p_phone = sc.nextLine();
        
        out.println();
        out.printf("STAFF ADDRESS: ");
        String p_address = sc.nextLine();
        
        out.println("\n\t CREATED NEW STAFF");
        
        p = new Staff(p_name,p_gender,p_age,"JNR",p_phone,p_address);
       
        rpm.appendStaffRepo(p);
        
        return p;
    }
    
    public void deleteStaff(Staff p){
        out.println(String.format("\t SURE TO REMOVE STAFF ?\n[%s]",p.infoString()));
        String option = sc.next();
        if((option == "YES") || (option == "yes") || (option == "y") || (option == "Y")){
            rpm.removeFromStaffRepo(p);
        }
    }
    
    
    public Staff searchStaff(){
        out.print("\n1) BY NAME \n2) BY GENDER \n3) BY AGE \n4) BY PHONE \n5) BY ADDRESS \n6) BY DESIGNATION \n7) BY AUTH \n8) BY ID \n\t => ");
        
        String e_option = sc.nextLine();
        String nw_val = "";
        
        Staff p = null;//new Staff(); 
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
             out.printf("PROVIDE DESIGNATION: ");
             nw_val = sc.nextLine();
             out.println();
             attr = "DESIGNATION";
             break;
         case "7" :
             out.printf("PROVIDE AUTH: ");
             nw_val = sc.nextLine();
             out.println();
             attr = "AUTH";
             break;
         case "8" :
             out.printf("PROVIDE ID: ");
             nw_val = sc.nextLine();
             out.println();
             attr = "ID";
             break;
        }
        rpm.getStaffByAttribute(attr,nw_val); 
        
        if(rpm.getQueryMatches().size() > 0) {
        	p = rpm.displayQueryMatches();
        	rpm.clearQueryMatches();
        }
        return p;
    }
    
    
    public Staff editStaff(Staff po){
        out.println("\n1) EDIT NAME \n2) EDIT GENDER \n3) EDIT AGE \n4) EDIT PHONE \n5) EDIT ADDRESS \n\t => ");
        
        String e_option = sc.nextLine();
        String nw_val = "";
        
        Staff p = po; 
        
        out.println("\t [EDITING OPERATION] \n\n");
        
        out.printf("INITIAL STAFF DATA: \n%s\n\n",po.infoString());
        
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
        }
        
        out.printf("MODIFIED STAFF DATA: \n%s\n\n",p.toString());
        rpm.replaceStaff(po,p);
        return p;
    }
}

