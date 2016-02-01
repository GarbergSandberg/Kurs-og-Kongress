package service;

import domain.*;

import java.util.*;

/**
 * Created by Lars on 28.01.16.
 */
public interface PersonService {

    public List<Person> getAllePersoner();
    public Person getPerson(String personNr);

    public boolean oppdaterPerson(Person p);
    public boolean oppdaterPersoner(List<Person> personListe);

    public boolean registrerPerson(Person p);

    public boolean slettPersoner(List<Person> personListe);

}
