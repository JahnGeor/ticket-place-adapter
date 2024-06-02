package ru.kidesoft.desktop.domain.entity.profile;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.kidesoft.desktop.domain.entity.login.Login;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "PROFILE")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @ColumnDefault("RANDOM_UUID()")
    @Column(name = "ID", nullable = false)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "LOGIN_ID", nullable = false)
    private Login login;

    @Column(name = "INN", nullable = false)
    private Long inn;
    @Lob
    @Column(name = "FULLNAME", nullable = false)
    private String fullname;

    @Lob
    @Column(name = "AVATAR")
    private String avatar;

    @Lob
    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "USER_ID", nullable = false)
    private Integer userId;

    @Column(name = "ROLE", columnDefinition = "TINYINT")
    @Enumerated(EnumType.ORDINAL)
    private RoleType role;
}
