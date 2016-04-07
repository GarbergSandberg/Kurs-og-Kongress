package repository;

import domain.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Created by eiriksandberg on 07.04.2016.
 */

public class RegistrationRepositoryMock implements RegistrationRepository{
    ArrayList<Person> persons = generateMockPersons();
    ArrayList<Workplace> workplaces = generateMockWorkplaces();
    ArrayList<Registration> registrations = generateRegistrations();

    @Override
    public ArrayList<Registration> getRegistrations(int courseID) {
        ArrayList<Registration> reg = new ArrayList<Registration>();
        for (Registration r : registrations){
            if(r.getCourseID() == courseID){
                reg.add(r);
            }
        }
        return reg;
    }

    public ArrayList<Registration> generateRegistrations(){
        ArrayList<Registration> reg = new ArrayList<Registration>();
        Registration r1 = new Registration(0, 1, sessionIDArray(), persons.get(0), workplaces.get(0), generatePayments(), setDates(), makeOptionalPersonaliaAnswers(),makeOptionalWorkplaceAnswers(),makeExtraInfoAnswers(), null,generateMockForm());
        Registration r2 = new Registration(1, 1, sessionIDArray(), persons.get(1), workplaces.get(0), generatePayments(), setDates(), makeOptionalPersonaliaAnswers(),makeOptionalWorkplaceAnswers(),makeExtraInfoAnswers(), null,generateMockForm());
        Registration r3 = new Registration(2, 1, sessionIDArray(), persons.get(2), workplaces.get(1), generatePayments(), setDates(), makeOptionalPersonaliaAnswers(),makeOptionalWorkplaceAnswers(),makeExtraInfoAnswers(), null,generateMockForm());
        Registration r4 = new Registration(3, 1, sessionIDArray(), persons.get(3), workplaces.get(1), generatePayments(), setDates(), makeOptionalPersonaliaAnswers(),makeOptionalWorkplaceAnswers(),makeExtraInfoAnswers(), null,generateMockForm());
        Registration r5 = new Registration(0, 1, sessionIDArray(), persons.get(4), workplaces.get(1), generatePayments(), setDates(), makeOptionalPersonaliaAnswers(),makeOptionalWorkplaceAnswers(),makeExtraInfoAnswers(), null,generateMockForm());
        reg.add(r1);
        reg.add(r2);
        reg.add(r3);
        reg.add(r4);
        reg.add(r5);
        return reg;
    }

    public ArrayList<Payment> generatePayments(){
        ArrayList<Payment> payments = new ArrayList<Payment>();
        Payment p1 = new Payment(3500, "Kursavgift");
        Payment p2 = new Payment(200, "Dagpakke");
        Payment p3 = new Payment(200, "Dagpakke");
        Payment p4 = new Payment(200, "Dagpakke");
        payments.add(p1);
        payments.add(p2);
        payments.add(p3);
        payments.add(p4);
        return payments;
    }

    public ArrayList<Person> generateMockPersons(){
        ArrayList<Person> array = new ArrayList<Person>();
        Person p1 = new Person(1, "Eirik", "Sandberg", 1994, 99463401, "eirik.sandberg@live.no");
        Person p2 = new Person(2, "Lars", "Garberg", 1994, 92392373, "lars_er_kul@hesterbest.no");
        Person p3 = new Person(3, "Ola", "Nordmann", 1989, 12345678, "ola@gmail.com");
        Person p4 = new Person(4, "Mats", "Nilsen", 1991, 48732817, "mats.nilsen@gmail.com");
        Person p5 = new Person(5, "Roy", "Moe", 1965, 91828374, "roy.moe@hotmail.com");
        array.add(p1);
        array.add(p2);
        array.add(p3);
        array.add(p4);
        array.add(p5);
        System.out.println(array.size() + " ==== ARRAY.SIZE PDFPSAKDSAKFDA");
        return array;
    }

