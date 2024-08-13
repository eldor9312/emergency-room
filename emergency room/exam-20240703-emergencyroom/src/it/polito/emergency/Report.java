package it.polito.emergency;

public class Report {
    public String SpecialistId;
    public String taxCode;
    public String visitDate;
    public String description;
    public int id = 0;
    public Report(String SpecialistId,String taxCode,String visitDate,String description){
        this.id = id;
        id += 1;       
        this.SpecialistId = SpecialistId;
        this.taxCode = taxCode;
        this.visitDate = visitDate;
        this.description = description;
    }
    public String getId() {
        return String.valueOf(id);
    }

    public String getProfessionalId() {
        return this.SpecialistId;
    }

    public String getFiscalCode() {
        return this.taxCode;
    }

    public String getDate() {
        return this.visitDate;
    }


    public String getDescription() {
        return this.description;
    }
}
