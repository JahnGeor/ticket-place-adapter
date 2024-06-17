package ru.kidesoft.desktop.domain.entity.order;

import lombok.Builder;
import lombok.Data;
import ru.kidesoft.desktop.domain.entity.profile.Cashier;

@Data
@Builder
public class OrderDto {
    Order order;
    Cashier cashier;
}
