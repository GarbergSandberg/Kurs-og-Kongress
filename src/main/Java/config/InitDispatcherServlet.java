/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import org.springframework.web.*;
import org.springframework.web.context.support.*;
import org.springframework.web.servlet.*;

import javax.servlet.*;

public class InitDispatcherServlet implements WebApplicationInitializer {


    public void onStartup(final ServletContext servletContext) throws ServletException {
        registerDispatcherServlet(servletContext);
    }

    private void registerDispatcherServlet(final ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(Configurate.class);
        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

    }
}
