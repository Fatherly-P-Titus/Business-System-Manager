package SchoolManager;

public class Subject implements SchoolObject{
    private String title;
    private String code;
    
    private double credits;
    
    public Subject(){
        
    }
    
    public Subject(String title,String code,double creds){
        this.title = title;
        this.code = code;
        this.credits = creds;
    }
    
    public Subject(String csv){
        String[]dta = csv.split(",");
        
        this.title = dta[0];
        this.code = dta[1];
        this.credits = Double.parseDouble(dta[2]);
    }
    
    public void setObject(String csv){
        String[]dta = csv.split(",");
        
        this.title = dta[0];
        this.code = dta[1];
        this.credits = Double.parseDouble(dta[2]);

    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
    public void setCode(String code){
        this.code = code;
    }
    
    public void setCredits(double creds){
        this.credits = creds;
    }
    
    public Subject getObject(){
        return new Subject();
    }
    
    public String getTitle(){
        return this.title;
    }
    
    public String getCode(){
        return this.code;
    }
    
    public double getCredits(){
        return this.credits;
    }
    
    public String infoString(){
        return String.format("");
    }
    
    @Override
    public String toString(){
        return String.format("%s,%s,%s",this.title,this.code,this.credits);
    }
    
    @Override
    public boolean equals(Object obj){
        Subject sobj = (Subject) obj;
        
        if((sobj.getTitle().equalsIgnoreCase(this.title)) && (sobj.getCode().equalsIgnoreCase(this.code))){
            return true;
        }else
            return false;
    }
    
}