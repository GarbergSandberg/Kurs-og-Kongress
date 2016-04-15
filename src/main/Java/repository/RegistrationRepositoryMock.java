/*
package repository;

import domain.*;

import java.util.*;

*/
/**
 * Created by eiriksandberg on 07.04.2016.
 *//*


public class RegistrationRepositoryMock implements RegistrationRepository{
    CourseRepositoryMock m = new CourseRepositoryMock();
    ArrayList<Course> courses = m.makeCourses();
    ArrayList<Person> persons = generateMockPersons();
    ArrayList<Workplace> workplaces = generateMockWorkplaces();
    ArrayList<Registration> registrations = generateRegistrations();


    public ArrayList<Registration> getRegistrations(int courseID) {
        ArrayList<Registration> reg = new ArrayList<Registration>();
        for (Registration r : registrations){
            if(r.getCourse().getId() == courseID){
                reg.add(r);
            }
        }
        return reg;
    }

    public ArrayList<Registration> generateRegistrations(){
        ArrayList<Registration> reg = new ArrayList<Registration>();
        Registration r1 = new Registration(0, courses.get(0), sessionIDArray(), sessionIDArray(), generateAccomondation(), persons.get(0), workplaces.get(0), generatePayments(), setDates(), makeOptionalPersonaliaAnswers(),makeOptionalWorkplaceAnswers(),makeExtraInfoAnswers(), "E.C. Dahls gate 2", "Lærer");
        Registration r2 = new Registration(1, courses.get(0), sessionIDArray(), sessionIDArray(), generateAccomondation(), persons.get(1), workplaces.get(0), generatePayments(), setDates(), makeOptionalPersonaliaAnswers(),makeOptionalWorkplaceAnswers(),makeExtraInfoAnswers(), null, "Elev");
        Registration r3 = new Registration(2, courses.get(0), sessionIDArray(), sessionIDArray(), generateAccomondation(), persons.get(2), workplaces.get(1), generatePayments(), setDates(), makeOptionalPersonaliaAnswers(),makeOptionalWorkplaceAnswers(),makeExtraInfoAnswers(), null, "Elev");
        Registration r4 = new Registration(3, courses.get(0), sessionIDArray(), sessionIDArray(), generateAccomondation(), persons.get(3), workplaces.get(1), generatePayments(), setDates(), makeOptionalPersonaliaAnswers(),makeOptionalWorkplaceAnswers(),makeExtraInfoAnswers(), null, "Elev");
        Registration r5 = new Registration(0, courses.get(0), sessionIDArray(), sessionIDArray(), generateAccomondation(), persons.get(4), workplaces.get(1), generatePayments(), setDates(), makeOptionalPersonaliaAnswers(),makeOptionalWorkplaceAnswers(),makeExtraInfoAnswers(), null, "Elev");
        r1.setSpeaker(true);
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
        Person p1 = new Person(1, "Eirik", "Sandberg", 1994, 99463401, "eirik.sandberg@live.no", "Mann");
        Person p2 = new Person(2, "Lars", "Garberg", 1994, 92392373, "lars_er_kul@hesterbest.no", "Mann");
        Person p3 = new Person(3, "Ola", "Nordmann", 1989, 12345678, "ola@gmail.com", "Mann");
        Person p4 = new Person(4, "Mats", "Nilsen", 1991, 48732817, "mats.nilsen@gmail.com", "Mann");
        Person p5 = new Person(5, "Roy", "Moe", 1965, 91828374, "roy.moe@hotmail.com", "Mann");
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
        dates.add(dato1);
        dates.add(dato2);
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
        Form form = new Form(0,optionalPersonalia,optionalWorkplace,extraInfo,true);
        return form;
    }

    public ArrayList<InputParameter> makeOptionalPersonaliaAnswers(){
        ArrayList<InputParameter> optionalPersonalia = new ArrayList<InputParameter>();
        InputParameter opt1 = new InputParameter("Blå", "Input");
        InputParameter opt2 = new InputParameter("Gul", "Input");
        InputParameter opt3 = new InputParameter("Rød", "Input");
        InputParameter opt4 = new InputParameter("Oransje", "Input");
        InputParameter opt5 = new InputParameter("Brun", "Input");
        optionalPersonalia.add(opt1);
        optionalPersonalia.add(opt2);
        optionalPersonalia.add(opt3);
        optionalPersonalia.add(opt4);
        optionalPersonalia.add(opt5);
        return optionalPersonalia;
    }
    public ArrayList<InputParameter> makeOptionalWorkplaceAnswers(){
        ArrayList<InputParameter> optionalWorkplace = new ArrayList<InputParameter>();
        InputParameter opt6 = new InputParameter("Arild", "Input");
        InputParameter opt7 = new InputParameter("Jon", "Input");
        InputParameter opt8 = new InputParameter("Jonas", "Input");
        InputParameter opt9 = new InputParameter("Mons", "Input");
        InputParameter opt10 = new InputParameter("Herman", "Input");
        optionalWorkplace.add(opt6);
        optionalWorkplace.add(opt7);
        optionalWorkplace.add(opt8);
        optionalWorkplace.add(opt9);
        optionalWorkplace.add(opt10);
        return optionalWorkplace;
    }
    public ArrayList<InputParameter> makeExtraInfoAnswers(){
        ArrayList<InputParameter> extraInfo = new ArrayList<InputParameter>();
        InputParameter e1 = new InputParameter("Audi", "Input");
        InputParameter e2 = new InputParameter("BMW", "Input");
        InputParameter e3 = new InputParameter("Skoda", "Input");
        InputParameter e4 = new InputParameter("VW", "Input");
        InputParameter e5 = new InputParameter("Ferrari", "Input");
        extraInfo.add(e1);
        extraInfo.add(e2);
        extraInfo.add(e3);
        extraInfo.add(e4);
        extraInfo.add(e5);
        return extraInfo;
    }

    public Accomondation generateAccomondation(){
        Accomondation accomondation = new Accomondation(0,"Torjus", 0, new Date(), new Date(), true);
        return accomondation;
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
}*/
