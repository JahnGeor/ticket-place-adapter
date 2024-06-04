package ru.kidesoft.desktop.domain.entity.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
public class Ticket {
    private Integer id;
    private String number;
    private StatusType status;
    private String zone;
    private Integer seatNumber;
    private Integer rowSector;
    private Float amount;
    private String showName;
    private String ageLimit;
    private ZonedDateTime dateTime;
}
