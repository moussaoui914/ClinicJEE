package mapper;

import dto.UserDTO;
import model.User;

import java.util.List;
import java.util.stream.Collectors;

public final class UserMapper {
    private UserMapper() {}

    public static UserDTO toDTO(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setNom(user.getNom());
        dto.setPrenom(user.getPrenom());
        dto.setEmail(user.getEmail());
        dto.setTelephone(user.getTelephone());
        dto.setRole(user.getRole());
        dto.setActif(user.getActif());
        dto.setDateCreation(user.getDateCreation());
        return dto;
    }

    public static List<UserDTO> toDTOList(List<User> users) {
        return users.stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }
}