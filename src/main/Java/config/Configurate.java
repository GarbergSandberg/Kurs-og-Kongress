
package config;

import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.*;
import org.springframework.web.servlet.view.tiles3.*;
import repository.*;
import service.*;

import java.sql.*;

@Configuration
@EnableWebMvc  // mvc annotation
@ComponentScan(basePackages = {"controller"}) // pakken der controllerne ligger
public class Configurate extends WebMvcConfigurationSupport {

    @Bean
    public TilesConfigurer tilesConfigurer() {
        return new TilesConfigurer();
    }

    @Bean
    public TilesViewResolver tilesViewResolver() {
        TilesViewResolver tilesViewResolver = new TilesViewResolver();
        return tilesViewResolver;
    }

    // equivalents for <mvc:resources/> tags
    // Hvor finnes statisk ressurser som bilder/ css/ js osv.
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(31556926);
    }

/*    @Bean
    public InternalResourceViewResolver getInternalResourceView() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }*/

    @Override
    @Bean
    public HandlerMapping resourceHandlerMapping() {
        AbstractHandlerMapping handlerMapping = (AbstractHandlerMapping) super.resourceHandlerMapping();
        handlerMapping.setOrder(-1);
        return handlerMapping;
    }

    // NYTT etter Database-impl..

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public DriverManagerDataSource dataSource(){
        String url = "jdbc:derby://localhost:1527/testDB;create=true";
        DriverManagerDataSource dmds = new DriverManagerDataSource(url);
        dmds.setDriverClassName("org.apache.derby.jdbc.ClientDriver");
        try{
            Connection con = dmds.getConnection();
            System.out.println(" *********  Konfig " + con );
            //getAllePersoner(con); //brukes for testing av oppkobling
        }catch(Exception e){
            System.out.println(" Konfig.Feil ved henting av conncetion() " + e);
        }
        return dmds;
    }

    @Bean
    public PersonRepository repository(){
        return new PersonDatabaseJdbcTemplateRepositoryImpl();
        //return new PersonDatabaseRepositoryImpl();
    }

    @Bean
    public PersonService personService(){
        return new PersonServiceImpl();
    }

    @Bean
    public CourseService courseService() {return new CourseServiceImpl();}

    @Bean
    public CourseRepository courseRepository() {return new CourseRepositoryMock();}

    @Bean
    public LoginService loginService() {return new LoginServiceImpl();}

    @Bean
    public LoginRepository loginRepository() {return new LoginRepositoryMock();}
}


