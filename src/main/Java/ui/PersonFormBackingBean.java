package ui;

/**
 * Created by Lars on 01.02.16.
 */

import domain.*;

import javax.validation.*;
import java.util.*;

public class PersonFormBackingBean {

    @Valid
    private List<Person> allePersoner = null;
    private List<Person> valgtePersoner = null;

    public List<Person> getValgtePersoner() {
        return valgtePersoner;
    }

    public void setValgtePersoner(List<Person> valgtePersoner) {
        this.valgtePersoner = valgtePersoner;
    }

    public List<Person> getAllePersoner(){
        return allePersoner;
    }

    public Person getPerson(String fnr){
        /*for (Person p : valgtePersoner){
            if(p.getPersonnr().equals(fnr)) return p;
        }*/
        return null;
    }

    public void setAllePersoner(List<Person> allePersoner){
        System.out.println(" PersonFormBackingBean.setAllerPersoner()  "  + allePersoner);
        this.allePersoner = allePersoner;
    }
}

