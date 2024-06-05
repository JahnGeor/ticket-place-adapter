package ru.kidesoft.desktop.domain.entity.history;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;
import ru.kidesoft.desktop.domain.entity.login.Login;
import ru.kidesoft.desktop.domain.entity.order.OperationType;
import ru.kidesoft.desktop.domain.entity.order.SourceType;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;
@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "HISTORY")
@AllArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ColumnDefault("RANDOM_UUID()")
    @Column(name = "ID", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "LOGIN_ID", nullable = false)
    @ToString.Exclude
    private Login login;

    @PrePersist
    protected void onCreate() {
        createdAt = ZonedDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        createdAt = ZonedDateTime.now();
    }

    @Column(name = "CREATED_AT", nullable = false)
    private ZonedDateTime createdAt;

    @Lob
    @Column(name = "ERROR")
    private String error;

    @Column(name = "ORDER_ID", nullable = false)
    private Integer orderId;

    @Column(name = "STATUS", columnDefinition = "TINYINT not null")
    @Enumerated(EnumType.ORDINAL)
    private StatusType statusType;

    @Column(name = "OPERATION_TYPE", columnDefinition = "TINYINT not null")
    @Enumerated(EnumType.ORDINAL)
    private OperationType operationType;

    @Column(name = "SOURCE_TYPE", columnDefinition = "TINYINT not null")
    @Enumerated(EnumType.ORDINAL)
    private SourceType sourceType;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        History history = (History) o;
        return getId() != null && Objects.equals(getId(), history.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
