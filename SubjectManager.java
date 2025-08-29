package SchoolManager;



import static java.lang.System.out;

import java.util.Scanner;
import java.util.*;

public class SubjectManager{
    
    SubjectRepoManager rpm;
    
    Scanner sc;
    SystemFileOps sys_file_ops;

    List<Subject> repo;
    
    public SubjectManager(SystemFileOps sfo, SubjectRepoManager rpm){
        this.rpm = rpm;
        sc = new Scanner(System.in);
        this.sys_file_ops = sfo;//new SystemFileMech(new CrypterSymmetricMech());
    }
    
    
    public Subject createSubject(){
        Subject p = null;
        
        out.printf("SUBJECT TITLE: ");
        String p_title = sc.nextLine();
        
        out.println();
        out.printf("SUBJECT CODE: ");
        String p_code = sc.nextLine();
        
        out.println();
        out.printf("SUBJECT CREDITS: ");
        double p_credits = sc.nextDouble();
        out.println();
        
        out.println("\n\t CREATED NEW SUBJECT");
         
        p = new Subject(p_title,p_code,p_credits);
        rpm.appendSubjectRepo(p);
        
        return p;
    }
    
    public void deleteSubject(Subject p){
        out.println(String.format("\t SURE TO REMOVE SUBJECT ?\n[%s]",p.infoString()));
        String option = sc.next();
        if((option == "YES") || (option == "yes") || (option == "y") || (option == "Y")){
            rpm.removeFromSubjectRepo(p);
        }
    }
    
    
    public Subject searchSubject(){
        out.print("\n1) BY TITLE \n2) BY CODE \n3) BY CREDITS \n\t => ");
        
        String e_option = sc.nextLine();
        String nw_val = "";
        
        Subject p = null;//new Subject(); 
        String attr = "";
        
        out.println("\t\t [QUERY OPERATION] \n\n");
        
        switch(e_option){
         case "1" :
            out.printf("PROVIDE TITLE: ");
            nw_val = sc.nextLine();
            out.println();
            attr = "TITLE"; break;
         case "2" :
            out.printf("PROVIDE CODE: ");
            nw_val = sc.nextLine();
            out.println();
            attr = "CODE";
            break;
         case "3" :
            out.printf("PROVIDE CREDITS: ");
            nw_val = sc.nextLine();
            out.println();
            attr = "CREDITS";
            break;
        }
        rpm.getSubjectByAttribute(attr,nw_val); 
        
        if(rpm.getQueryMatches().size() > 0) {
        	p = rpm.displayQueryMatches();
        	rpm.clearQueryMatches();
        }
        return p;
    }
    
    
    public Subject editSubject(Subject po){
        out.println("\n1) EDIT TITLE \n2) EDIT CODE \n3) EDIT CREDITS \n\t => ");
        
        String e_option = sc.nextLine();
        String nw_val = "";
        
        Subject p = po;//new Subject(); 
        
        out.println("\t [EDITING OPERATION] \n\n");
        
        out.printf("INITIAL SUBJECT DATA: \n%s\n\n",po.infoString());
        
        switch(e_option){
         case "1" :
            out.printf("PROVIDE TITLE: ");
            nw_val = sc.nextLine();
            out.println();
            p.setTitle(nw_val); break;
         case "2" :
            out.printf("PROVIDE CODE: ");
            nw_val = sc.nextLine();
            out.println();
            p.setCode(nw_val); break;
         case "3" :
            out.printf("PROVIDE CREDITS: ");
            nw_val = sc.nextLine();
            out.println();
            p.setCredits(Double.parseDouble(nw_val)); break;   
        }
        
        out.printf("MODIFIED SUBJECT DATA: \n%s\n\n",p.toString());
        rpm.replaceSubject(po,p);
        return p;
    }
}