    public ArrayList<Workplace> generateMockWorkplaces(){
        ArrayList<Workplace> array = new ArrayList<Workplace>();
        Workplace w1 = new Workplace(0, "Microsoft", 7042, "Trondheim", "Ladevegen 2");
        Workplace w2 = new Workplace(1, "St. Olavs hospital", 7020, "Trondheim", "Elgsetergata 4");
        array.add(w1);
        array.add(w2);
        return array;
    }

    public ArrayList<Date> setDates(){
        ArrayList<Date> dates = new ArrayList<Date>();
        Date dato1 = setCalendar(2016,6,6).getTime();
        Date dato2 = setCalendar(2016,6,7).getTime();
        Date dato3 = setCalendar(2016,6,8).getTime();
        dates.add(dato1);
        dates.add(dato2);
        dates.add(dato3);
        return dates;
    }

    public ArrayList<Integer> sessionIDArray(){
        ArrayList<Integer> sessionID = new ArrayList<Integer>();
        sessionID.add(0);
        sessionID.add(2);
        return sessionID;
    }

    public Form generateMockForm(){
        ArrayList<InputParameter> optionalPersonalia = new ArrayList<InputParameter>();
        InputParameter opt1 = new InputParameter("Huseier", "Checkbox");
        InputParameter opt2 = new InputParameter("Favorittfarge", "Input");
        optionalPersonalia.add(opt1);
        optionalPersonalia.add(opt2);
        ArrayList<InputParameter> optionalWorkplace = new ArrayList<InputParameter>();
        InputParameter opt3 = new InputParameter("Trives du på jobb?", "Checkbox");
        InputParameter opt4 = new InputParameter("Skriv inn navn på bedriftseier", "Input");
        optionalWorkplace.add(opt3);
        optionalWorkplace.add(opt4);
        ArrayList<InputParameter> extraInfo = new ArrayList<InputParameter>();
        InputParameter opt5 = new InputParameter("Har allergi", "Checkbox");
        InputParameter opt6 = new InputParameter("Favoritt bilmerke", "Input");
        extraInfo.add(opt5);
        extraInfo.add(opt6);
        CheckboxModel cm = new CheckboxModel(true, true);
        Form form = new Form(optionalPersonalia,optionalWorkplace,extraInfo,cm);
        return form;
    }

    public ArrayList<InputParameter> makeOptionalPersonaliaAnswers(){
        ArrayList<InputParameter> optionalPersonalia = new ArrayList<InputParameter>();
        InputParameter opt1 = new InputParameter("true", "Checkbox");
        InputParameter opt2 = new InputParameter("Blå", "Input");
        optionalPersonalia.add(opt1);
        optionalPersonalia.add(opt2);
        return optionalPersonalia;
    }
    public ArrayList<InputParameter> makeOptionalWorkplaceAnswers(){
        ArrayList<InputParameter> optionalWorkplace = new ArrayList<InputParameter>();
        InputParameter opt3 = new InputParameter("true", "Checkbox");
        InputParameter opt4 = new InputParameter("Jon Jonasen", "Input");
        optionalWorkplace.add(opt3);
        optionalWorkplace.add(opt4);
        return optionalWorkplace;
    }
    public ArrayList<InputParameter> makeExtraInfoAnswers(){
        ArrayList<InputParameter> extraInfo = new ArrayList<InputParameter>();
        InputParameter opt5 = new InputParameter("Har allergi", "Checkbox");
        InputParameter opt6 = new InputParameter("Favoritt bilmerke", "Input");
        extraInfo.add(opt5);
        extraInfo.add(opt6);
        return extraInfo;
    }






    public Calendar setCalendar(int year, int month,int day, int hour, int minute){
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, day);
        date.set(Calendar.HOUR_OF_DAY, hour);
        date.set(Calendar.MINUTE, minute);
        return date;
    }
    public Calendar setCalendar(int year, int month,int day){
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, day);
        return date;
    }
}
