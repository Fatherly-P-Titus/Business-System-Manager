package SchoolManager;

public class Student implements SchoolObject{
    private String name;
    private String grade;
    private String age;
    private String gender;
    private String phone;
    private String address;
    
    private double CGPA;
    
    private String ID;
    
    /// CONSTRUCTORS
    public Student(){
        this.name = "John Doe";
        this.gender = "M";
        this.age = "35";
        this.phone = "080000000";
        this.address = "10 New Shelby Drive";
        this.grade = "JNR";
        this.CGPA = 5.0;
        
        this.generateID();
    }
    
    public Student(String n,String d,String p,String a){
        this.name = n;
        this.grade = d;
        this.phone = p;
        this.address = a;
        
        this.gender = "M";
        this.age = "13";
        this.CGPA = 5.0;
        
        this.generateID();
    }
    
    public Student(String n,String g,String age,String d,String p,String a){
        this.name = n;
        this.grade = d;
        this.phone = p;
        this.address = a;
        this.gender = g;
        this.age = age;
        this.CGPA = 5.0;
        
        this.generateID();
    }
    
    public Student(String n,String g,String age,String d,String p,String a,double cgpa){
        this.name = n;
        this.grade = d;
        this.phone = p;
        this.address = a;
        this.gender = g;
        this.age = age;
        this.CGPA = cgpa;
        
        this.generateID();
    }
    
    //ID,name, gender, age, desig, phone, address, auth
    public Student(String csv){
        String[]dta = csv.split(",");
        
        this.name = dta[1];
        this.gender = dta[2];
        this.age = dta[3];
        this.grade = dta[4];
        this.phone = dta[5];
        this.address = dta[6];
        this.CGPA = Double.parseDouble(dta[7]);
        
        this.ID = dta[0];
    }

    public void setObject(String csv){
        String[]dta = csv.split(",");
        
        this.name = dta[1];
        this.gender = dta[2];
        this.age = dta[3];
        this.grade = dta[4];
        this.phone = dta[5];
        this.address = dta[6];
        this.CGPA = Double.parseDouble(dta[7]);
        
        this.ID = dta[0];
    }
    
    public Student getObject(){
        return new Student();
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
    
    public String getGrade (){
        return this.grade;
    }
    
    public String getPhone (){
        return this.phone;
    }
    
    public String getAddress (){
        return this.address;
    }
    
    public double getCGPA(){
        return this.CGPA;
    }
    
    public String getID (){
        return this.ID;
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
    
    public void setGrade(String desig){
        this.grade = desig;
    }
    
    public void setPhone(String p){
        this.phone = p;
    }
    
    public void setAddress(String a){
        this.address = a;
    }
    
    public void setCGPA(double cgpa){
        this.CGPA = cgpa;
    }
    
    public void setID(String id){
        this.ID = id;
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
        return String.format("ID: %s\tCGPA: %s\nNAME: %s\nGENDER: %s\tAGE: %s\nGRADE: %s\nPHONE: %s\nADDRESS: %s\n",this.ID,this.CGPA,this.name,this.gender,this.age,this.grade,this.phone,this.address);
    }
    
    @Override
    public String toString(){
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",this.ID,this.name,this.gender,this.age,this.grade,this.phone,this.address,this.CGPA);
    }
    
    @Override
    public boolean equals(Object pobj){
        Student p = (Student) pobj;
        
        if((p.getName() == this.name) && (p.getID() == this.ID)){
          return true;   
        }else return false;
    }
}