package ru.kidesoft.desktop.repository.printer;

import gg.jte.CodeResolver;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.TemplateOutput;
import gg.jte.output.StringOutput;
import gg.jte.resolve.DirectoryCodeResolver;
import gg.jte.resolve.ResourceCodeResolver;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.print.*;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Repository;
import ru.kidesoft.desktop.DesktopApplication;
import ru.kidesoft.desktop.controller.javafx.events.manager.StageManager;
import ru.kidesoft.desktop.domain.dao.printer.PrinterRepository;
import ru.kidesoft.desktop.domain.dao.printer.dto.PrinterDto;
import ru.kidesoft.desktop.domain.entity.order.*;
import ru.kidesoft.desktop.domain.entity.profile.Cashier;
import ru.kidesoft.desktop.domain.entity.setting.PageSize;
import ru.kidesoft.desktop.domain.exception.AppException;
import ru.kidesoft.desktop.domain.exception.UnspecifiedException;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PrinterRepositoryImpl implements PrinterRepository {
    ConfigurableApplicationContext applicationContext;
    TemplateEngine templateEngine;

    @Autowired
    public PrinterRepositoryImpl(TemplateEngine templateEngine, ConfigurableApplicationContext context) {
        this.applicationContext = context;

        this.templateEngine = templateEngine;
    }

    @Override
    public void print(OrderDto order, PrinterDto printerDto) throws AppException {

        StringOutput output = new StringOutput();
        templateEngine.render("ticket-place.jte", order, output);

        Platform.runLater(
                () -> {
                    Printer printer = Printer.getAllPrinters().stream().filter(printer1 ->
                            printer1.getName().equals(printerDto.getName())).findFirst().orElseThrow(
                            () -> new RuntimeException(new UnspecifiedException("Printer not found"))
                    );

                    var webView = new WebView();

                    var webEngine = webView.getEngine();

                    webEngine.loadContent(output.toString());


                    PrinterJob printerJob = PrinterJob.createPrinterJob(printer);
                    JobSettings jobSettings = printerJob.getJobSettings();


                    var paper = switch (printerDto.getPageSize()) {
                        case UNDEFINED -> throw new RuntimeException(new UnspecifiedException("Paper not found"));
                        case A4 -> Paper.A4;
                        case A5 -> Paper.A5;
                    };

                    var pageOrientation = switch (printerDto.getPageOrientation()) {
                        case UNDEFINED -> throw new RuntimeException(new UnspecifiedException("Page orientation not found"));
                        case PORTRAIT -> PageOrientation.PORTRAIT;
                        case LANDSCAPE -> PageOrientation.LANDSCAPE;
                    };

                    var pageLayout = printer.createPageLayout(paper, pageOrientation, 0, 0, 0, 0);
                    //var pageLayout = printer.createPageLayout(paper, pageOrientation, Printer.MarginType.HARDWARE_MINIMUM);

                    jobSettings.setPageLayout(pageLayout);



                    webEngine.print(printerJob);

                    printerJob.endJob();

                });




    }


}
