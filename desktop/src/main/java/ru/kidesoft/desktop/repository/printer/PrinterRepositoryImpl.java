package ru.kidesoft.desktop.repository.printer;


import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Repository;
import ru.kidesoft.desktop.infrastructure.port.api.printer.PrinterRepository;
import ru.kidesoft.desktop.infrastructure.port.api.printer.dto.PrinterDto;
import ru.kidesoft.desktop.domain.entity.order.*;
import ru.kidesoft.desktop.domain.exception.AppException;

@Repository
public class PrinterRepositoryImpl implements PrinterRepository {
    ConfigurableApplicationContext applicationContext;
//    TemplateEngine templateEngine;

//    @Autowired
//    public PrinterRepositoryImpl(TemplateEngine templateEngine, ConfigurableApplicationContext context) {
//        this.applicationContext = context;
//
//        this.templateEngine = templateEngine;
//    }

    @Override
    public void print(OrderDto order, PrinterDto printerDto) throws AppException {

//        StringOutput output = new StringOutput();
//        templateEngine.render("ticket-place.jte", order, output);
//
//        Platform.runLater(
//                () -> {
//                    Printer printer = Printer.getAllPrinters().stream().filter(printer1 ->
//                            printer1.getName().equals(printerDto.getName())).findFirst().orElseThrow(
//                            () -> new RuntimeException(new UnspecifiedException("Printer not found"))
//                    );
//
//                    var webView = new WebView();
//
//                    var webEngine = webView.getEngine();
//
//                    webEngine.loadContent(output.toString());
//
//
//                    PrinterJob printerJob = PrinterJob.createPrinterJob(printer);
//                    JobSettings jobSettings = printerJob.getJobSettings();
//
//
//                    var paper = switch (printerDto.getPageSize()) {
//                        case UNDEFINED -> throw new RuntimeException(new UnspecifiedException("Paper not found"));
//                        case A4 -> Paper.A4;
//                        case A5 -> Paper.A5;
//                    };
//
//                    var pageOrientation = switch (printerDto.getPageOrientation()) {
//                        case UNDEFINED -> throw new RuntimeException(new UnspecifiedException("Page orientation not found"));
//                        case PORTRAIT -> PageOrientation.PORTRAIT;
//                        case LANDSCAPE -> PageOrientation.LANDSCAPE;
//                    };
//
//                    var pageLayout = printer.createPageLayout(paper, pageOrientation, 0, 0, 0, 0);
//                    //var pageLayout = printer.createPageLayout(paper, pageOrientation, Printer.MarginType.HARDWARE_MINIMUM);
//
//                    jobSettings.setPageLayout(pageLayout);
//
//
//
//                    webEngine.print(printerJob);
//
//                    printerJob.endJob();
//
//                });
//
//


    }


}
