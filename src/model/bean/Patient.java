package model.bean;

public class Patient extends PatientInfo{

    private PatientRsa patientRsa;

    public PatientRsa getPatientRsa() {
        return patientRsa;
    }

    public void setPatientRsa(PatientRsa patientRsa) {
        this.patientRsa = patientRsa;
    }
}
