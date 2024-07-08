package ru.kidesoft.desktop.infrastructure.port.api.kkt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KktSetting {
    String path;
    Boolean autoReconnect;
}
