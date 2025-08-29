package SchoolManager;

import java.util.*;
import java.util.Scanner;
import static java.lang.System.out;

import java.io.*;

public class ExpenseRepoManager {
    
    private String expenses_repo_file = "";
    
    private Crypter crypter;
    
    private Logger logger = new LoggerMech("EXPENSES REPO MANAGER");
    
    private List<Expense> expense_repo;
    
    private List<Expense> query_matches;
    
    
    boolean expense_repo_updated;
    boolean logs_repo_updated;
    
    public ExpenseRepoManager(Crypter crypter){
        this.crypter = crypter;
        
        this.expense_repo_updated = false;
        this.logs_repo_updated = false;
        
        this.expense_repo = this.loadExpensesDataList();
        
        this.query_matches = new ArrayList<>();
    }
    
    /////////////////////////////////////////////////////////////////////////////////////
    
    public int getRepoSize(){
        return this.expense_repo.size();
    }
    
    public List<Expense> getExpenseRepo(){
    	return this.expense_repo;
    }
    
    public List<Expense> getQueryMatches(){
    	return this.query_matches;
    }
    
    public void clearQueryMatches() {
    	this.query_matches = new ArrayList<>();
    }
    
    public void storeSaveRepoUpdate(){
        logger.logINFO("SAVING REPOSITORY UPDATES ");

        out.println("\n [SAVING REPOSITORY UPDATES]");
        if(expense_repo_updated){
            out.println("\t Updating Expense Repo....");
            this.storeExpensesDataList(this.expense_repo);
            logger.logINFO("Expense Repository Updates Stored");
            this.expense_repo_updated = false;
        }
    }
    
    private List<Expense> turn(List<SchoolObject>sobj){
        List<Expense> expenses = new ArrayList<>();
        for(SchoolObject so: sobj){
            expenses.add((Expense)so);
        }return expenses;
    }
    
    //COMMANDS FOR EXPENSE REPOSITORY
    
    public void appendExpenseRepo(Expense p){
        this.expense_repo.add(p);
        //this.expense_repo_updated = true;
        this.storeExpensesDataList(this.expense_repo);
        logger.logINFO("New Expense Added To Expense Repo");
    }
    
    public void removeFromExpenseRepo(Expense p){
        if(this.expense_repo.contains(p)){
            this.expense_repo.remove(p);
            //expense_repo_updated = true;
            this.storeExpensesDataList(this.expense_repo);
            logger.logINFO("Expense Removed From Expense Repo");

        }
    }
    
