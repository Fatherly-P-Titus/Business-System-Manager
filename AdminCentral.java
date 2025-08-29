package SchoolManager;

import static java.lang.System.out;
import java.util.*;

	
public class AdminCentral {
    
    ADMIN admin;
    
    Crypter crypter;
    SystemFileOps sfo;
    Logger logger;
    
    AuthGenerator auth_generator;
    
    Validator validator;
    
    RepoManager rpm;
    StaffManager umv;
    StudentRepoManager srm;
    
    Scanner sc;
    
    
    public AdminCentral(){
        
        admin = new ADMIN();
        
        
        sc = new Scanner(System.in);
        
        this.crypter = admin.getSystemCrypter();
        this.logger = admin.getSystemLogger();

        this.sfo = admin.getSystemFileMech();
        
        this.auth_generator = admin.getSystemAuthGenerator();
        
        
        this.rpm = new RepoManager(this.crypter);
        umv = new StaffManager(rpm);
        
      /*  sfo.loadStaffRepositoryData();
        sfo.loadStaffAuthRepositoryData();
        sfo.loadAllLogs();*/
        
        out.println("\n\t [ADMIN CENTRAL INITIALIZED] \n");
        
    }
    
    
    public void StudentManagement(){
        //How to list entries
        srm = new StudentRepoManager(this.crypter);
        StudentManager manager = new StudentManager(srm);
        
        StudentFeeRepoManager sfrm = new StudentFeeRepoManager(this.crypter);
        StudentFeeManager sfm = new StudentFeeManager(sfrm);
        
        SubjectRepoManager sbrm = new SubjectRepoManager(this.crypter);
        
        StudentScoreRepoManager ssrm = new StudentScoreRepoManager(this.crypter);
        StudentScoreManager stdm = new StudentScoreManager(ssrm); 
        
        while(true){
        
        out.println("1) ENROLL NEW STUDENT\n2) EDIT STUDENT SCORE\n3) EDIT STUDENT FEE");
        
        String option = sc.nextLine();
        
        switch(option){
            case "1" :
            Student s = manager.createStudent();
            //Create Student Entry
            //Create Student Fee 
            sfrm.createNewStudentFeeEntry(s,0.0);
            //Create Student Scores
            ssrm.createNewStudentScoresEntry(s,sbrm.getSubjectsMapper());
            break;
            case "2" :
            //Search / select for Student id/name
            StudentScore sc = stdm.searchStudentScore();
            //Edit Score
            sc = stdm.editStudentScore(sc);
            break;
            case "3":
            //Search // Select student id
            StudentFee sf = sfm.searchStudentFee();
            //Edit sf
            sfm.editStudentFee(sf);
            break;
            
        }
        }
        
    }
    
    public void ACCESS_CONTROL() {
    	out.print("\n\t\t [SELECT ACCESS LEVEL] \n1) ADMIN ACCESS \n2) MANAGER \n3) USER\n\t => ");
    
    	 sc = new Scanner(System.in);
    	
    	String access_option = sc.nextLine();
    	String sys_pass = "";
    	String p_auth = "";
    	
    	Staff p = null;
    	
    	switch(access_option) {
    	case "1" : 
    		out.printf("\n\t PROVIDE SYSTEM PASSCODE:\n\t => ");
    		sys_pass = sc.nextLine();
    		out.println();
    		out.printf("\t PROVIDE AUTHORIZATION CODE:\n\t => ");
    		p_auth = sc.nextLine();
    		out.println();
    		
    		p = this.validator.ValidateAdminAccess(sys_pass, p_auth);
    		break;
    	case "2" :
    		out.printf("\n\t PROVIDE AUTHORIZATION CODE:\n\t => ");
    		p_auth = sc.nextLine();
    		out.println();
    		break;
    	case "3" :
    		out.printf("\n\t PROVIDE STAFF ID:\n\t => ");
    		p_auth = sc.nextLine();
    		out.println();
    		break;
    	}
    	
    	if(p == null) {
    		out.println("\n\t\t [ADMIN ACCESS VALIDATION FAILED !] ");
    	}else {
    		this.show();
    	}
    	
    	sc = null;
    }
    
    public void show(){
        
        Scanner user_input = new Scanner(System.in);
        String u_level_option = "", 
        u_option = "", 
        umv_option = "";
        
        while(!Objects.equals(u_option.toLowerCase(),"e")){
            if(Objects.equals(u_option.toLowerCase(),"e") ){
               u_option = ""; 
               break; 
            }
            
            out.print("\n1) USER MANAGEMENT\n2) ACCESS CONTROL & SECURITY\n3) DATA MANAGEMENT\n4) REPORTS & LOGS\n\t => ");
            
            u_option = user_input.next();
            
            //out.println("REPO SIZE: "+this.rpm.getRepoSize());

            while(!umv_option.toLowerCase().equals("e")){
                if(Objects.equals(umv_option.toLowerCase(),"e")) {
                    umv_option = "";
                    break;
                }
                
        switch(u_option){
            case "1" : 
            
            out.print("\n\t\t [USER MANAGEMENT OPERATIONS]\n\n1) REGISTER STAFF\n2) EDIT STAFF\n3) DELETE STAFF\n4) ASSIGN ROLE/PERMISSION\n5) HIDE RECORD\n6) SEARCH FOR STAFF \n7) LIST RECORDS\n\t => ");
            umv_option = user_input.next();	
              
              switch(umv_option){
                  case "1" : umv.createStaff(); break;
                  case "2" : umv.editStaff(rpm.listSelectStaffRepo()); break;
                  case "3" : umv.deleteStaff(rpm.listSelectStaffRepo()); break;
                  case "4" : umv.assignStaffDesignation(rpm.listSelectStaffRepo()); break;
                  //case "5" : umv.hideStaff(rpm.listSelectStaffRepo()); break;
                  case "6" : umv.searchStaff(); break;
                  case "7" : umv.rpm.listRecords();
                  default : rpm.displayLogs(); break;
              }
            //this.rpm.storeSaveRepoUpdate();    
            break;
            case "2" :
              out.println("\n\t [ACCESS & SECURITY OPERATIONS]\n\n1) REGISTER STAFF\n2) EDIT STAFF\n3) DELETE STAFF\n4) ASSIGN ROLE/PERMISSION\n5) ENABLE / DISABLE ACCESS\n"); break;
            case "3" : 
               out.println("\n\t [DATA MANAGEMENT OPERATIONS]\n\n1) REGISTER STAFF\n2) EDIT STAFF\n3) DELETE STAFF\n4) ASSIGN ROLE/PERMISSION\n5) ENABLE / DISABLE ACCESS\n"); break;
            case "4" : 
               out.println("\n\t [REPORTS & LOGS OPERATIONS]\n\n1) DISPLAY ALL LOGS \n2) DISPLAY INFO LOGS\n3) DISPLAY WARNNING LOGS\n4) DISPLAY ERROR \n5) CLEAR LOGS  \n"); 
               
               umv_option = user_input.nextLine();
               switch(umv_option){
                   case "1" :
                    rpm.displayLogs();
                   
                   break;
                   case "2" :
                   
                   break;
                   case "3" :
                   
                   break;
                   case "4" :
                   
                   break;
                   case "5" :
                   
                   break;
                    
                }
            break;
            default : rpm.displayLogs(); break;
        }
             
            }
        
        }
        
        /*
        To Edit/Delete a personnel, we first list out all registered personnels,
        2) select the personnel we wish to perform our operation on
        */
    }
    
}