package SchoolManager;

public class StudentScore implements SchoolObject{
    
    private String student_name;
    private String student_id;
    private String grade;
    
    private String subject;
    private String subject_code;
    
    private double score;
    
    public StudentScore(){
        this.student_name = "NONE";
        this.student_id = "NONE";
        this.grade = "NONE";
        
        this.subject = "";
        this.subject_code = "";
        this.score = 0.0;
    }
    
    
    
    public StudentScore(Student std,Subject subj,double score){
        this.student_name = std.getName();
        this.student_id = std.getID();
        this.grade = std.getGrade();
        
        this.subject = subj.getTitle();
        this.subject_code = subj.getCode();
        
        this.score = score;
    }
    
    public StudentScore(String sn,String id,String grade,String subj,String code,double score){
        this.student_name = sn;
        this.student_id = id;
        this.grade = grade;
        this.subject = subj;
        this.subject_code = code;
        this.score = score;
    }
    
    public StudentScore(String csv){
        String[] dta = csv.split(",");
        
        this.student_id = dta[0];
        this.student_name = dta[1];
        this.grade = dta[2];
        this.subject = dta[3];
        this.subject_code = dta[4];
        this.score = Double.parseDouble(dta[5]);
    }
    
    public void setStudent(Student std){
        this.student_name = std.getName();
        this.student_id = std.getID();
        this.grade = std.getGrade();
    }
    
    public void setStudentName(String nme){
        this.student_name = nme;
    }
    
    public void setStudentID(String id){
        this.student_id = id;
    }
    
    public void setStudentGrade(String grade){
        this.grade = grade;
    }
    
    public void setSubject(String subject){
        this.subject = subject;
    }
    
    public void setSubjectCode(String code){
        this.subject_code = code;
    }
    
    public void setScore(double score){
        this.score = score;
    }
    
    //////////////////////////////////////////////////////////////////////
    public String getStudentName(){
        return this.student_name;
    }
    
    public String getStudentID(){
        return this.student_id;
    }
    
    public String getStudentGrade(){
        return this.grade;
    }
    
    public String getSubject(){
        return this.subject;
    }
    
    public String getSubjectCode(){
        return this.subject_code;
    }
    
    public double getScore(){
        return this.score;
    }
    
    //////////////////////////////////////////////////////
    
    public void setObject(String csv){
        String[] dta = csv.split(",");
        
        this.student_id = dta[0];
        this.student_name = dta[1];
        this.grade = dta[2];
        this.subject = dta[3];
        this.subject_code = dta[4];
        this.score = Double.parseDouble(dta[5]);
    }
    
    public SchoolObject getObject(){
        return new StudentScore();
    }
    
    public String infoString(){
        return String.format("Grade: %s\t\tID: %s\nStudent Name: %s\nSubject: %s\nCode: %s\nStudent Score: %s",this.grade,this.student_id,this.student_name,this.subject,this.subject_code,this.score);
    }
    
    @Override
    public String toString(){
        return String.format("%s,%s,%s,%s,%s,%s",this.student_id,this.student_name,this.grade,this.subject,this.subject_code,this.score);
    }
    
    @Override
    public boolean equals(Object obj){
        StudentScore sobj = (StudentScore)obj;
        
        if((sobj.getStudentID().equals(this.student_id)) && (sobj.getStudentName().equals(this.student_name)) && (sobj.getSubject().equals(this.subject))){
            return true;
        }else
            return false;
    }
    
}