package service;

import dto.TimeSlotDTO;
import model.Appointment;
import model.Availability;
import repository.AppointmentRepository;
import repository.AvailabilityRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Génère les créneaux disponibles pour un médecin à une date donnée.
 *
 * Règles :
 *  - durée RDV : 30 min
 *  - buffer : 5 min
 *  - lead time : 2h
 *  - pause : 12:00 → 13:00
 *  - dimanche : fermé
 */
public class TimeSlotService {

    public static final int DUREE_RDV = 30;
    public static final int BUFFER = 5;
    public static final int LEAD_TIME_HEURES = 2;
    public static final LocalTime PAUSE_DEBUT = LocalTime.of(12, 0);
    public static final LocalTime PAUSE_FIN = LocalTime.of(13, 0);

    private final AvailabilityRepository availabilityRepo = new AvailabilityRepository();
    private final AppointmentRepository appointmentRepo = new AppointmentRepository();

    public List<TimeSlotDTO> genererCreneaux(Long doctorId, LocalDate date) {

        List<TimeSlotDTO> resultat = new ArrayList<>();

        // Dimanche fermé
        if (date.getDayOfWeek() == java.time.DayOfWeek.SUNDAY) return resultat;

        LocalDateTime maintenant = LocalDateTime.now();
        LocalDateTime limiteLeadTime = maintenant.plusHours(LEAD_TIME_HEURES);

        // Récupérer disponibilités du médecin pour ce jour
        List<Availability> dispos = availabilityRepo.findByDoctor(doctorId);

        // Récupérer les RDV existants ce jour
        LocalDateTime debutJour = date.atStartOfDay();
        LocalDateTime finJour = date.plusDays(1).atStartOfDay();
        List<Appointment> rdvs = appointmentRepo
                .findByDoctorAndDateRange(doctorId, debutJour, finJour);

        for (Availability dispo : dispos) {
            // Vérifier jour
            if (dispo.getJour().getJavaDay() != date.getDayOfWeek()) continue;

            // Vérifier statut
            if (!"ACTIVE".equals(dispo.getStatut().name())) continue;

            // Vérifier période validité
            if (dispo.getDateDebut() != null && date.isBefore(dispo.getDateDebut())) continue;
            if (dispo.getDateFin() != null && date.isAfter(dispo.getDateFin())) continue;

            // Générer les créneaux
            LocalTime curseur = dispo.getHeureDebut();
            while (!curseur.plusMinutes(DUREE_RDV).isAfter(dispo.getHeureFin())) {

                LocalTime slotDebut = curseur;
                LocalTime slotFin = curseur.plusMinutes(DUREE_RDV);

                // Exclure pause déjeuner
                if (chevauchePause(slotDebut, slotFin)) {
                    curseur = PAUSE_FIN;
                    continue;
                }

                LocalDateTime dtDebut = date.atTime(slotDebut);
                LocalDateTime dtFin = date.atTime(slotFin);

                // Exclure trop proche (lead time)
                if (dtDebut.isBefore(limiteLeadTime)) {
                    curseur = curseur.plusMinutes(DUREE_RDV);
                    continue;
                }

                // Exclure conflits avec RDV existants
                boolean conflit = rdvs.stream().anyMatch(r ->
                        chevauche(dtDebut, dtFin, r.getDateRdv(), r.getDateFin()));

                if (!conflit) {
                    resultat.add(new TimeSlotDTO(dtDebut, dtFin, true));
                }

                curseur = curseur.plusMinutes(DUREE_RDV + BUFFER);
            }
        }

        return resultat;
    }

    private boolean chevauchePause(LocalTime debut, LocalTime fin) {
        return debut.isBefore(PAUSE_FIN) && fin.isAfter(PAUSE_DEBUT);
    }

    private boolean chevauche(LocalDateTime d1, LocalDateTime f1,
                              LocalDateTime d2, LocalDateTime f2) {
        return d1.isBefore(f2) && f1.isAfter(d2);
    }
}