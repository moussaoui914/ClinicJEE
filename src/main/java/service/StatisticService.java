package service;

import model.enums.AppointmentStatus;
import repository.StatisticRepository;

import java.util.HashMap;
import java.util.Map;

public class StatisticService {

    private final StatisticRepository repo = new StatisticRepository();

    public Map<String, Object> getAdminDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("totalPatients",     repo.countPatients());
        stats.put("totalDoctors",      repo.countDoctors());
        stats.put("totalAppointments", repo.countAppointments());
        stats.put("totalDepartments",  repo.countDepartments());
        stats.put("totalSpecialties",  repo.countSpecialties());

        stats.put("plannedAppointments",
                repo.countAppointmentsByStatus(AppointmentStatus.PLANNED));
        stats.put("doneAppointments",
                repo.countAppointmentsByStatus(AppointmentStatus.DONE));
        stats.put("canceledAppointments",
                repo.countAppointmentsByStatus(AppointmentStatus.CANCELED));

        stats.put("todayAppointments", repo.countTodayAppointments());

        return stats;
    }
}