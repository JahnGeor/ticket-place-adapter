package ru.kidesoft.desktop.domain.service;

import org.springframework.stereotype.Service;
import ru.kidesoft.desktop.domain.dao.database.ConstantRepository;
import ru.kidesoft.desktop.domain.dao.database.LoginRepository;
import ru.kidesoft.desktop.domain.dao.database.SettingRepository;
import ru.kidesoft.desktop.domain.entity.constant.Constant;
import ru.kidesoft.desktop.domain.entity.constant.ConstantEnums;
import ru.kidesoft.desktop.domain.entity.setting.Setting;

import java.util.Optional;
import java.util.UUID;

@Service
public class SettingService {
    private final ConstantRepository constantRepository;
    private final LoginRepository loginRepository;
    SettingRepository settingRepository;

    public SettingService(SettingRepository settingRepository, ConstantRepository constantRepository, LoginRepository loginRepository) {
        this.settingRepository = settingRepository;
        this.constantRepository = constantRepository;
        this.loginRepository = loginRepository;
    }

    public Setting getSetting() {
        Optional<Constant> constant = constantRepository.findByName(ConstantEnums.ACTIVE_USER_ID);

        if (constant.isPresent()) {
            var login = loginRepository.findById(UUID.fromString(constant.get().getVal()));

            if (login.isEmpty()) {
                throw new RuntimeException("Active user id not found"); // TODO: add exception
            }

            var settingOpt = settingRepository.findByLogin(login.get());

            return settingOpt.orElseGet(() -> settingRepository.save(new Setting(login.get())));
        } else {
            throw new RuntimeException("Active user id not found"); // TODO: add exception
        }
    }

    public void save(Setting setting) {
        var loginId = constantRepository.findByName(ConstantEnums.ACTIVE_USER_ID).orElseThrow(() -> new RuntimeException("Active user id not found")).getVal();

        var login = loginRepository.findById(UUID.fromString(loginId)).orElseThrow(() -> new RuntimeException("Didn't find login with active user id"));

        setting.setLogin(login);

        settingRepository.findByLogin(login).ifPresent(
                oldSetting -> {
                    setting.setId(oldSetting.getId());
                }
        );

        settingRepository.save(setting);
    }

}
