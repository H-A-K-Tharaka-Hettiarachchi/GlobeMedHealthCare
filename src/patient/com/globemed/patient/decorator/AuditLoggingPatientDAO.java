package com.globemed.patient.decorator;

import com.globemed.patient.dao.PatientDAO;
import com.globemed.patient.model.Patient;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Decorator for PatientDAO that adds audit logging to each operation.
 * This is an implementation of the Decorator pattern.
 */
public class AuditLoggingPatientDAO implements PatientDAO {

    private static final Logger logger = LoggerFactory.getLogger(AuditLoggingPatientDAO.class);

    private final PatientDAO wrappedDao;
    // In a real application, you would get the current user from a security context.
    private final String currentUser = "system"; // Placeholder

    public AuditLoggingPatientDAO(PatientDAO wrappedDao) {
        this.wrappedDao = wrappedDao;
    }

    @Override
    public Optional<Patient> findById(int id) {
        logger.info("[AUDIT] User '{}' is attempting to find patient by id: {}", currentUser, id);
        Optional<Patient> patient = wrappedDao.findById(id);
        if (patient.isPresent()) {
            logger.info("[AUDIT] User '{}' successfully found patient: {}", currentUser, patient.get());
        } else {
            logger.warn("[AUDIT] User '{}' failed to find patient with id: {}", currentUser, id);
        }
        return patient;
    }

    @Override
    public List<Patient> findAll() {
        logger.info("[AUDIT] User '{}' is attempting to find all patients.", currentUser);
        List<Patient> patients = wrappedDao.findAll();
        logger.info("[AUDIT] User '{}' found {} patients.", currentUser, patients.size());
        return patients;
    }

    @Override
    public boolean save(Patient patient) {
        logger.info("[AUDIT] User '{}' is attempting to save a new patient: {}", currentUser, patient);
        boolean result = wrappedDao.save(patient);
        if (result) {
            logger.info("[AUDIT] User '{}' successfully saved new patient with generated id: {}", currentUser, patient.getId());
        } else {
            logger.error("[AUDIT] User '{}' failed to save patient: {}", currentUser, patient);
        }
        return result;
    }

    @Override
    public boolean update(Patient patient) {
        logger.info("[AUDIT] User '{}' is attempting to update patient with id {}: {}", currentUser, patient.getId(), patient);
        boolean result = wrappedDao.update(patient);
        if (result) {
            logger.info("[AUDIT] User '{}' successfully updated patient with id: {}", currentUser, patient.getId());
        } else {
            logger.error("[AUDIT] User '{}' failed to update patient with id: {}", currentUser, patient.getId());
        }
        return result;
    }

    @Override
    public boolean deleteById(int id) {
        logger.info("[AUDIT] User '{}' is attempting to delete patient by id: {}", currentUser, id);
        boolean result = wrappedDao.deleteById(id);
        if (result) {
            logger.info("[AUDIT] User '{}' successfully deleted patient with id: {}", currentUser, id);
        } else {
            logger.error("[AUDIT] User '{}' failed to delete patient with id: {}", currentUser, id);
        }
        return result;
    }
}
