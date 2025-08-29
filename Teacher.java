package SchoolManager;

public class Teacher extends Staff{
    
    private Subject subject;
    
    public Teacher(){
        super();
    }
    
    public Teacher(Subject subject){
        this.subject = subject;
    }
    
    public void setSubject(Subject subj){
        this.subject = subj;
    }
    
    public Subject getSubject(){
        return this.subject;
    }
    
}