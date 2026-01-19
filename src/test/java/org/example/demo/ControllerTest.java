package org.example.demo;

import org.example.demo.appointment.AppointmentSearchController;
import org.example.demo.doctor.DoctorSearchController;
import org.example.demo.hospital.HospitalSearchController;
import org.example.demo.menu.MainMenuController;
import org.example.demo.patient.PatientSearchController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The type Controller test.
 */
class ControllerTest {

    /**
     * Test main menu controller instantiation.
     */
    @Test
    void testMainMenuControllerInstantiation() {
        MainMenuController controller = new MainMenuController();
        assertNotNull(controller);
    }

    /**
     * Test hospital search controller instantiation.
     */
    @Test
    void testHospitalSearchControllerInstantiation() {
        HospitalSearchController controller = new HospitalSearchController();
        assertNotNull(controller);
    }

    /**
     * Test doctor search controller instantiation.
     */
    @Test
    void testDoctorSearchControllerInstantiation() {
        DoctorSearchController controller = new DoctorSearchController();
        assertNotNull(controller);
    }

    /**
     * Test patient search controller instantiation.
     */
    @Test
    void testPatientSearchControllerInstantiation() {
        PatientSearchController controller = new PatientSearchController();
        assertNotNull(controller);
    }

    /**
     * Test appointment search controller instantiation.
     */
    @Test
    void testAppointmentSearchControllerInstantiation() {
        AppointmentSearchController controller = new AppointmentSearchController();
        assertNotNull(controller);
    }
}

