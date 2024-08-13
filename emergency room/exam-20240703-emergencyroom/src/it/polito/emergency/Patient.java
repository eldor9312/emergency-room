package it.polito.emergency;

import it.polito.emergency.EmergencyApp.*;

public class Patient {

    String FirstName;
    String LastName;
    String dateOfBirth;
    String taxCode;
    String reason;
    String date;
    PatientStatus status;
    public Patient(String taxCode,String FirstName,String LastName,String dateOfBirth,String reason,String date){
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.dateOfBirth = dateOfBirth;
        this.taxCode = taxCode;
        this.reason = reason;
        this.date = date;
        this.status = PatientStatus.ADMITTED;

    }
    public enum Status{
        ADMITTED,HOSPITALIZED,DISCHARGED
    }

    public void setStatus(PatientStatus status) {
		this.status = status;
	}

    public String getFiscalCode() {
        return this.taxCode;
    }

    public String getName() {
        return this.FirstName;
    }

    public String getSurname() {
        return this.LastName;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public String getReason() {
        return this.reason;
    }

    public String getDateTimeAccepted() {
        return this.date;
    }

    public PatientStatus getStatus() {
        return this.status;
    }
}
