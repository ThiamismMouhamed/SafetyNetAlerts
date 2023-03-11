package com.example.safetynetalerts.service.medicalrecord;

import com.example.safetynetalerts.exeptions.BadRequestExeptions;
import com.example.safetynetalerts.model.Id;
import com.example.safetynetalerts.model.MedicalRecord;
import com.example.safetynetalerts.service.dataStorage.DataStorageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Log4j2
public class MedicalRecordService implements MedicalRecordIn {
    private DataStorageService dataStorageService;

    @Autowired
    public MedicalRecordService(DataStorageService dataStorageService) {
        this.dataStorageService = dataStorageService;
    }

    public MedicalRecordService(){

    }


    @Override
    public void addMedicalRecord(MedicalRecord medicalRecord) {
        log.info("Add a medical record" + medicalRecord);

        Optional<MedicalRecord> optionalMedicalrecord =
                dataStorageService
                        .getMedicalRecordById(medicalRecord.getId());

        if(optionalMedicalrecord.isPresent()) {
            log.error("Medical present cannot be created, existing already");
            throw new BadRequestExeptions("This medical record exist already");
        }

        dataStorageService
                .getMedicalrecords()
                .add(medicalRecord);
        log.info("Medicalrecord created");

    }

    @Override
    public void updateMedicalRecord(MedicalRecord medicalRecord) {
        log.info("Update a medical record" + medicalRecord);

        Optional<MedicalRecord> optionalMedicalRecord =
                dataStorageService
                        .getMedicalRecordById(medicalRecord.getId());

        if(optionalMedicalRecord.isPresent()) {
            int indexOfMedicalRecord = dataStorageService.getMedicalrecords().indexOf(optionalMedicalRecord.get());

            dataStorageService
                    .getMedicalrecords()
                    .set(indexOfMedicalRecord, medicalRecord);
            log.info("Medicalrecord updated");

        } else {
            log.error("Cannot update, medicalrecord doesn't exist");
            throw new BadRequestExeptions("This medical record doesn't exist");
        }

    }

    @Override
    public void deleteMedicalRecord(String firstName, String lastName) {
        log.info("Delete a medical record " + firstName + " " + lastName);

        Optional<MedicalRecord> optionalMedicalRecord =
                dataStorageService
                        .getMedicalRecordById(new Id(firstName, lastName));

        if(optionalMedicalRecord.isPresent()) {

            dataStorageService
                    .getMedicalrecords()
                    .remove(optionalMedicalRecord.get());
            log.info("Medicalrecord deleted");

        } else {
            log.error("Cannot delete, medicalrecord doesn't exist");
            throw new BadRequestExeptions("This medical record doesn't exist");
        }

    }

    @Override
    public Optional<MedicalRecord> getMedicalRecordByPersonneId(Id id) {
        log.debug("get medicalrecord using person id");
        return dataStorageService
                .getMedicalrecords()
                .stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
    }


}
