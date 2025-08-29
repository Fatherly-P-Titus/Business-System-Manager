package SchoolManager;

import static java.lang.System.out;

import java.util.Scanner;
import java.util.*;

public class ExpenseManager{
    
    private ExpenseRepoManager rpm;
    
    private Scanner sc;
    private SystemFileOps sys_file_ops;
    
    public ExpenseManager(SystemFileOps sfo, ExpenseRepoManager rpm){
        this.rpm = rpm;
        sc = new Scanner(System.in);
        this.sys_file_ops = sfo;
    }
    
    
    public Expense createExpense(){
        Expense p = null;
        
        out.printf("EXPENSE ITEM: ");
        String p_item = sc.nextLine();
        
        out.println();
        out.printf("AMOUNT: ");
        int p_amount = sc.nextInt();
        
        out.println();
        out.printf("UNIT COST PER ITEM: ");
        double p_unit = sc.nextDouble();
        
        out.println();
        out.printf("EXPENSE NOTE: ");
        String p_note = sc.nextLine();
        
        
        out.println("\n\t CREATED NEW EXPENSE");
        
        p = new Expense(p_item,p_amount,p_unit,p_note);
       
        rpm.appendExpenseRepo(p);
        
        return p;
    }
    
    public void deleteExpense(Expense p){
        out.println(String.format("\t SURE TO REMOVE EXPENSE ?\n[%s]",p.infoString()));
        String option = sc.next();
        if((option == "YES") || (option == "yes") || (option == "y") || (option == "Y")){
            rpm.removeFromExpenseRepo(p);
        }
    }
    
    
    public Expense searchExpense(){
        out.print("\n1) BY ITEM \n2) BY AMOUNT \n3) BY UNIT COST \n4) BY TOTAL AMOUNT \n5) BY NOTE \n6) BY ID \n\t => ");
        
        String e_option = sc.nextLine();
        String nw_val = "";
        
        Expense p = null;//new Expense(); 
        String attr = "";
        
        out.println("\t\t [QUERY OPERATION] \n\n");
        
        switch(e_option){
         case "1" :
            out.printf("PROVIDE ITEM: ");
            nw_val = sc.nextLine();
            out.println();
            attr = "ITEM"; break;
         case "2" :
            out.printf("PROVIDE AMOUNT: ");
            nw_val = sc.nextLine();
            out.println();
            attr = "AMOUNT";
            break;
         case "3" :
            out.printf("PROVIDE UNIT COST: ");
            nw_val = sc.nextLine();
            out.println();
            attr = "UNIT-COST";
            break;   
         case "4" :
            out.printf("PROVIDE TOTAL COST: ");
            nw_val = sc.nextLine();
            out.println();
            attr = "TOTAL-COST";
            break;
         case "5" :
            out.printf("PROVIDE NOTE: ");
            nw_val = sc.nextLine();
            out.println();
            attr = "NOTE";
            break;
         case "6" :
             out.printf("PROVIDE ID: ");
             nw_val = sc.nextLine();
             out.println();
             attr = "ID";
             break;
        }
        rpm.getExpenseByAttribute(attr,nw_val); 
        
        if(rpm.getQueryMatches().size() > 0) {
        	p = rpm.displayQueryMatches();
        	rpm.clearQueryMatches();
        }
        return p;
    }
    
    
    public Expense editExpense(Expense po){
        out.println("\n1) EDIT ITEM \n2) EDIT AMOUNT \n3) EDIT UNIT COST \n4) EDIT NOTE  \n\t => ");
        
        String e_option = sc.nextLine();
        String nw_val = "";
        
        Expense p = po;//new Expense(); 
        
        out.println("\t [EDITING OPERATION] \n\n");
        
        out.printf("INITIAL EXPENSE DATA: \n%s\n\n",po.infoString());
        
        switch(e_option){
         case "1" :
            out.printf("PROVIDE ITEM: ");
            nw_val = sc.nextLine();
            out.println();
            p.setItem(nw_val); break;
         case "2" :
            out.printf("PROVIDE AMOUNT: ");
            nw_val = sc.nextLine();
            out.println();
            p.setAmount(Integer.parseInt(nw_val)); break;
         case "3" :
            out.printf("PROVIDE UNIT COST: ");
            nw_val = sc.nextLine();
            out.println();
            p.setUnitCost(Double.parseDouble(nw_val)); break;   
         case "4" :
            out.printf("PROVIDE NOTE: ");
            nw_val = sc.nextLine();
            out.println();
            p.setNote(nw_val); break;
        }
        
        out.printf("MODIFIED EXPENSE DATA: \n%s\n\n",p.toString());
        rpm.replaceExpense(po,p);
        return p;
    }
}

