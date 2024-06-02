package ru.kidesoft.desktop.domain.entity.history;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.kidesoft.desktop.domain.entity.login.Login;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "HISTORY")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ColumnDefault("RANDOM_UUID()")
    @Column(name = "ID", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "LOGIN_ID", nullable = false)
    private Login login;

    @Column(name = "CREATED_AT", nullable = false)
    private Instant createdAt;

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
}
