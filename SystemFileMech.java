package SchoolManager;

import java.util.*;
import java.io.*;

import static java.lang.System.out;


public class SystemFileMech implements SystemFileOps{
    
    private Logger logger;
    private Crypter crypter;
    
    // CONSTRUCTOR
    public SystemFileMech(Crypter crypter,Logger logger){
        
        this.crypter = crypter;
        this.logger = logger;
    }
    
    
    /// SETTER METHODS
    public void setCrypter(Crypter crypter){
        this.crypter = crypter;
    }
    
    
    //STORING OBJECT DATA TO FILE
    public void storeDataList(List<String> obj_list, String file){
        //ENCRYPT, ENCODE, PERSIST
        try(FileWriter fw = new FileWriter(file,false);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);){
            String data = "";
            
            for(String obj: obj_list){
                data = this.crypter.encryptEncode(obj);
                pw.println(data);
            }pw.flush(); pw.close();
            logger.log("DATA LIST STORED TO FILE! \n");
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    
    public List<String> loadDataList(String file){
        List<String> data_list = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = "",data;
            while((line = br.readLine()) != null){
                if(!line.trim().isEmpty()){
                    data = this.crypter.decodeDecrypt(line);
                    data_list.add(data);
                }
            }br.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        } return data_list;
    }
    ///////////////////////////////////////////////////////////////////////////////////////
    
    public List<String> loadRepositoryData(String file){
        List<String>repo = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(staff_auth_data_file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    repo.add(line);
                }
            }br.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }return repo;
    }
    
    
    public String infoString(){
        return String.format("SYSTEM-FILE-MECH");
    }
    
}