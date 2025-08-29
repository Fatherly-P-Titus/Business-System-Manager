package SchoolManager;

import java.util.*;

public interface Logger {
    
    public List<Log> recent_logs = new ArrayList<>();
    
    public void log(String msg);
    
    public void logINFO(String msg);
    
    public void logERROR(String err);
    
    public void logWARNING(String warn);
    
    public List<Log> getRecentLogs();
    
    public Stack<Log> getAllLogs();
    
    public void setAllLogs(Stack<Log> logs);
    
    public String report();
    
}