package ru.netology.patient.service.medical;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MedicalServiceImplTest {
    PatientInfo patientInfo = new PatientInfo("0", "Alex", "Suvorov",
            LocalDate.of(1985, 1, 1),
            new HealthInfo(BigDecimal.valueOf(38.6), new BloodPressure(140, 90)));

    @AfterEach
    public void init_before_each() {
        System.out.println();
    }

    @Test
    public void checkBloodPressure_out_message() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById(patientInfo.getId()))
                .thenReturn(patientInfo);

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository,
                Mockito.spy(SendAlertServiceImpl.class));

        System.out.println("Давление не в норме:");
        medicalService.checkBloodPressure(patientInfo.getId(),
                new BloodPressure(120, 80));
        //давление в "норме"
        medicalService.checkBloodPressure(patientInfo.getId(),
                new BloodPressure(140, 90));
    }

    @Test
    public void checkTemperature_out_message() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById(patientInfo.getId()))
                .thenReturn(patientInfo);

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository,
                Mockito.spy(SendAlertServiceImpl.class));

        System.out.println("Температура не в норме:");
        medicalService.checkTemperature(patientInfo.getId(),
                BigDecimal.valueOf(36.6));
        //температура в "норме"
        medicalService.checkTemperature(patientInfo.getId(),
                BigDecimal.valueOf(38.6));
    }
}