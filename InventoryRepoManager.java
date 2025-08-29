package SchoolManager;

import java.io.*;
import java.util.*;

public class InventoryRepoManager {
    
    private final String inventory_file = "inventory_repo.txt";
    
    private Crypter crypter;
    
    private List<Inventory> inventory;
    private List<Inventory> query_matches;
    
    private Logger logger;
    
    public InventoryRepoManager(Crypter crypter,Logger logger){
        this.crypter = crypter;
        
        this.inventory = new ArrayList<>();
        
        this.query_matches = new ArrayList<>();
        
        this.logger = logger;
    }
    
    public List<Inventory> getInventory(){
        return this.inventory;
    }
    public List<Inventory> getQueryMatches(){
        return this.query_matches;
    }
    
    
    public void addInventoryItem(Inventory item){
        this.inventory.add(item);
        
        this.logger.log("ITEM ADDED TO INVENTORY");
        this.storeInventoryData();
    }
    
    
    public List<Inventory> getInventoryByAttribute(String attr, Object value){
        Inventory match = null;
        
        for(Inventory p: this.inventory){
            switch(attr){
                case "NAME" : 
                if(Objects.equals(p.getItem(),value)){
                    this.query_matches.add(p);
                } break;
                case "ID" :
                if(Objects.equals(p.getID(),value)){
                    this.query_matches.add(p);
                }break;
                case "DATE ENTERED" :
                if(Objects.equals(p.getDateStored(),value)){
                    this.query_matches.add(p);
                }break;
                case "DATE UPDATED" :
                if(Objects.equals(p.getDateUpdated(),value)){
                    this.query_matches.add(p);
                }break;
                case "AMOUNT" :
                if(p.getItemCount() == (int)(value)){
                    this.query_matches.add(p);
                }break;
            }
        }
            if(match != null) {
            	this.query_matches.add(match);
            	match = null;
            }
        return this.query_matches;
    }
    
    
    public List<Inventory> queryInventoryRepo(String attribute, String value){
        List<Inventory> matches = new ArrayList<>();
        
        logger.logINFO(String.format("Querying Inventory Repo: [%s = '%s']l",attribute,value));
        
        for(Inventory p: this.inventory){
            switch(attribute){
                case "NAME" : 
                if(Objects.equals(p.getItem(),value)){
                    matches.add(p);
                } break;
                case "ID" :
                if(Objects.equals(p.getID(),value)){
                    matches.add(p);
                }break;
                case "DATE ENTERED" :
                if(Objects.equals(p.getDateStored(),value)){
                    matches.add(p);
                }break;
                case "DATE UPDATED" :
                if(Objects.equals(p.getDateUpdated(),value)){
                    matches.add(p);
                }break;
                case "AMOUNT" :
                if(p.getItemCount() == Integer.parseInt(value)){
                    matches.add(p);
                }break;
            }
        }
        if(matches.size() > 0){
            logger.logINFO(String.format("\t- Query Matches Found: %s",matches.size()));
        }
        return matches;
    }
    
    
    public List<Inventory> queryInventoryRepo(String value){
        List<Inventory> matches = new ArrayList<>();
        
        logger.logINFO(String.format("Querying Inventory Repo: ['%s']",value));
        
        for(Inventory p: this.inventory){
            if((Objects.equals(p.getItem(),value)) || 
                (Objects.equals(p.getDateStored(),value)) || 
                (Objects.equals(p.getDateUpdated(),value)) || 
                (Objects.equals(p.getID(),value)) || 
                (p.getItemCount() == Integer.parseInt(value))
                ){
                    matches.add(p);
                } 
        }logger.logINFO(String.format("/t- Query Matches: %s",matches.size()));
        return matches;
    }
    
    
    public void loadInventoryData(){
        try{
            BufferedReader br = new BufferedReader( new FileReader(this.inventory_file));
            String line, data;
            
            while((line = br.readLine()) != null){
                if(!line.trim().isEmpty()){
                    data = this.crypter.decodeDecrypt(line);
                    
                    Inventory inv = new Inventory(data);
                    this.inventory.add(inv);
                }
            } br.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    private void storeInventoryData(){
        if(this.inventory.size() > 0){
            String data = "";
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(this.inventory_file));
            PrintWriter pw = new PrintWriter(bw)){
                for(Inventory item: this.inventory){
                    data = this.crypter.encryptEncode(item.toString());
                    pw.println(data);
                }
                pw.flush(); pw.close();
                this.logger.log("STORED INVENTORY RECORDS DATA SUCCESSFULLY");
            }catch(IOException ioe){
                ioe.printStackTrace();
            }
        }
    }
    
    
}