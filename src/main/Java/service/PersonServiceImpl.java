package service;

import domain.*;
import org.springframework.beans.factory.annotation.*;
import repository.*;

import java.util.*;

/**
 * Created by Lars on 01.02.16.
 */
//@Service
public class PersonServiceImpl implements PersonService{

    private PersonRepository repo;

    @Autowired
    public void setRepository(PersonRepository repo){
        System.out.println("PersonServiceImpl.setDatabase2   " + repo);
        this.repo = repo;
    }

    public Person getPerson(String personNr){
        System.out.println("**** PersonServiceImpl.getPerson()  *** ");
        return repo.getPerson(personNr);
    }

    public List<Person> getAllePersoner(){
        System.out.println("**** PersonServiceImpl.getAllePersoner()  *** ");
        return repo.getAllePersoner();
    }

    public boolean oppdaterPersoner(List<Person> personListe){
        System.out.println("**** PersonServiceImpl.oppdaterPerson()  *** ");
        if (personListe == null || personListe.size() == 0) return true;

        boolean erOppdateringOk = true;
        for (Person p : personListe){
            if (!repo.oppdaterPerson(p)) erOppdateringOk=false;
        }
        return erOppdateringOk;
    }

    public boolean registrerPerson(Person p){
        System.out.println("**** PersonServiceImpl.registrerPerson()  *** ");
        return repo.registrerPerson(p);
    }

    public boolean slettPersoner(List<Person> personListe){
        System.out.println("**** PersonServiceImpl.slettPersonER()  *** ");
        if (personListe == null || personListe.size() == 0) return true;

        boolean erSlettingOk = true;
        for (Person p : personListe){
            if (!repo.slettPerson(p)) erSlettingOk = false;
        }
        return erSlettingOk;
    }

    public boolean oppdaterPerson(Person p){
        System.out.println("**** PersonServiceImpl.oppdaterPerson()  *** ");
        return repo.oppdaterPerson(p);
    }
}