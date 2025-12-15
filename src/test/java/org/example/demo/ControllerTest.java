package org.example.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple test to ensure controllers are loadable
 */
class ControllerTest {

    @Test
    void testMainMenuControllerInstantiation() {
        MainMenuController controller = new MainMenuController();
        assertNotNull(controller);
    }

    @Test
    void testHospitalSearchControllerInstantiation() {
        HospitalSearchController controller = new HospitalSearchController();
        assertNotNull(controller);
    }

    @Test
    void testDoctorSearchControllerInstantiation() {
        DoctorSearchController controller = new DoctorSearchController();
        assertNotNull(controller);
    }

    @Test
    void testPatientSearchControllerInstantiation() {
        PatientSearchController controller = new PatientSearchController();
        assertNotNull(controller);
    }

    @Test
    void testAppointmentSearchControllerInstantiation() {
        AppointmentSearchController controller = new AppointmentSearchController();
        assertNotNull(controller);
    }
}

