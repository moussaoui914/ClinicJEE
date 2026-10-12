package service;

import exception.DuplicateEmailException;
import exception.UnauthorizedActionException;
import exception.UserNotFoundException;
import model.Patient;
import model.User;
import model.enums.Role;
import repository.PatientRepository;
import repository.UserRepository;
import util.PasswordUtil;

import java.util.Optional;

public class AuthService {

    private final UserRepository userRepo = new UserRepository();
    private final PatientRepository patientRepo = new PatientRepository();

    // =========================================================
    // 1) INSCRIPTION PATIENT
    // =========================================================
    public User register(String nom, String prenom, String email,
                         String telephone, String motDePasse) {

        if (nom == null || nom.isBlank())     throw new IllegalArgumentException("Nom obligatoire.");
        if (prenom == null || prenom.isBlank()) throw new IllegalArgumentException("Prénom obligatoire.");
        if (email == null || email.isBlank())   throw new IllegalArgumentException("Email obligatoire.");
        if (motDePasse == null || motDePasse.length() < 6)
            throw new IllegalArgumentException("Mot de passe : minimum 6 caractères.");

        if (userRepo.findByEmail(email).isPresent())
            throw new DuplicateEmailException("Email déjà utilisé : " + email);

        User user = new User();
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setEmail(email);
        user.setTelephone(telephone);
        user.setMotDePasse(PasswordUtil.hash(motDePasse));
        user.setRole(Role.PATIENT);
        user.setActif(true);
        user = userRepo.save(user);

        Patient patient = new Patient();
        patient.setId(user.getId());
        patient.setNom(nom);
        patient.setPrenom(prenom);
        patient.setEmail(email);
        patient.setTelephone(telephone);
        patient.setMotDePasse(user.getMotDePasse());
        patient.setRole(Role.PATIENT);
        patient.setActif(true);
        patientRepo.save(patient);

        return user;
    }

    // =========================================================
    // 2) INSCRIPTION GÉNÉRIQUE (utilisée par l'Admin pour créer un médecin)
    // =========================================================
    public User registerUser(String nom, String prenom, String email,
                             String telephone, String motDePasse, Role role) {

        if (nom == null || nom.isBlank())     throw new IllegalArgumentException("Nom obligatoire.");
        if (prenom == null || prenom.isBlank()) throw new IllegalArgumentException("Prénom obligatoire.");
        if (email == null || email.isBlank())   throw new IllegalArgumentException("Email obligatoire.");
        if (motDePasse == null || motDePasse.length() < 6)
            throw new IllegalArgumentException("Mot de passe : minimum 6 caractères.");

        if (userRepo.findByEmail(email).isPresent())
            throw new DuplicateEmailException("Email déjà utilisé : " + email);

        User user = new User();
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setEmail(email);
        user.setTelephone(telephone);
        user.setMotDePasse(PasswordUtil.hash(motDePasse));
        user.setRole(role);
        user.setActif(true);
        return userRepo.save(user);
    }

    // =========================================================
    // 3) CONNEXION
    // =========================================================
    public User login(String email, String motDePasse) {
        if (email == null || email.isBlank() || motDePasse == null || motDePasse.isBlank())
            throw new UserNotFoundException("Email ou mot de passe manquant.");

        Optional<User> opt = userRepo.findByEmail(email);
        if (opt.isEmpty())
            throw new UserNotFoundException("Email ou mot de passe incorrect.");

        User user = opt.get();

        if (!PasswordUtil.check(motDePasse, user.getMotDePasse()))
            throw new UserNotFoundException("Email ou mot de passe incorrect.");

        if (Boolean.FALSE.equals(user.getActif()))
            throw new UnauthorizedActionException("Compte désactivé. Contactez l'admin.");

        return user;
    }

    // =========================================================
    // 4) PROFIL
    // =========================================================
    public User getProfile(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable : " + userId));
    }

    public User updateProfile(Long userId, String nom, String prenom, String telephone) {
        User user = getProfile(userId);
        if (nom != null && !nom.isBlank())       user.setNom(nom);
        if (prenom != null && !prenom.isBlank()) user.setPrenom(prenom);
        if (telephone != null)                   user.setTelephone(telephone);
        return userRepo.update(user);
    }

    // =========================================================
    // 5) MOT DE PASSE
    // =========================================================
    public void changePassword(Long userId, String ancien, String nouveau) {
        User user = getProfile(userId);

        if (!PasswordUtil.check(ancien, user.getMotDePasse()))
            throw new UnauthorizedActionException("Ancien mot de passe incorrect.");

        if (nouveau == null || nouveau.length() < 6)
            throw new IllegalArgumentException("Nouveau mot de passe : minimum 6 caractères.");

        user.setMotDePasse(PasswordUtil.hash(nouveau));
        userRepo.update(user);
    }

    // =========================================================
    // 6) ACTIVER / DÉSACTIVER
    // =========================================================
    public void deactivate(Long userId) {
        User user = getProfile(userId);
        user.setActif(false);
        userRepo.update(user);
    }

    public void activate(Long userId) {
        User user = getProfile(userId);
        user.setActif(true);
        userRepo.update(user);
    }
}