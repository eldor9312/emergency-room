package it.polito.emergency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.time.Period;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import it.polito.emergency.Patient.Status;

public class EmergencyApp {

    public enum PatientStatus {
        ADMITTED,
        DISCHARGED,
        HOSPITALIZED
    }
    
    /**
     * Add a professional working in the emergency room
     * 
     * @param id
     * @param name
     * @param surname
     * @param specialization
     * @param period
     * @param workingHours
     */
    public Map<String,Professional> Professionals = new HashMap<>();
    public Map<String,Department> Departments = new HashMap<>();
    public ArrayList<Report> Reports = new ArrayList<>();
    public Map<String,Patient> Patients = new HashMap<>();
    public ArrayList<String> Specializations = new ArrayList<>();
    public void addProfessional(String id, String name, String surname, String specialization, String period) {
        //TODO: to be implemented
        Professional Professional = new Professional(id, name, surname, specialization, period);
        Professionals.put(id, Professional);
    }

    /**
     * Retrieves a professional utilizing the ID.
     *
     * @param id The id of the professional.
     * @return A Professional.
     * @throws EmergencyException If no professional is found.
     */    
    public Professional getProfessionalById(String id) throws EmergencyException {
        if(!Professionals.containsKey(id)){throw new EmergencyException();}
        //TODO: to be implemented
        return Professionals.get(id);
    }

    /**
     * Retrieves the list of professional IDs by their specialization.
     *
     * @param specialization The specialization to search for among the professionals.
     * @return A list of professional IDs who match the given specialization.
     * @throws EmergencyException If no professionals are found with the specified specialization.
     */    
    public List<String> getSpecializations(){
        for (Professional x: Professionals.values()){
            Specializations.add(x.getSpecialization());
        }
        return Specializations;
    }

    public List<String> getProfessionals(String specialization) throws EmergencyException { 
        //TODO: to be implemented
        if (!getSpecializations().contains(specialization)){
        throw new EmergencyException();
        }
        ArrayList<String> result =new ArrayList<>();
        for (Professional x: Professionals.values()){
            if(x.getSpecialization()==specialization){
                result.add(x.getId());
            }
        }
        return result;
    }

    /**
     * Retrieves the list of professional IDs who are specialized and available during a given period.
     *
     * @param specialization The specialization to search for among the professionals.
     * @param period The period during which the professional should be available, formatted as "YYYY-MM-DD to YYYY-MM-DD".
     * @return A list of professional IDs who match the given specialization and are available during the period.
     * @throws EmergencyException If no professionals are found with the specified specialization and period.
     */    
    public HashSet<Professional> getAvailable(String period){
        HashSet <Professional> result = new HashSet<>();
        for (Professional x: Professionals.values()){
            if (period.compareTo(x.from()) >=0 && period.compareTo(x.to())<0){
                result.add(x);
            };
        }
        return result;
    }

    public List<String> getProfessionalsInService(String specialization, String period) throws EmergencyException {
        // if (!getAvailable().getProfessionals().contains(specialization)){
        //     throw new EmergencyException();
        //     }

        ArrayList <String> result = new ArrayList<>();
        for(Professional x: getAvailable(period)){
            if (x.getSpecialization().equals(specialization)){
                result.add(x.getId());
            }
        }
        if (result.isEmpty()){throw new EmergencyException();}
        return result;
    }

    /**
     * Adds a new department to the emergency system if it does not already exist.
     *
     * @param name The name of the department.
     * @param maxPatients The maximum number of patients that the department can handle.
     * @throws EmergencyException If the department already exists.
     */
    public void addDepartment(String name, int maxPatients) {
        //TODO: to be implemented
        Department department = new Department(name, maxPatients);
        Departments.put(name, department);
    }

    /**
     * Retrieves a list of all department names in the emergency system.
     *
     * @return A list containing the names of all registered departments.
     * @throws EmergencyException If no departments are found.
     */
    public List<String> getDepartments() throws EmergencyException {
        //TODO: to be implemented
        if(Departments.isEmpty()){throw new EmergencyException();}
        ArrayList<String> result =new ArrayList<>();
        for (Department x: Departments.values()){
                result.add(x.getName());
        }
        return result;
    }

