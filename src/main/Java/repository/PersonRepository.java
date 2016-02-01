package repository;

import domain.*;

import java.util.*;

/**
 * Created by Lars on 28.01.16.
 */
public interface PersonRepository {

    public Person getPerson(String personNr);

    public List<Person> getAllePersoner() ;

    public boolean registrerPerson(Person p) ;

    public boolean oppdaterPerson(Person p) ;

    public boolean slettPerson(Person p) ;
}