    public List<Expense> getExpenseByAttribute(String attr, Object value){
        Expense match = null;
        
        for(Expense p: this.expense_repo){
            switch(attr){
                case "ITEM" : 
                     if(Objects.equals(p.getItem(),value)){
                         match = p;
                     }; break;
                case "ID" :
                    if(Objects.equals(p.getID(),value)){
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
    
    public Expense displayQueryMatches () {
    	out.println("\t [DISPLAYING QUERY RESULTS] \n");
    	
    	 Map<String,Expense> list = new HashMap<>();
         Expense p = null;
         Scanner sc = new Scanner(System.in);
         
         int count = 1;
         for(Expense ps: this.query_matches){
                 out.println(String.format("%s) %s\n",count,ps.infoString()));
                 list.put(""+count,ps);
                 count += 1;
         }
         
         out.printf("\t SELECT EXPENSE: ");
         String option = sc.next();
         out.println();
         sc = null;
         
         if(list.containsKey(option)){
             p = list.get(option);
         }
         return p;
    }
    
    public void replaceExpense(Expense p, Expense pr){
        List<Expense> p_repo = new ArrayList<>();
        out.println("\n\t [REPLACING EXPENSE]\n");
        out.println("REPO SIZE: "+this.expense_repo.size());

        for(Expense pl: this.expense_repo){
            if(pl.equals(p)){
                out.println("found Target Object\nReplacing...\n");
                p_repo.add(pr);
            }else{
                p_repo.add(pl);
            }
        } this.expense_repo = p_repo;
        out.println("REPO SIZE: "+this.expense_repo.size());
        //this.expense_repo_updated = true;
        this.storeExpensesDataList(this.expense_repo);
        logger.logINFO(String.format("Performed Replacement Operation On Expense Repository"));
    }
    
    public List<Expense> queryExpenseRepo(String attribute, String value){
        List<Expense> matches = new ArrayList<>();
        
        logger.logINFO(String.format("Querying Expense Repo: [%s = '%s']l",attribute,value));
        Expense match = null;
        
        for(Expense p: this.expense_repo){
            switch(attribute){
                case "ITEM" : 
                if(p.getItem().equalsIgnoreCase(value)){
                   match = p; 
                }; break;
                case "AMOUNT" :
                    if(p.getAmount() == Integer.parseInt(value)){
                        match = p;
                    }; break;
                case "UNIT-COST" :
                    if(p.getUnitCost() == Double.parseDouble(value)){
                        match = p;
                    }; break;
                case "TOTAL-COST" :
                    if(p.getTotalCost() == Double.parseDouble(value)){
                        match = p;
                    }; break;
                case "NOTE" :
                    if(p.getNote().equalsIgnoreCase(value)){
                        match = p;
                    }; break;
                case "ID" :
                if(p.getID().equalsIgnoreCase(value)){
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
    
    
    public List<Expense> queryExpenseRepo(String value){
        List<Expense> matches = new ArrayList<>();
        
        logger.logINFO(String.format("Querying Expense Repo: ['%s']",value));
        
        for(Expense p: this.expense_repo){
            if((p.getItem().equalsIgnoreCase(value)) || 
                (p.getAmount() == Integer.parseInt(value)) ||
                (p.getUnitCost() == Double.parseDouble(value)) ||
                (p.getTotalCost() == Double.parseDouble(value)) ||
                (p.getNote().equalsIgnoreCase(value)) ||
                (p.getID().equalsIgnoreCase(value))){
                    matches.add(p);
                } 
        }
        logger.logINFO(String.format("/t- Query Matches: %s",matches.size()));
        return matches;
    }
    
    
    public Expense listSelectExpenseRepo(){
        Expense p = null;
        
        out.println("\n\t [EXPENSE REPOSITORY LIST] \n");
        
        Map<String,Expense> list = new HashMap<>();
        
        Scanner sc = new Scanner(System.in);
        
        int count = 1;
        for(Expense ps: this.expense_repo){
                out.println(String.format("%s) %s\n",count,ps.infoString()));
                list.put(""+count,ps);
                count += 1;
        }
        out.printf("\t SELECT EXPENSE: ");
        String option = sc.next();
        out.println();
        sc = null;
        if(list.containsKey(option)){
            p = list.get(option);
        }
        return p;
    }
    
    public void listRecords(){
        out.println("\n\t [EXPENSE REPOSITORY LIST] \n");
        
        out.println("REPO SIZE: "+this.expense_repo.size());
        
        Map<String,Expense> list = new HashMap<>();
        
        int count = 1;
        for(Expense ps: this.expense_repo){
                out.println(String.format("%s) %s\n",count,ps.infoString()));
                list.put(""+count,ps);
                count += 1;
        }
    }
    
    public Expense editExpense(Expense p,String attribute,String value){
        for(Expense expense: this.expense_repo){
            if(expense.equals(p)){
            
                switch(attribute){
                    case "ITEM" :
                    p.setItem(value); break;
                    case "AMOUNT" :
                    p.setAmount(Integer.parseInt(value)); break;
                    case "UNIT-COST" :
                    p.setUnitCost(Double.parseDouble(value)); break;
                    case "TOTAL-COST" :
                    p.setTotalCost(Double.parseDouble(value)); break;
                    case "NOTE" :
                    p.setNote(value); break;
                }
            }
        } this.storeExpensesDataList(this.expense_repo);
        return p;
    }
    
    public void displayLogs(){
        out.println(this.logger.report().toString());
    }
     
    
    ///FOR EXPENSES DATA REPOSITORY
    public void storeExpensesDataList(List<Expense> obj_list){
        //ENCRYPT, ENCODE, PERSIST
        try(FileWriter fw = new FileWriter(expenses_repo_file,false);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);){ 
            String data = ""; String encoded_data = "";
            for(Expense obj: obj_list){
                data = obj.toString();
                byte[] encrypted_data = crypter.encrypt(data);
                encoded_data = crypter.encode64(encrypted_data);
                pw.println(encoded_data);
            }pw.flush(); pw.close();
            this.expense_repo = obj_list;
            out.println("\t DATA LIST STORED TO FILE! \n");
        }catch(IOException ioe){ioe.printStackTrace();}
    }
    public List<Expense> loadExpensesDataList(){
        List<Expense> data_list = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(expenses_repo_file));
            String line = "";
            while((line = br.readLine()) != null){
                if(!line.trim().isEmpty()){
                    byte[] encryptedData = crypter.decode64(line);
                    String decrypted = crypter.decrypt(encryptedData);
                    Expense so = new Expense(decrypted);
                    data_list.add(so);
                }
            }br.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        } return data_list;
    }
    /////////////////////////////////////////////////////////////////////////////////////////
     
    
}