package SchoolManager;

import java.util.*;

public class Validator {
	
	private RepoManager rpm;
	
	public Validator() {
		
		System.out.println("\n\t\t [ACCESS VALIDATOR INITIALIZED] \n");
	}

    public void setRepoManager(RepoManager rpm){
        this.rpm = rpm;
    }
    
	
	public Staff ValidateAdminAccess(String sys_pass,String p_auth) {
	    Staff pm = null;
	    
	    return pm;
	}
    
}
