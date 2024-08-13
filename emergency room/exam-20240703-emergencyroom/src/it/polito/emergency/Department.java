package it.polito.emergency;

import java.util.HashSet;
import java.util.TreeSet;

public class Department {
    String name;
    int max;
    int current;
    private HashSet<String> Patients = new HashSet<>();
    public Department(String name,int max){
        this.name = name;
        this.max = max;
        
    }
    public String getName(){
        return this.name;
    }
    public void addPatient(String fiscalcode){
        Patients.add(fiscalcode);
    }
    public int getsize(){
        return this.Patients.size();
    }
    public HashSet<String> getList(){
        return Patients;
    }
}
