package ru.kidesoft.desktop.domain.entity.setting;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.proxy.HibernateProxy;
import ru.kidesoft.desktop.domain.entity.login.Login;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Setting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ColumnDefault("RANDOM_UUID()")
    @Column(name = "ID", nullable = false)
    private UUID id;

    @ColumnDefault("TRUE")
    @Column(name = "KKT_AUTO_RECONNECT", nullable = false)
    private Boolean kktAutoReconnect = false;

    @ColumnDefault("'./drivers/kkt/10.9.5.0/'")
    @Lob
    @Column(name = "KKT_DRIVER_PATH", nullable = false)
    private String kktDriverPath;

    @ColumnDefault("''")
    @Lob
    @Column(name = "PRINTER_NAME")
    private String printerName;

    @ColumnDefault("TRUE")
    @Column(name = "PRINT_CHECK", nullable = false)
    private Boolean printCheck = false;

    @ColumnDefault("TRUE")
    @Column(name = "PRINT_TICKET", nullable = false)
    private Boolean printTicket = false;

    @ColumnDefault("'https://example.com'")
    @Lob
    @Column(name = "UPDATE_REPOSITORY_URL", nullable = false)
    private String updateRepositoryUrl;

    @ColumnDefault("FALSE")
    @Column(name = "UPDATE_AUTOMATICALLY", nullable = false)
    private Boolean updateAutomatically = false;

    @ColumnDefault("10000000000")
    @Column(name = "SERVER_REQUEST_TIMEOUT", nullable = false)
    private BigDecimal serverRequestTimeout;

    @ColumnDefault("5000000000")
    @Column(name = "SERVER_REQUEST_INTERVAL", nullable = false)
    private BigDecimal serverRequestInterval;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "LOGIN_ID", nullable = false)
    @ToString.Exclude
    private Login login;

    @ColumnDefault("0")
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "PAGE_SIZE", columnDefinition = "TINYINT not null")
    private PageSize pageSize;


    @ColumnDefault("0")
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "PAGE_ORIENTATION", columnDefinition = "TINYINT not null")
    private PageOrientation pageOrientation;


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
