package SchoolManager;



public class StudentFee{
    
    private String student;
    private String student_id;
    
    private double total_fee;
    private double amount_paid;
    private double amount_owed;
    
    
    public StudentFee(){
        this.student = "John Doe";
        this.student_id = "NONE";
    
        this.total_fee = 0.0;
        this.amount_paid = 0.0;
        this.amount_owed = 0.0;
    }
    
    public StudentFee(String student, String id, double tuition, double paid){
        this.student = student;
        this.student_id = id;
    
        this.total_fee = tuition;
        this.amount_paid = paid;
        this.amount_owed = (tuition - paid);
    }
    
    public StudentFee(String csv){
        String[]dta = csv.split(",");
        
        this.student = dta[0];
        this.student_id = dta[1];
    
        this.total_fee = Double.parseDouble(dta[2]);
        this.amount_paid = Double.parseDouble(dta[3]);
        this.amount_owed = Double.parseDouble(dta[4]);
    }
    
    public void setObject(String csv){
        String[]dta = csv.split(",");
        
        this.student = dta[0];
        this.student_id = dta[1];
    
        this.total_fee = Double.parseDouble(dta[2]);
        this.amount_paid = Double.parseDouble(dta[3]);
        this.amount_owed = Double.parseDouble(dta[4]);
    }
    
    public void setStudent(Student std){
        this.student = std.getName();
        this.student_id = std.getID();
    }
    
    public void setStudent(String stud){
        this.student = stud;
    }
    
    public void setStudentID(String sid){
        this.student_id = sid;
    }
    
    public void setTotalFee(double fee){
        this.total_fee = fee;
    }
    
    public void setPaidFee(double fee){
        this.amount_paid = fee;
        this.amount_owed = (total_fee - fee);
    }
    
    public String getStudent(){
        return this.student;
    }
    
    public String getStudentID(){
        return this.student_id;
    }
    
    public double getFeeTotal(){
        return this.total_fee;	
    }
    
    public double getFeePaid(){
        return this.amount_paid;
    }
    
    public double getFeeOwed(){
        return this.amount_owed;
    }
    
    public void addAmountPaid(double amnt){
        this.amount_paid += amnt;
        this.amount_owed = (total_fee - this.amount_paid);
    }
    
    public String infoString(){
        return String.format("Student: %s\nTotal Fees: %s\nFees Paid: %s\nFees Owing: %s",this.student,this.total_fee,this.amount_paid,this.amount_owed);
    }
    
    @Override
    public String toString(){
        return String.format("%s,%s,%s,%s,%s",this.student_id,this.student,this.total_fee,this.amount_paid,this.amount_owed);
    }
    
    
    
}