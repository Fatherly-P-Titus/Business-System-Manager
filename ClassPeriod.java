package SchoolManager;

public class ClassPeriod implements SchoolObject{
    
    String subject;
    String subject_code;
    
    String teacher;
    String teacher_id;
    
    String start_time;
    String end_time;
    
    String venue;
    
    
    
    public ClassPeriod(){
        this.subject = "NONE";
        this.subject_code = "NONE";
        
        this.teacher = "NONE";
        this.teacher_id = "NONE";
        
        this.start_time = "NONE";
        this.end_time = "NONE";
        this.venue = "NONE";
    }
    
    public ClassPeriod(Subject subj,Teacher teacher,String s_time,String e_time,String venue){
        this.subject = subj.getTitle();
        this.subject_code = subj.getCode();
        
        this.teacher = teacher.getName();
        this.teacher_id = teacher.getID();
        
        this.start_time = s_time;
        this.end_time = e_time;
        
        this.venue = venue;
    }
    
    public ClassPeriod(String subject,String code,String teacher,String t_id,String start_time,String stop_time,String venue){
        this.subject = subject;
        this.subject_code = code;
        
        this.teacher = teacher;
        this.teacher_id = t_id;
        
        this.start_time = start_time;
        this.end_time = stop_time;
        this.venue = venue;
    }
    
    public ClassPeriod(String csv){
        String[]dta = csv.split(",");
        
        this.subject = dta[0];
        this.subject_code = dta[1];
        
        this.teacher = dta[2];
        this.teacher_id = dta[3];
        
        String[]p_time = dta[4].split("-");
        this.start_time = p_time[0];
        this.end_time = p_time[1];
        
        this.venue = dta[5];
    }
    
    
    //////////////////////////////////////////////////////////////////////
    public void setSubject(String subj){
        this.subject = subj;
    }
    
    public void setSubjectCode(String code){
        this.subject_code = code;
    }
    
    public void setTeacherName(String nme){
        this.teacher = nme;
    }
    
    public void setTeacherID(String id){
        this.teacher_id = id;
    }
    
    public void setVenue(String subj){
        this.venue = subj;
    }
    
    public void setStartTime(String start){
        this.start_time = start;
    }
    
    public void setEndTime(String end){
        this.end_time = end;
    }
    
    ///////////////////////////////////////////////////////////////
    
    public String getSubject (){
        return this.subject_code ;
    }
    
    public String getSubjectCode (){
        return this.subject_code ;
    }
    
    public String getTeacherName (){
        return this.teacher ;
    }
    
    public String getTeacherID (){
        return this.teacher_id ;
    }
    
    public String getVenue (){
        return this.venue ;
    }
    
    public String getStartTime (){
        return this.start_time ;
    }
    
    public String getEndTime (){
        return this.end_time ;
    }
    
    
    
    public String getPeriodTime(){
        return String.format("%s-%s",this.start_time,this.end_time);
    }
    
    public void setObject(String csv){
        String[]dta = csv.split(",");
        
        this.subject = dta[0];
        this.subject_code = dta[1];
        
        this.teacher = dta[2];
        this.teacher_id = dta[3];
        
        String[]p_time = dta[4].split("-");
        this.start_time = p_time[0];
        this.end_time = p_time[1];
        
        this.venue = dta[5];
    }
    
    public SchoolObject getObject(){
        return new ClassPeriod();
    }
    
    public String infoString(){
        return String.format("\n\t [CLASS PERIOD DETAILS]\n•) COURSE: %s\n•) CODE: %s\n•) TEACHER: %s\n•) TIME: %s\n•) VENUE: %s\n",this.subject,this.subject_code,this.teacher,this.getPeriodTime());
    }
    
    public String toString(){
        return String.format("%s,%s,%s,%s,%s",this.subject,this.subject_code,this.teacher,this.teacher_id,this.getPeriodTime(),this.venue);
    }
    
}


















