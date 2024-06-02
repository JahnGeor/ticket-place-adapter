package ru.kidesoft.desktop.domain.entity.click;

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
@Table(name = "CLICK")
public class Click {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @ColumnDefault("RANDOM_UUID()")
    @Column(name = "ID", nullable = false)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "LOGIN_ID", nullable = false)
    private Login login;

    @Column(name = "CLICK_ID", nullable = false)
    private Integer clickId;

}