package ru.kidesoft.desktop.repository.kkt.atol;

import org.springframework.stereotype.Repository;
import ru.atol.drivers10.fptr.Fptr;
import ru.atol.drivers10.fptr.IFptr;
import ru.kidesoft.desktop.infrastructure.port.api.kkt.KktRepository;
import ru.kidesoft.desktop.infrastructure.port.api.kkt.KktSetting;
import ru.kidesoft.desktop.domain.exception.KktException;

import java.io.File;

@Repository
public class AtolRepositoryImpl implements KktRepository {

    private IFptr fptr;

    public AtolRepositoryImpl() {}

    public void checkErrorCode(int code) throws KktException {
        if (code != 0) {
            throw new KktException(getFptr().errorDescription(), getFptr().errorCode());
        }
    }

    public void checkErrorAndCancel(int code) throws KktException {
        if (code != 0) {
            var exception = new KktException(getFptr().errorDescription(), getFptr().errorCode());
            checkErrorCode(getFptr().cancelReceipt());
            throw exception;
        }
    }

    @Override
    public KktRepository setConnection(KktSetting kktSetting) throws KktException {
        File driverDir = new File(kktSetting.getPath());

        if (!(driverDir.isDirectory() && driverDir.exists())) {
            throw new KktException("По выбранному пути не обнаружена директория");
        }

        // URI uriDriverPath = driverDir.toURI().resolve(getOSDirectory());

        if (fptr != null) {
            fptr.destroy();
        }

        try {
            // var driverDirPath = uriDriverPath.getPath();
            var driverDirPath = driverDir.getCanonicalPath();
            fptr = new Fptr(driverDirPath);
        } catch (Throwable e) {
            throw new KktException("Не удалось загрузить драйвер ККТ", e);
        }
        fptr.setSingleSetting(IFptr.LIBFPTR_SETTING_AUTO_RECONNECT, kktSetting.getAutoReconnect().toString());
        fptr.setSingleSetting(IFptr.LIBFPTR_SETTING_AUTO_TIME_SYNC, "0"); //

        fptr.applySingleSettings();
        return this;
    }

    @Override
    public KktRepository setConnection() throws KktException {
        if (fptr != null) {
            fptr.destroy();
        }

        try {
            fptr = new Fptr();
        } catch (Throwable e) {
            throw new KktException("Не удалось загрузить драйвер ККТ", e);
        }
        fptr.setSingleSetting(IFptr.LIBFPTR_SETTING_AUTO_RECONNECT, "false");
        fptr.applySingleSettings();
        return this;
    }

    @Override
    public KktRepository openConnection() throws KktException {
        getFptr().open();
        return this;
    }

    @Override
    public KktRepository closeConnection() throws KktException {
        getFptr().close();
        return this;
    }

    @Override
    public IFptr getFptr() throws KktException {
        if (fptr == null) {
            throw new KktException("Не инициализирован драйвер ККТ");
        } else {
            return fptr;
        }
    }

    public String getOSDirectory() {
        String osArch = System.getProperty("os.arch");

        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
            if (osArch.equals("x86")) {
                return "nt-x86-msvc2015";
            } else if (osArch.equals("amd64")) {
                return "nt-x64-msvc2015";
            }
        } else if (osName.startsWith("Mac")) {
            return "macos-x86_64";
        } else if (osName.startsWith("Linux")) {
            if (osArch.equals("x86")) {
                return "linux-x86";
            } else if (osArch.equals("x86_64")) {
                return "linux-x64";
            }
        }

        throw new IllegalArgumentException("Unsupported OS: " + osName);
    }


}
