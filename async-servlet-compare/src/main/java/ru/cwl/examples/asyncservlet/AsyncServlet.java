package ru.cwl.examples.asyncservlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by tischenko on 21.02.2018 18:37.
 */
public class AsyncServlet extends HttpServlet {
    ExecutorService pool = Executors.newFixedThreadPool(1000);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final AsyncContext ctxt = req.startAsync();

        pool.execute(new Runnable() {
            @Override
            public void run() {
                //System.err.println("In AsyncContext / Start / Runnable / run");
                try {
                    TimeUnit.SECONDS.sleep(2);
                    ctxt.getResponse().getWriter().print("qqqqq");
                    //ctxt.getResponse().flushBuffer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ctxt.complete();
            }
        });
/*            ctxt.start(new Runnable() {
                @Override
                public void run() {
                    System.err.println("In AsyncContext / Start / Runnable / run");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ctxt.complete();
                }
            });*/
    }
}