    /**
     * Reads professional data from a CSV file and stores it in the application.
     * Each line of the CSV should contain a professional's ID, name, surname, specialization, period of availability, and working hours.
     * The expected format of each line is: matricola, nome, cognome, specializzazione, period, orari_lavoro
     * 
     * @param reader The reader used to read the CSV file. Must not be null.
     * @return The number of professionals successfully read and stored from the file.
     * @throws IOException If there is an error reading from the file or if the reader is null.
     */
    public int readFromFileProfessionals(Reader reader) throws IOException {
        //TODO: to be implemented
        if (reader == null) {throw new IOException("Reader cannot be null");}
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            int count = 0;

            bufferedReader.readLine(); // Skip header line
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    String surname = parts[2].trim();
                    String specialization = parts[3].trim();
                    String period = parts[4].trim();

                    Professionals.put(id,new Professional(id, name, surname, specialization, period));
                    count++;
                }
            }
            return count;
        }
    }
    

    /**
     * Reads department data from a CSV file and stores it in the application.
     * Each line of the CSV should contain a department's name and the maximum number of patients it can accommodate.
     * The expected format of each line is: nome_reparto, num_max
     * 
     * @param reader The reader used to read the CSV file. Must not be null.
     * @return The number of departments successfully read and stored from the file.
     * @throws IOException If there is an error reading from the file or if the reader is null.
     */    
    public int readFromFileDepartments(Reader reader) throws IOException {
        if (reader == null) {
            throw new IOException("Reader cannot be null");
        }

        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            int count = 0;

            bufferedReader.readLine(); // Skip header line
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String departmentName = parts[0].trim();
                    int maxPatients = Integer.parseInt(parts[1].trim());

                    Departments.put(departmentName,new Department(departmentName, maxPatients));
                    count++;
                }
            }
            return count;
        }
    }

    /**
     * Registers a new patient in the emergency system if they do not exist.
     * 
     * @param fiscalCode The fiscal code of the patient, used as a unique identifier.
     * @param name The first name of the patient.
     * @param surname The surname of the patient.
     * @param dateOfBirth The birth date of the patient.
     * @param reason The reason for the patient's visit.
     * @param dateTimeAccepted The date and time the patient was accepted into the emergency system.
     */
    public Patient addPatient(String fiscalCode, String name, String surname, String dateOfBirth, String reason, String dateTimeAccepted) {
        //TODO: to be implemented
        Patient patient = new Patient(fiscalCode, name, surname, dateOfBirth, reason, dateTimeAccepted);
        Patients.put(fiscalCode, patient);
        return patient;
    }

    /**
     * Retrieves a patient or patients based on a fiscal code or surname.
     *
     * @param identifier Either the fiscal code or the surname of the patient(s).
     * @return A single patient if a fiscal code is provided, or a list of patients if a surname is provided.
     *         Returns an empty collection if no match is found.
     */    
    public List<Patient> getPatient(String identifier) throws EmergencyException {
        //TODO: to be implemented
        ArrayList<Patient> result =new ArrayList<>();
        for (Patient x: Patients.values()){
                if (x.getFiscalCode().equals(identifier)){
                    result.add(x);
                    return result;
                } 
                else if(x.getSurname().equals(identifier)){
                    result.add(x);
                }
        }
        return result;
    }

    /**
     * Retrieves the fiscal codes of patients accepted on a specific date, 
     * sorted by acceptance time in descending order.
     *
     * @param date The date of acceptance to filter the patients by, expected in the format "yyyy-MM-dd".
     * @return A list of patient fiscal codes who were accepted on the given date, sorted from the most recent.
     *         Returns an empty list if no patients were accepted on that date.
     */
    public List<String> getPatientsByDate(String date) {
        //TODO: to be implemented
        ArrayList<Patient> PatientsList = new ArrayList<>();
        
        for(Patient x: Patients.values()){
            if (x.getDateTimeAccepted().equals(date)){
            PatientsList.add(x);
            }
        }
        List<Patient> SortedPatientsList = PatientsList.stream()
            .sorted(Comparator.comparing(Patient::getSurname).thenComparing(Patient::getName))
            .collect(Collectors.toList());
        List<String> result = new ArrayList<>();
        for(Patient y: SortedPatientsList){
            result.add(y.taxCode);
        }
            return result;
    }

    /**
     * Assigns a patient to a professional based on the required specialization and checks availability during the request period.
     *
     * @param fiscalCode The fiscal code of the patient.
     * @param specialization The required specialization of the professional.
     * @return The ID of the assigned professional.
     * @throws EmergencyException If the patient does not exist, if no professionals with the required specialization are found, or if none are available during the period of the request.
     */
    public String assignPatientToProfessional(String fiscalCode, String specialization) throws EmergencyException {
        //TODO: to be implemented
        if (Patients.get(fiscalCode) == null){throw new EmergencyException();}
        Patient x = Patients.get(fiscalCode);
        List<String> Profs = new ArrayList<>(); 
        Profs = getProfessionalsInService(specialization, x.getDateTimeAccepted());
        Professional Prof = Professionals.get(Profs.get(0));
        
        if (Profs.isEmpty()){throw new EmergencyException();} 
        Prof.addPatient(x);
        return Prof.getId();        
    }


    public Report saveReport(String professionalId, String fiscalCode, String date, String description) throws EmergencyException {
        //TODO: to be implemented
        if(Professionals.get(professionalId) == null){throw new EmergencyException();}
        Report report = new Report(professionalId, fiscalCode, date, description);
        Reports.add(report);
        return report;
    }

    /**
     * Either discharges a patient or hospitalizes them depending on the availability of space in the requested department.
     * 
     * @param fiscalCode The fiscal code of the patient to be discharged or hospitalized.
     * @param departmentName The name of the department to which the patient might be admitted.
     * @throws EmergencyException If the patient does not exist or if the department does not exist.
     */

    public void dischargeOrHospitalize(String fiscalCode, String departmentName) throws EmergencyException {
        //TODO: to be implemented
        if(!Departments.containsKey(departmentName)){throw new EmergencyException();}
        if(!Patients.containsKey(fiscalCode)){throw new EmergencyException();}
        if(Departments.get(departmentName).getsize() < Departments.get(departmentName).max){
            Patients.get(fiscalCode).setStatus(PatientStatus.HOSPITALIZED);
            Departments.get(departmentName).addPatient(fiscalCode);
        }
        else {
            Patients.get(fiscalCode).setStatus(PatientStatus.DISCHARGED); 
            Departments.get(departmentName).addPatient(fiscalCode);
        }
    }

    /**
     * Checks if a patient is currently hospitalized in any department.
     *
     * @param fiscalCode The fiscal code of the patient to verify.
     * @return 0 if the patient is currently hospitalized, -1 if not hospitalized or discharged.
     * @throws EmergencyException If no patient is found with the given fiscal code.
     */
    public int verifyPatient(String fiscalCode) throws EmergencyException{
        //TODO: to be implemented
        if(!Patients.containsKey(fiscalCode)){throw new EmergencyException();}
        if(Patients.get(fiscalCode).getStatus() == PatientStatus.HOSPITALIZED){
            return 1;
        }
        else if(Patients.get(fiscalCode).getStatus() == PatientStatus.DISCHARGED){
            return 0;
        }
        return -1;
    }

    /**
     * Returns the number of patients currently being managed in the emergency room.
     *
     * @return The total number of patients in the system.
     */    
    public int getNumberOfPatients() {
        //TODO: to be implemented
        int result = 0;
        for(Patient x: Patients.values()){
            if (x.getStatus().equals(PatientStatus.ADMITTED)){ result ++;}
        }
        return result;
    }

    /**
     * Returns the number of patients admitted on a specified date.
     *
     * @param dateString The date of interest provided as a String (format "yyyy-MM-dd").
     * @return The count of patients admitted on that date.
     */
    public int getNumberOfPatientsByDate(String date) {
        //TODO: to be implemented
        int result = 0;
        for(Patient x: Patients.values()){
            if(x.getDateTimeAccepted().equals(date)){
                result+=1;
            }    
        }
        return result;
    }

    public int getNumberOfPatientsHospitalizedByDepartment(String departmentName) throws EmergencyException {
        //TODO: to be implemented
        if (Departments.get(departmentName) == null){throw new EmergencyException();}
        return Departments.get(departmentName).getsize();

    }

    /**
     * Returns the number of patients who have been discharged from the emergency system.
     *
     * @return The count of discharged patients.
     */
    public int getNumberOfPatientsDischarged() {
        //TODO: to be implemented
        int result = 0;
        for (Patient x: Patients.values()){
            if(x.getStatus().equals(PatientStatus.DISCHARGED)){
                result++;
            }
        }
        return result;
    }

    /**
     * Returns the number of discharged patients who were treated by professionals of a specific specialization.
     *
     * @param specialization The specialization of the professionals to filter by.
     * @return The count of discharged patients treated by professionals of the given specialization.
     * @throws EmergencyException 
     */
    public int getNumberOfPatientsAssignedToProfessionalDischarged(String specialization) throws EmergencyException {
        //TODO: to be implemented
        int result = 0;
        for(String x: getProfessionals(specialization)){
            for(String y: Professionals.get(x).getList()){
                if(Patients.get(y).getStatus().equals(PatientStatus.DISCHARGED)){
                    result += 1;
                }
            }
        }
        return result;
    }
}
