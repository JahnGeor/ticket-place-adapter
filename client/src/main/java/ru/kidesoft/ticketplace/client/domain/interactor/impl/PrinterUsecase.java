package ru.kidesoft.ticketplace.client.domain.interactor.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.kidesoft.ticketplace.client.domain.dao.Kkt;

public class PrinterUsecase {
    static Logger logger = LogManager.getLogger(PrinterUsecase.class);
    public static Kkt kktRepo;

    public static void printXReport() throws Exception {
        try {
            kktRepo.printXReport();
        } catch (Exception e) {
            logger.error("Ошибка в процессе печати Х-отчета", e);
        }
    }


}
