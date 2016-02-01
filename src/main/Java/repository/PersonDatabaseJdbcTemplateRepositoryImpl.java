package repository;

import domain.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;

import javax.sql.*;
import java.sql.*;
import java.util.*;

/**
 * Created by Lars on 01.02.16.
 */

public class PersonDatabaseJdbcTemplateRepositoryImpl implements PersonRepository{

    private Connection forbindelse;
    private final String sqlDeletePerson = "Delete from person where personnr = ?";
    private final String sqlSelectPerson = "Select * from person where personnr = ?";
    private final String sqlSelectAllePersoner = "Select * from person";

    private final String sqlInsertPerson = "insert into person values(?,?,?)";
    private final String sqlUpdatePerson = "update person set fornavn=?, etternavn = ? where personnr = ?";

    Person p = new Person("Lars", "Garberg");
    Person p2 = new Person("Eirik", "Sandberg");

    private DataSource dataSource;
    JdbcTemplate jdbcTemplateObject;

    public PersonDatabaseJdbcTemplateRepositoryImpl() {}

    @Autowired
    public void setDataSource(DataSource dataSource){
        System.out.println(" Database.setDataSource " + dataSource);
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    public Person getPerson(String personnr ){
        //return (Person)jdbcTemplateObject.queryForObject(sqlSelectPerson, new Object[]{personnr}, new PersonMapper());
        return p;
    }

    public List<Person> getAllePersoner(){
        //return jdbcTemplateObject.query(sqlSelectAllePersoner, new PersonMapper());
        List<Person> list = new ArrayList<Person>();
        list.add(p);
        list.add(p2);
        return list;
    }

    public boolean slettPerson(Person person) {
        //jdbcTemplateObject.update(sqlDeletePerson, person.getPersonnr() );
        return true;
    }

    public boolean oppdaterPerson(Person person){
      /*  jdbcTemplateObject.update(sqlUpdatePerson, new Object[]{
                person.getFornavn(),
                person.getEtternavn(),
                person.getPersonnr()
        }); */
        return true;
    }

    public boolean registrerPerson(Person person){
       /* jdbcTemplateObject.update(sqlInsertPerson,
                new Object[]{
                        person.getPersonnr(),
                        person.getFornavn(),
                        person.getEtternavn()
                }); */
        return true;
    }
}
