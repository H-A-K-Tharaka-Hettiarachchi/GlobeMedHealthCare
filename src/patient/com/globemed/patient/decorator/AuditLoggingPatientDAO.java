package com.globemed.patient.decorator;

import com.globemed.patient.dao.PatientDAO;
import com.globemed.patient.model.Patient;
import java.util.List;
import java.util.Optional;

/**
 * Decorator for PatientDAO that adds audit logging to each operation.
 * This is an implementation of the Decorator pattern.
 */
public class AuditLoggingPatientDAO implements PatientDAO {

    private final PatientDAO wrappedDao;
    // In a real application, you would get the current user from a security context.
    private final String currentUser = "system"; // Placeholder

    public AuditLoggingPatientDAO(PatientDAO wrappedDao) {
        this.wrappedDao = wrappedDao;
    }

    @Override
    public Optional<Patient> findById(int id) {
        System.out.println(String.format("[AUDIT] User '%s' is attempting to find patient by id: %d", currentUser, id));
        Optional<Patient> patient = wrappedDao.findById(id);
        if (patient.isPresent()) {
            System.out.println(String.format("[AUDIT] User '%s' successfully found patient: %s", currentUser, patient.get()));
        } else {
            System.out.println(String.format("[AUDIT] User '%s' failed to find patient with id: %d", currentUser, id));
        }
        return patient;
    }

    @Override
    public List<Patient> findAll() {
        System.out.println(String.format("[AUDIT] User '%s' is attempting to find all patients.", currentUser));
        List<Patient> patients = wrappedDao.findAll();
        System.out.println(String.format("[AUDIT] User '%s' found %d patients.", currentUser, patients.size()));
        return patients;
    }

    @Override
    public boolean save(Patient patient) {
        System.out.println(String.format("[AUDIT] User '%s' is attempting to save a new patient: %s", currentUser, patient));
        boolean result = wrappedDao.save(patient);
        if (result) {
            System.out.println(String.format("[AUDIT] User '%s' successfully saved new patient with generated id: %d", currentUser, patient.getId()));
        } else {
            System.err.println(String.format("[AUDIT] User '%s' failed to save patient: %s", currentUser, patient));
        }
        return result;
    }

    @Override
    public boolean update(Patient patient) {
        System.out.println(String.format("[AUDIT] User '%s' is attempting to update patient with id %d: %s", currentUser, patient.getId(), patient));
        boolean result = wrappedDao.update(patient);
        if (result) {
            System.out.println(String.format("[AUDIT] User '%s' successfully updated patient with id: %d", currentUser, patient.getId()));
        } else {
            System.err.println(String.format("[AUDIT] User '%s' failed to update patient with id: %d", currentUser, patient.getId()));
        }
        return result;
    }

    @Override
    public boolean deleteById(int id) {
        System.out.println(String.format("[AUDIT] User '%s' is attempting to delete patient by id: %d", currentUser, id));
        boolean result = wrappedDao.deleteById(id);
        if (result) {
            System.out.println(String.format("[AUDIT] User '%s' successfully deleted patient with id: %d", currentUser, id));
        } else {
            System.err.println(String.format("[AUDIT] User '%s' failed to delete patient with id: %d", currentUser, id));
        }
        return result;
    }
}
