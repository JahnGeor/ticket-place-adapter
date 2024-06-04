package ru.kidesoft.desktop.domain.dao.kkt;

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
