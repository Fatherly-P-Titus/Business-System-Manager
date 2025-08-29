package SchoolManager;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Staff implements SchoolObject{
    
    protected String name;
    protected String designation;
    protected String age;
    protected String gender;
    protected String phone;
    protected String address;
    
    protected String ID;
    protected String auth_pass;
    
    protected double salary;
    protected String DOA;
    
    private String adate = new SimpleDateFormat("dd/MM/yyy").format(new Date());
    /// CONSTRUCTORS
    public Staff(){
        this.name = "John Doe";
        this.gender = "M";
        this.age = "35";
        this.phone = "080000000";
        this.address = "10 New Shelby Drive";
        this.designation = "JNR";
        this.salary = 00.0;
        
        this.DOA = adate;
        
        this.generateID();
        this.auth_pass = "NONE";
    }
    
    public Staff(String n,String g,String age,String d,String p,String a){
        this.name = n;
        this.age = age;
        this.gender = g;
        this.designation = d;
        this.phone = p;
        this.address = a;
        
        this.generateID();
        
        this.auth_pass = "NONE";
        this.salary = 00.0;
        
        this.DOA = adate;
    }
    
    public Staff(String n,String g,String age,String d,String p,String a,double pay,String doa){
        this.name = n;
        this.designation = d;
        this.phone = p;
        this.address = a;
        this.gender = g;
        this.age = age;
        this.salary = pay;
        
        this.DOA = doa;
    
        this.generateID();
        
        this.auth_pass = "NONE";
    }
    
    //ID,name, gender, age, desig, phone, address, auth
    public Staff(String csv){
        String[]dta = csv.split(",");
        
        this.name = dta[1];
        this.gender = dta[2];
        this.age = dta[3];
        this.designation = dta[4];
        this.phone = dta[5];
        this.address = dta[6];
        this.salary = Double.parseDouble(dta[7]);
        
        this.ID = dta[0];
        
        this.auth_pass = dta[8];
        this.DOA = dta[9];
    }

    
    public void setObject(String csv){
        String[]dta = csv.split(",");
        
        this.name = dta[1];
        this.gender = dta[2];
        this.age = dta[3];
        this.designation = dta[4];
        this.phone = dta[5];
        this.address = dta[6];
        this.salary = Double.parseDouble(dta[7]);
        
        this.ID = dta[0];
        
        this.auth_pass = dta[8];
        this.DOA = dta[9];
    }
    
    public Staff getObject(){
        return new Staff();
    }
    
    /// GETTER METHODS
    public String getName (){
        return this.name;
    }
    
    public String getGender(){
        return this.gender;
    }
    
    public String getAge(){
        return this.age;
    }
    
    public String getDesignation (){
        return this.designation;
    }
    
    public String getPhone (){
        return this.phone;
    }
    
    public String getAddress (){
        return this.address;
    }
    
    public double getSalary(){
        return this.salary;
    }
    
    public String getID (){
        return this.ID;
    }
    
    public String getAuth(){
        return this.auth_pass;
    }
    
    public String DOA(){
        return DOA;
    }
    
    /// SETTER MRTHODS
    public void setName(String nm){
        this.name = nm;
    }
    
    public void setGender(String gender){
        this.gender = gender;
    }
    
    public void setAge(String age){
        this.age = age;
    }
    
    public void setDesignation(String desig){
        this.designation = desig;
    }
    
    public void setPhone(String p){
        this.phone = p;
    }
    
    public void setAddress(String a){
        this.address = a;
    }
    
    public void setSalary(double pay){
        this.salary = pay;
    }
    
    public void setAuth(String auth){
        this.auth_pass = auth;
    }
    
    public void setID(String id){
        this.ID = id;
    }
    
    public void setDOA(String doa){
        this.DOA = doa;
    }
    
    private void generateID(){
        String id = "";
        
        for(int i = 0; i < 9; i++){
            id += String.format("%s",(int)(Math.random()*9));
        }
        this.ID = id;
    }
    
    
    public String infoString(){
        //the object String to be shown / displayed
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s",this.ID,this.name,this.gender,this.age,this.designation,this.phone,this.address,this.salary,this.auth_pass);
    }
    
    @Override
    public String toString(){
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",this.ID,this.name,this.gender,this.age,this.designation,this.phone,this.address,this.salary,this.auth_pass,this.DOA);
    }
    
    @Override
    public boolean equals(Object pobj){
        Staff p = (Staff) pobj;
        
        if((p.getName() == this.name) && (p.getID() == this.ID)){
          return true;   
        }else return false;
    }
}