package SchoolManager;

import java.util.*;
import java.lang.StringBuilder;
import java.io.*;

public class Grade {
    
    private String grade;
    private List<String> subjects;
    
    public Grade(String grade){
        this.grade = grade;
    }
    
    public Grade(String grade, String ... subjects_code){
        this.grade = grade;
        this.subjects = new ArrayList();
        for(String crs : subjects_code){
            this.subjects.add(crs);
        }
    }
    
    public void addGradeSubject(String sub){
        this.subjects.add(sub);
    }
    
    
    public void storeGradeData(){
        
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("grade_file.txt",false));
        PrintWriter pw = new PrintWriter(bw)){
            pw.println(toString());
            pw.flush(); pw.close();
        }catch(IOException ioe){ioe.printStackTrace();}
    }
    
    public void loadGradeData(){
        
        try(BufferedReader br = new BufferedReader(new FileReader("grade_file.txt"))){
            
        }catch(IOException e){e.printStackTrace();}
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s,",this.grade));
        for(String sbj: this.subjects){
            sb.append(String.format("%s,",sbj));
        }return sb.toString().substring(0,sb.length()-1);
    }
    
}