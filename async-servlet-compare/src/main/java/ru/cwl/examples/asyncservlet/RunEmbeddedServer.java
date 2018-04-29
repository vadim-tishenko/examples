package ru.cwl.examples.asyncservlet;

/**
 * Created by tischenko on 21.02.2018 12:47.
 */

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

public class RunEmbeddedServer {


    public static void main(String[] args) throws Exception {

        final int PORT = 9090;
        final int POOL_SIZE = 5;

        QueuedThreadPool threadPool = new QueuedThreadPool(POOL_SIZE, POOL_SIZE);

        Server server = new Server(threadPool);

        ServerConnector connector=new ServerConnector(server);
        connector.setPort(PORT);
        server.setConnectors(new Connector[]{connector});

        //Server server = new Server(9090);

        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        ServletHolder asyncHolder = context.addServlet(AsyncServlet.class, "/async");
        asyncHolder.setAsyncSupported(true);
        context.addServlet(SyncServlet.class, "/sync");

        server.setHandler(context);
        server.start();
        System.out.println("http://localhost:"+ PORT);
        server.join();
    }
}
