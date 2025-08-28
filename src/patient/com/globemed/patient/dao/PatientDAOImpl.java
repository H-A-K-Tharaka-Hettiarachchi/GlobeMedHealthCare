package com.globemed.patient.dao;

import com.globemed.db.DatabaseConnection;
import com.globemed.patient.model.Patient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JDBC implementation of the PatientDAO interface.
 */
public class PatientDAOImpl implements PatientDAO {

    private static final Logger logger = LoggerFactory.getLogger(PatientDAOImpl.class);

    private static final String SELECT_PATIENT_BY_ID = "SELECT * FROM patient WHERE id = ?";
    private static final String SELECT_ALL_PATIENTS = "SELECT * FROM patient";
    private static final String INSERT_PATIENT = "INSERT INTO patient (fname, lname, nic, email, mobile, date_of_birth, gender_id, address, blood_type_id, created_at, encrypted_medical_history) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_PATIENT = "UPDATE patient SET fname = ?, lname = ?, nic = ?, email = ?, mobile = ?, date_of_birth = ?, gender_id = ?, address = ?, blood_type_id = ?, encrypted_medical_history = ? WHERE id = ?";
    private static final String DELETE_PATIENT_BY_ID = "DELETE FROM patient WHERE id = ?";

    @Override
    public Optional<Patient> findById(int id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_PATIENT_BY_ID)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToPatient(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error finding patient by id: {}", id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> patients = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_PATIENTS);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                patients.add(mapRowToPatient(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding all patients", e);
        }
        return patients;
    }

    @Override
    public boolean save(Patient patient) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_PATIENT, Statement.RETURN_GENERATED_KEYS)) {
            setCommonPatientStatementParameters(patient, pstmt);
            // Set created_at for new records
            pstmt.setTimestamp(10, Timestamp.valueOf(java.time.LocalDateTime.now()));
            pstmt.setBytes(11, patient.getEncryptedMedicalHistory());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        patient.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error saving patient: {}", patient, e);
        }
        return false;
    }

    @Override
    public boolean update(Patient patient) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_PATIENT)) {
            setCommonPatientStatementParameters(patient, pstmt);
            // Set encrypted_medical_history and the ID for the WHERE clause
            pstmt.setBytes(10, patient.getEncryptedMedicalHistory());
            pstmt.setInt(11, patient.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error updating patient: {}", patient, e);
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_PATIENT_BY_ID)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error deleting patient by id: {}", id, e);
        }
        return false;
    }

    private Patient mapRowToPatient(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setId(rs.getInt("id"));
        patient.setFname(rs.getString("fname"));
        patient.setLname(rs.getString("lname"));
        patient.setNic(rs.getString("nic"));
        patient.setEmail(rs.getString("email"));
        patient.setMobile(rs.getString("mobile"));
        patient.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
        patient.setGenderId(rs.getInt("gender_id"));
        patient.setAddress(rs.getString("address"));
        patient.setBloodTypeId(rs.getInt("blood_type_id"));
        patient.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        patient.setEncryptedMedicalHistory(rs.getBytes("encrypted_medical_history"));
        return patient;
    }

    private void setCommonPatientStatementParameters(Patient patient, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, patient.getFname());
        pstmt.setString(2, patient.getLname());
        pstmt.setString(3, patient.getNic());
        pstmt.setString(4, patient.getEmail());
        pstmt.setString(5, patient.getMobile());
        pstmt.setDate(6, Date.valueOf(patient.getDateOfBirth()));
        pstmt.setInt(7, patient.getGenderId());
        pstmt.setString(8, patient.getAddress());
        pstmt.setInt(9, patient.getBloodTypeId());
    }
}
