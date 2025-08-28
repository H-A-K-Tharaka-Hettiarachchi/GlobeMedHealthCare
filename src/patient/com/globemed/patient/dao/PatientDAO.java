package com.globemed.patient.dao;

import com.globemed.patient.model.Patient;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for Patient entities.
 * This interface defines the standard operations to be performed on a Patient model.
 */
public interface PatientDAO {

    /**
     * Finds a patient by their ID.
     *
     * @param id The ID of the patient to find.
     * @return an Optional containing the patient if found, otherwise empty.
     */
    Optional<Patient> findById(int id);

    /**
     * Retrieves all patients from the database.
     *
     * @return a list of all patients.
     */
    List<Patient> findAll();

    /**
     * Saves a new patient to the database.
     * The implementation should handle setting the generated ID on the patient object.
     *
     * @param patient The patient object to save.
     * @return true if the patient was saved successfully, false otherwise.
     */
    boolean save(Patient patient);

    /**
     * Updates an existing patient in the database.
     *
     * @param patient The patient object with updated information.
     * @return true if the patient was updated successfully, false otherwise.
     */
    boolean update(Patient patient);

    /**
     * Deletes a patient from the database by their ID.
     *
     * @param id The ID of the patient to delete.
     * @return true if the patient was deleted successfully, false otherwise.
     */
    boolean deleteById(int id);
}
