package SchoolManager;

import java.io.Serializable;

public class IVault implements Serializable{
    
    boolean stat;
    
    int iv_size;
    byte[] iv1;
    byte[] iv2;
    
    
    public IVault(){
        this.stat = false;
        this.iv_size = 16;
        this.iv1 = new byte[16];
        this.iv2 = new byte[16];
    }
    
    public void setIVault1(byte[] b){
        this.iv1 = b;
    }
    
    public void setIVault2(byte[] b){
        this.iv2 = b;
    }
    
    public void setStat(boolean stat){
        this.stat = stat;
    }
    
    public boolean getStat(){
        return this.stat;
    }
    
    public byte[] IVault1(){
        return this.iv1;
    }
    
    public byte[] IVault2(){
        return this.iv2;
    }
    
    public String toString(){
       StringBuilder sb = new StringBuilder();
        String s = "";
        for(byte b: this.iv1){
            s += ""+b+",";
        }
        sb.append(String.format("•) IV1: [%s]\n",s.substring(0,s.length()-1)));
        s = "";
        for(byte b: this.iv2){
            s += ""+b+",";
        }
        sb.append(String.format("•) IV2: [%s]\n",s.substring(0,s.length()-1)));

        return sb.toString();
    }
    
}