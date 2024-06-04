package ru.kidesoft.desktop.domain.entity.setting;

import jakarta.persistence.*;
import lombok.*;
import lombok.Generated;
import org.hibernate.annotations.*;
import org.hibernate.proxy.HibernateProxy;
import ru.kidesoft.desktop.domain.entity.login.Login;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@DynamicInsert
public class Setting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ColumnDefault("RANDOM_UUID()")
    @Column(name = "ID", nullable = false)
    private UUID id;


    @Column(name = "KKT_AUTO_RECONNECT", nullable = false)
    private Boolean kktAutoReconnect = false;


    @Lob
    @Column(name = "KKT_DRIVER_PATH", nullable = false)
    private String kktDriverPath = "./drivers/kkt/10.9.5.0/";

    @Lob
    @Column(name = "PRINTER_NAME")
    private String printerName = "";

    @Column(name = "PRINT_CHECK", nullable = false)
    private Boolean printCheck = true;


    @Column(name = "PRINT_TICKET", nullable = false)
    private Boolean printTicket = true;

    @Lob
    @Column(name = "UPDATE_REPOSITORY_URL", nullable = false)
    private String updateRepositoryUrl = "https://example.com";

    @Column(name = "UPDATE_AUTOMATICALLY", nullable = false)
    private Boolean updateAutomatically = false;

    @Column(name = "SERVER_REQUEST_TIMEOUT", nullable = false)
    private Duration serverRequestTimeout = Duration.ofSeconds(10);

    @Column(name = "SERVER_REQUEST_INTERVAL", nullable = false)
    private Duration serverRequestInterval = Duration.ofSeconds(5);

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "LOGIN_ID", nullable = true)
    @ToString.Exclude
    private Login login;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "PAGE_SIZE", columnDefinition = "TINYINT not null")
    private PageSize pageSize = PageSize.A4;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "PAGE_ORIENTATION", columnDefinition = "TINYINT not null")
    private PageOrientation pageOrientation = PageOrientation.PORTRAIT;


    public Setting(Login login) {
        this.login = login;
    }


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Setting setting = (Setting) o;
        return getId() != null && Objects.equals(getId(), setting.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
