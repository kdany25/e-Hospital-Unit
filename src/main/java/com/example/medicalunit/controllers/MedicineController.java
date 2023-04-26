package com.example.medicalunit.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.medicalunit.dtos.MedecineDto;
import com.example.medicalunit.dtos.PatientMedecineDto;
import com.example.medicalunit.dtos.PatientMedicalRecordDTO;
import com.example.medicalunit.model.Patient;
import com.example.medicalunit.model.Pharmacist;
import com.example.medicalunit.model.Physician;
import com.example.medicalunit.services.MedicalDataService;

import java.util.List;

@RestController
public class MedicineController {

    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }

    /**
     * Patient APis.
     */

    // Get all physicisians
    @GetMapping("/physicians")
    public ResponseEntity<List<Physician>> getAllPhysicians() {
        List<Physician> physicians = MedicalDataService.getAllPhysicians();

        return new ResponseEntity<>(physicians, HttpStatus.OK);
    }

    // Get all pharmacists
    @GetMapping("/pharmacists")
    public ResponseEntity<List<Pharmacist>> getAllPharmacists() {
        List<Pharmacist> physicians = MedicalDataService.getAllPharmacists();

        return new ResponseEntity<>(physicians, HttpStatus.OK);
    }

    // Grant access to a doctor
    @PostMapping("/medicalRecords/assignDoctor")
    public ResponseEntity<String> grantAccessToDoctor(
            @RequestBody PatientMedicalRecordDTO patientMedicalRecord) {

        Patient patient = MedicalDataService.getPatientById(patientMedicalRecord.getPatientId());
        Physician physician = MedicalDataService.getPhysicianById(patientMedicalRecord.getPhysicianId());

        if (patient == null)
            return new ResponseEntity<>("Invalid patient id", HttpStatus.NOT_FOUND);
        if (physician == null)
            return new ResponseEntity<>("Invalid physician id", HttpStatus.NOT_FOUND);

        // find record
        PatientMedicalRecordDTO foundPatientMedicalRecord = MedicalDataService
                .getPatientMedicalRecordById(patientMedicalRecord.getId());
        if (foundPatientMedicalRecord == null)
            return new ResponseEntity<>("Invalid medical record id", HttpStatus.OK);

        // Grant access
        foundPatientMedicalRecord.setPhysicianId(physician.getId());

        MedicalDataService.updatePatientMedicalRecordById(foundPatientMedicalRecord);

        return new ResponseEntity<>("Granted access successfully", HttpStatus.OK);
    }

    // Grant access to a pharmacist

    @PostMapping("/medicalRecords/assignPharmacist")
    public ResponseEntity<String> grantAccessToPharmacy(
            @RequestBody PatientMedicalRecordDTO patientMedicalRecord) {

        Patient patient = MedicalDataService.getPatientById(patientMedicalRecord.getPatientId());
        Pharmacist pharmacist = MedicalDataService.getPharmacistById(patientMedicalRecord.getPharmacistId());

        if (patient == null)
            return new ResponseEntity<>("Invalid patient id", HttpStatus.NOT_FOUND);
        if (pharmacist == null)
            return new ResponseEntity<>("Invalid pharmacist id", HttpStatus.NOT_FOUND);

        // find record
        PatientMedicalRecordDTO foundPatientMedicalRecord = MedicalDataService
                .getPatientMedicalRecordById(patientMedicalRecord.getId());
        if (foundPatientMedicalRecord == null)
            return new ResponseEntity<>("Invalid medical record id", HttpStatus.OK);

        // Grant access
        foundPatientMedicalRecord.setPharmacistId(pharmacist.getId());

        MedicalDataService.updatePatientMedicalRecordById(foundPatientMedicalRecord);

        return new ResponseEntity<>("Granted access successfully", HttpStatus.OK);
    }

    // Download prescription
    @GetMapping("/downloadPrescription")
    public ResponseEntity<?> getMedsPerRecord(
            @RequestParam(value = "recordId", required = false) String recordId) {
        return MedicalDataService.getMedsPerPatientRecords(recordId);
    }

    /**
     * Physician APis.
     */

    // consultation
    @PostMapping("/addConsultation")
    public ResponseEntity<String> provideConsultation(
            @RequestBody PatientMedicalRecordDTO patientMedicalRecord) {

        if (patientMedicalRecord.getConsultation() == null)
            return new ResponseEntity<>("Provide consultation details", HttpStatus.BAD_REQUEST);

        Physician physician = MedicalDataService.getPhysicianById(patientMedicalRecord.getPhysicianId());

        if (physician == null)
            return new ResponseEntity<>("Invalid physician id", HttpStatus.BAD_REQUEST);

        // find record
        PatientMedicalRecordDTO foundPatientMedicalRecord = MedicalDataService
                .getPatientMedicalRecordById(patientMedicalRecord.getId());

        if (foundPatientMedicalRecord == null)
            return new ResponseEntity<>("Invalid medical record id", HttpStatus.BAD_REQUEST);

        if (!physician.getId().equals(foundPatientMedicalRecord.getPhysicianId()))
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);

        // Grant access
        foundPatientMedicalRecord.setConsultation(patientMedicalRecord.getConsultation());

        MedicalDataService.updatePatientMedicalRecordById(foundPatientMedicalRecord);

        return new ResponseEntity<>("Added consultation successfully", HttpStatus.OK);
    }

    /**
     * Pharmacist APis.
     */
    @PostMapping("/uploadMedecine")
    public ResponseEntity<String> addMed(
            @RequestBody MedecineDto medDto) {
        String result = MedicalDataService.addMedecine(medDto);
        if (result != null) {
            return new ResponseEntity<>("Medication added", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Medication add failed", HttpStatus.BAD_REQUEST);
        }
    }

    // Get all medecine
    @GetMapping("/getMedecines")
    public ResponseEntity<?> getMeds() {
        List<MedecineDto> result = MedicalDataService.getMedecines();

        if (result.iterator().hasNext()) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Medication fetch failed", HttpStatus.BAD_REQUEST);
        }
    }


    // Prescribe medecine
    @PostMapping("/prescribeMedecine")
    public ResponseEntity<String> prescribeMed(
            @RequestBody PatientMedecineDto patientMedDto) {
        PatientMedicalRecordDTO patientMedicalRecord = MedicalDataService
                .getPatientMedicalRecordById(patientMedDto.getRecordId());

        Pharmacist pharmacist = MedicalDataService.getPharmacistById(patientMedicalRecord.getPharmacistId());

        if (pharmacist == null)
            return new ResponseEntity<>("Invalid pharmacist id", HttpStatus.BAD_REQUEST);

        // find record
        PatientMedicalRecordDTO foundPatientMedicalRecord = MedicalDataService
                .getPatientMedicalRecordById(patientMedicalRecord.getId());

        if (foundPatientMedicalRecord == null)
            return new ResponseEntity<>("Invalid medical record id", HttpStatus.BAD_REQUEST);

        if (!pharmacist.getId().equals(foundPatientMedicalRecord.getPharmacistId()))
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);

        // add medicine
        List<MedecineDto> res = foundPatientMedicalRecord.getMedicines();
        res.add(new MedecineDto(patientMedDto.getMedName(), patientMedDto.getMedPrice(),
                patientMedDto.getMedExpiration()));
        foundPatientMedicalRecord.setMedicines(res);

        MedicalDataService.updatePatientMedicalRecordById(foundPatientMedicalRecord);

        return new ResponseEntity<>("Added medicine successfully", HttpStatus.OK);
    }

    // get all medical records
    @GetMapping("/medicalRecords")
    public ResponseEntity<List<PatientMedicalRecordDTO>> getAllMedicalRecords(
            @RequestParam(value = "physicianId", required = false) String physicianId,
            @RequestParam(value = "pharmacistId", required = false) String pharmacistId,
            @RequestParam(value = "patientId", required = false) String patientId) {

        if (physicianId != null) {
            List<PatientMedicalRecordDTO> patientMedicalReport = MedicalDataService
                    .getAllPatientsMedicalRecordsByPhysicianId(physicianId);
            return new ResponseEntity<>(patientMedicalReport, HttpStatus.OK);
        }

        if (pharmacistId != null) {
            List<PatientMedicalRecordDTO> patientMedicalReport = MedicalDataService
                    .getAllPatientsMedicalRecordsByPharmacistId(pharmacistId);
            return new ResponseEntity<>(patientMedicalReport, HttpStatus.OK);
        }

        if (patientId != null) {
            List<PatientMedicalRecordDTO> patientMedicalReport = MedicalDataService
                    .getAllPatientsMedicalRecordsByPatientId(patientId);
            return new ResponseEntity<>(patientMedicalReport, HttpStatus.OK);
        }

        List<PatientMedicalRecordDTO> patientMedicalRecords = MedicalDataService.getAllPatientsMedicalRecords();

        return new ResponseEntity<>(patientMedicalRecords, HttpStatus.OK);
    }

    @PostMapping("/addMedicine")
    public ResponseEntity<String> addMedicine(
            @RequestBody PatientMedicalRecordDTO patientMedicalRecord) {

        if (patientMedicalRecord.getMedicines() == null)
            return new ResponseEntity<>("Provide medicine", HttpStatus.BAD_REQUEST);

        Pharmacist pharmacist = MedicalDataService.getPharmacistById(patientMedicalRecord.getPharmacistId());

        if (pharmacist == null)
            return new ResponseEntity<>("Invalid pharmacist id", HttpStatus.BAD_REQUEST);

        // find record
        PatientMedicalRecordDTO foundPatientMedicalRecord = MedicalDataService
                .getPatientMedicalRecordById(patientMedicalRecord.getId());

        if (foundPatientMedicalRecord == null)
            return new ResponseEntity<>("Invalid medical record id", HttpStatus.BAD_REQUEST);

        if (!pharmacist.getId().equals(foundPatientMedicalRecord.getPharmacistId()))
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);

        // add medicine
        foundPatientMedicalRecord.setMedicines(patientMedicalRecord.getMedicines());

        MedicalDataService.updatePatientMedicalRecordById(foundPatientMedicalRecord);

        return new ResponseEntity<>("Added medicine successfully", HttpStatus.OK);
    }

}