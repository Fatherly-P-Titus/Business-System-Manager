package SchoolManager;

import java.util.*;


//TO HANDLE THE FILE I/O OPERATIONS OF THE SYSTEM
public interface SystemFileOps {
    
    public String staff_ser_file = "";
    public String staff_data_file = "sys_staffs.txt";
    
    public String staff_auth_ser_file = "";
    public String staff_auth_data_file = "staffs_auth.txt";
    
    
    public String db_data_file = "";
    public String security_config_file = "";
    
    public String crypter_ser_file = "";
    
    public String keystore1_file = "";
    public String keystore2_file = "";
    
    //public String SYSTEM_PASS = "";
    public String system_sec_file = "ftp_security_mech.txt";
    
    public String sys_logs_file = "system_logs.txt"; //STORE INFO, WARNING, ERROR LOGS
    public String sys_reports_file = "";
    
    
    public String students_repo_file = "student_repo.txt";
    public String students_scores_file = "student_course_scores.txt";
    public String subjects_repo_file = "subject_repo.txt";
    public String expenses_repo_file = "expense_repo.txt";

    
    public void storeDataList(List<String>obj,String file);
    public List<String> loadDataList(String file);
    public List<String> loadRepositoryData(String file);
    
}