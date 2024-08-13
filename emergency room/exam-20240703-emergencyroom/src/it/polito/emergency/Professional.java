package it.polito.emergency;

import java.util.*;

public class Professional {
    private String ID;
    private String FirstName;
    private String LastName;
    private String specialization;
    private String period;
    private TreeSet<String> Patients = new TreeSet<>();
    public Professional(String ID,String FirstName,String LastName,String specialization ,String period){
        this.ID = ID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.specialization = specialization;
        this.period = period;
    }
    public String getId() {
        return this.ID;
    }

    public String getName() {
        return this.FirstName;
    }

    public String getSurname() {
        return this.LastName;
    }

    public String getSpecialization() {
        return this.specialization;
    }

    public String getPeriod() {
        return this.period;
    }
    
    public String from(){
        String result;
        result = period.substring(0, 10);
        return result;
    }

    public String to(){
        String result;
        result = period.substring(14);
        return result;
    }


    public String getWorkingHours() {
        return null;
    }

    public void addPatient(Patient Patient){
        Patients.add(Patient.taxCode);
    }

    public TreeSet<String> getList(){
        return Patients;
    }
}
