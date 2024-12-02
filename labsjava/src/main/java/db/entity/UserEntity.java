package db.entity;

import db.security.user.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "mathfunction", name="t_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "c_username")
    @NotNull
    @NotBlank
    private String username;

    @Column(name = "c_password")
    @NotNull
    @NotBlank
    @Size(min = 4, max = 255)
    private String password;

    @Column(name = "c_role")
    @Enumerated(EnumType.STRING)
    @NotNull
    private UserRole role;
}