package org.test;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import ru.cwl.vaadin1.data.DataService;
import ru.cwl.vaadin1.data.MyItems;
import ru.cwl.vaadin1.data.Row;

import java.util.List;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
    DataService ds=new DataService();
    Grid<MyItems> grid=new Grid<>(MyItems.class);
    Grid<Row> opGrid=new Grid<>(Row.class);

    @Override
    protected void init(VaadinRequest vaadinRequest) {


        final VerticalLayout layout = new VerticalLayout();
        
        final TextField name = new TextField();
        name.setCaption("Type your name here:");
        final TextField name2 = new TextField();

        name2.setCaption("Type your name2 here:");



        Button button = new Button("Click Me2222");
        button.addClickListener(e -> {
            layout.addComponent(new Label("Thanks " + name.getValue() 
                    + ", it works!"));
        });

        List<MyItems> items = ds.getAllItems();
        grid.setItems(items);

        List<Row> opList = ds.getAllOperations();
        opGrid.setItems(opList);


        layout.addComponents(name,name2, button);
        layout.addComponent(grid);
        layout.addComponent(opGrid);

        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
