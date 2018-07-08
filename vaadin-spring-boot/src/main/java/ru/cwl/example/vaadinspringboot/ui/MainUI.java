package ru.cwl.example.vaadinspringboot.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.cwl.example.vaadinspringboot.model.Operation;
import ru.cwl.example.vaadinspringboot.service.CsvImportService;

import java.util.List;

@SpringUI
@Theme("valo")
public class MainUI extends UI {
    @Autowired
    CsvImportService csvImportService;

    @Override
    protected void init(VaadinRequest request) {
        TextField textField = new TextField("enter name:");
        Button button = new Button("Go!");
        Button btnReadCsv = new Button("read!");


        Grid<Operation> grid = new Grid<>();

        grid.addColumn(Operation::getId).setCaption("id");
        grid.addColumn(Operation::getPayDate).setCaption("PayDate");
        grid.addColumn(Operation::getOpDate).setCaption("OpDate");
        grid.addColumn(Operation::getCardNum).setCaption("CardNum");
        grid.addColumn(Operation::getDescription).setCaption("Description");
        grid.addColumn(Operation::getStatus).setCaption("status");
        grid.addColumn(Operation::getPaySumm).setCaption("summ");

        FormLayout formLayout=new FormLayout();

        VerticalLayout layout = new VerticalLayout(textField, button, btnReadCsv, grid);
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);
        button.addClickListener(e -> Notification.show("RUN " + textField.getValue()));
        btnReadCsv.addClickListener(e -> {
            List<Operation> r = csvImportService.readOperationList();
            grid.setItems(r);
        });

    }
}
