package SchoolManager;

import java.util.*;

class UpdaterTask implements Runnable{
    
    private SystemFileOps sfm;
    
    private volatile boolean quit_task = false;
    
    public UpdaterTask(SystemFileOps sfm){
        this.sfm = sfm;
    }
    
    @Override
    public void run(){
        while(!quit_task){
            /*sfm.saveStaffRepo();
            sfm.saveStaffAuthRepo();
            sfm.saveStudentsRepo();
            sfm.saveStudentScoresRepo();
            sfm.saveSubjectsRepo();
            sfm.saveExpensesRepo();*/
        }
    }
    
    public void quitTask(){
        this.quit_task = true;
    }
}




public class ControlCentral {
    
    SystemFileOps sfo;
    
    public ControlCentral(SystemFileOps sfo){
        this.sfo = sfo;
    }
    
    
    public void _INIT_(){
        UpdaterTask task = new UpdaterTask(this.sfo);
        Thread worker = new Thread(task);
    
        //worker.start();
        
        //task.requestQuit();
        
    }
    
}