package repository;

import domain.*;

import java.util.*;

/**
 * Created by eiriksandberg on 10.02.2016.
 */
public class CourseRepositoryMock implements CourseRepository {

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

    Date dato1 = setCalendar(2016,6,6).getTime();
    Date dato2 = setCalendar(2016,6,7).getTime();
    Date dato3 = setCalendar(2016,6,8).getTime();

    Date start1 = setCalendar(2016,6,6,10,0).getTime();
    Date start2 = setCalendar(2016,6,6,12,0).getTime();
    Date start3 = setCalendar(2016,6,6,15,0).getTime();
    Date start4 = setCalendar(2016,6,7,9,0).getTime();
    Date start5 = setCalendar(2016,6,7,11,0).getTime();
    Date start6 = setCalendar(2016,6,7,13,0).getTime();
    Date start7 = setCalendar(2016,6,8,10,0).getTime();
    Date start8 = setCalendar(2016,6,8,12,0).getTime();
    Date start9 = setCalendar(2016,6,8,14,0).getTime();

    Date end1 = setCalendar(2016,6,6,13,0).getTime();
    Date end2 = setCalendar(2016,6,6,14,0).getTime();
    Date end3 = setCalendar(2016,6,6,17,0).getTime();
    Date end4 = setCalendar(2016,6,7,12,30).getTime();
    Date end5 = setCalendar(2016,6,7,12,0).getTime();
    Date end6 = setCalendar(2016,6,7,15,0).getTime();
    Date end7 = setCalendar(2016,6,8,12,0).getTime();
    Date end8 = setCalendar(2016,6,8,13,30).getTime();
    Date end9 = setCalendar(2016,6,8,16,0).getTime();

    /*

    Calendar start1 = new GregorianCalendar(1,6,2016,10,0);
    Calendar start2 = new GregorianCalendar(1,6,2016,12,0);
    Calendar start3 = new GregorianCalendar(1,6,2016,15,0);
    Calendar start4 = new GregorianCalendar(2,6,2016,9,0);
    Calendar start5 = new GregorianCalendar(2,6,2016,11,0);
    Calendar start6 = new GregorianCalendar(2,6,2016,13,0);
    Calendar start7 = new GregorianCalendar(3,6,2016,10,0);
    Calendar start8 = new GregorianCalendar(3,6,2016,12,0);
    Calendar start9 = new GregorianCalendar(3,6,2016,14,0);

    Calendar end1 = new GregorianCalendar(1,6,2016,13,0);
    Calendar end2 = new GregorianCalendar(1,6,2016,14,0);
    Calendar end3 = new GregorianCalendar(1,6,2016,17,0);
    Calendar end4 = new GregorianCalendar(2,6,2016,12,30);
    Calendar end5 = new GregorianCalendar(2,6,2016,12,0);
    Calendar end6 = new GregorianCalendar(2,6,2016,15,0);
    Calendar end7 = new GregorianCalendar(3,6,2016,12,0);
    Calendar end8 = new GregorianCalendar(3,6,2016,13,30);
    Calendar end9 = new GregorianCalendar(3,6,2016,16,0);

     */
    
    

    private Course course = generateCourseMock("Kurs for legeforeningen", "Oppdal", "Dette er et kurs for legeforeningen. Kurset holder sted i Oppdal", 200);
    private ArrayList<Course> courses = makeCourses();

    public Course getCourse(int id){
        for (int i = 0; i < courses.size(); i++){
            if (courses.get(i).getId() == id){
                return courses.get(i);
            }
        }
        return null;
    }

    public ArrayList<Course> getCourses(){return courses;}

    public ArrayList<Course> makeCourses() {
        ArrayList<Course> courses = new ArrayList<Course>();

        // Course 1
        ArrayList<Session> sessions = new ArrayList<Session>();
        sessions.add(generateSessionMock("Kurs i franske oster", "Dette er et kurs i franske oster. Oster fra Bordeux", start1, end1, "Trondheim", 0));
        sessions.add(generateSessionMock("Kurs i vinsmaking", "Dette er et kurs i vinsmaking", start2, end2, "Trondheim", 1));
        sessions.add(generateSessionMock("Kurs i Chablis", "Dette er et kurs om hvitvin. Nærmere bestemt Chablis",  start3, end3, "Trondheim", 2));
        ArrayList<Event> events = new ArrayList<Event>();
        events.add(generateEventMock("Konsert med Kaja Gunnufsen", 20, 250, start1, end1, "Byscenen", 0));
        events.add(generateEventMock("Topptur på Byåsen", 70, 99, start2, end2, "Byåsens eldorado", 1));
        events.add(generateEventMock("Oktoberfest i dødens dal", 10, 499, start3, end3, "Dødens dal", 2));
        ArrayList<String> roles = generateRoleArray("Hobby", "Profesjonell");
        Form form = generateMockForm();
        String title = "Kurs i gastronomi";
        String location = "Trondheim";
        String description = "Dette er et kurs i forskjellige matopplevelser fra Frankrike";
        Date startDate = dato1;
        Date endDate = dato2;
        int maxNumber = 200;
        int id = 0;
        courses.add(new Course(sessions,events,roles,form,title,location,description,startDate,endDate,3560,1250,200,maxNumber,id));

        //Course 2
        ArrayList<Session> sessions2 = new ArrayList<Session>();
        sessions2.add(generateSessionMock("Kurs i franske oster", "Dette er et kurs i franske oster. Oster fra Bordeux", start1, end1, "Trondheim", 0));
        sessions2.add(generateSessionMock("Kurs i vinsmaking", "Dette er et kurs i vinsmaking", start2, end2, "Trondheim", 1));
        sessions2.add(generateSessionMock("Kurs i Chablis", "Dette er et kurs om hvitvin. Nærmere bestemt Chablis",  start3, end3, "Trondheim", 2));
        sessions2.add(generateSessionMock("Kurs i Javaprogrammering", "Dette er et kurs i Javaprogrammering. Dette kurset passer for viderekommende",  start4, end4,"Trondheim", 3));
        sessions2.add(generateSessionMock("Kurs i PHP", "Dette er et kurs i PHP", start5, end5, "Trondheim", 4));
        sessions2.add(generateSessionMock("Kurs i HTML", "Dette er et kurs i HTML 5", start6, end6, "Trondheim", 5));
        ArrayList<Event> events2 = new ArrayList<Event>();
        events2.add(generateEventMock("Java-zone", 20, 799, start1, end1, "Samfundet", 0));
        events2.add(generateEventMock("Foredrag om AI", 100, 50, start2, end2,"Work-Work", 1));
        events2.add(generateEventMock("Fordrag med Bill Gates", 50, 0, start3, end3,"NTNU Gløshaugen", 2));
        ArrayList<String> roles2 = generateRoleArray("Elev", "Lærer");
        Form form2 = generateMockForm2();
        String title2 = "Kurs i programmering";
        String location2 = "Trondheim";
        String description2 = "Dette er et kurs i forskjellige programmeringsspråk";
        Date startDate2 = dato1;
        Date endDate2 = dato2;
        int maxNumber2 = 100;
        int id2 = 1;
        courses.add(new Course(sessions2,events2,roles2,form2,title2,location2,description2,startDate2,endDate2,3560,1250,200,maxNumber2, id2));

        //Course 3
        ArrayList<Session> sessions3 = new ArrayList<Session>();
        sessions3.add(generateSessionMock("Kurs i fingerspill", "Dette er et kurs i fingerspill til gitar. Her ser vi på mange forskjellige stilarter som inkluderer alt fra flamenco til James Taylor. Dette kurset passer for gitarister som har spilt en del fra før", start7, end7, "Oslo", 0));
        sessions3.add(generateSessionMock("Kurs i sweep picking", "Dette er et kurs i teknikken sweep picking", start8, end8, "Oslo", 1));
        sessions3.add(generateSessionMock("Kurs i arpeggios med Brent Hinds", "Dette er et kurs i arpeggios, holdt av gitaristen, Brent Hinds fra Mastodon", start9, end9, "Oslo", 2));
        ArrayList<Event> events3 = new ArrayList<Event>();
        events3.add(generateEventMock("Konsert med Marit Larsen", 20, 449, start7, end7,"Sentrum Scene", 0));
        events3.add(generateEventMock("Stevie Ray Vaughan Tribute", 200, 50, start8, end8,"Café Brasil", 1));
        events3.add(generateEventMock("Ølkurs med Al Ko Holiker", 50, 0, start9, end9,"Skansen", 2));
        ArrayList<String> roles3 = generateRoleArray("Gitarlærer", "Gitarspiller");
        Form form3 = generateMockForm2();
        String title3 = "Kurs i gitarspilling";
        String location3 = "Oslo";
        String description3 = "Dette er et kurs i gitarspilling der vi ser på forskjellige teknikker";
        Date startDate3 = dato1;
        Date endDate3 = dato2;
        int maxNumber3 = 100;
        int id3 = 2;
        courses.add(new Course(sessions3,events3,roles3,form3,title3,location3,description3,startDate3,endDate3,3560,1250,200,maxNumber3, id3));
        return courses;
    }

    // {day: 'Mandag', id: 'Sangtime', start: new Date(Date.UTC(2016, 1, 10, 12, 0)), end: new Date(Date.UTC(2016, 1, 10, 14, 0))},

    public Course getMockCourse() {
        return course;
    }


    public Course generateCourseMock(String t, String l, String d, int max) {
        ArrayList<Session> sessions = new ArrayList<Session>();
        sessions.add(generateSessionMock("Kurs i hjerte/lunge-redning", "Dette er et kurs i hjerte/lunge redning",  start1, end1, "Trondheim", 1));
        ArrayList<Event> events = new ArrayList<Event>();
        events.add(generateEventMock("Konsert med Highasakite", 50, 499, start1, end1, "Byscenen", 1));
        ArrayList<String> roles = generateRoleArray("Lege", "Ambulansearbeider");
        Form form = generateMockForm();
        String title = t;
        String location = l;
        String description = d;
        Date startDate = dato1;
        Date endDate = dato2;
        int maxNumber = max;
        return new Course(sessions,events,roles,form,title,location,description,startDate,endDate,3560,1250,200,maxNumber,12);
    }

    public Session generateSessionMock(String t, String d, Date sDate, Date eDate, String l, int id){
        String title = t;
        String description = d;
        Date date = dato1;
        Date start = sDate;
        Date end = eDate;
        String location = l;
        int max = 50;
        return new Session(title,description,date,start,end,location,max,id);
    }

    public Event generateEventMock(String t, int max, double p, Date s, Date e, String l, int id){
        String title = t;
        int maxNumber = max;
        double price = p;
        String location = l;
        Date start = s;
        Date end = e;
        return new Event(title,maxNumber,price,location,start,end,id);
    }

    public ArrayList<String> generateRoleArray(String role1, String role2){
        ArrayList<String> roles = new ArrayList<String>();
        roles.add(role1);
        roles.add(role2);
        return roles;
    }

    public Form generateMockForm(){
         ArrayList<InputParameter> requiredPersonalia = generateRequiredPersonalia();
         ArrayList<InputParameter> optionalPersonalia = generateOptionalPersonalia();
         ArrayList<InputParameter> requiredWorkplace = generateRequiredWorkplace();
         ArrayList<InputParameter> optionalWorkplace = generateOptionalWorkplace();
         ArrayList<InputParameter> inputQuestions = generateInputQuestions();
        CheckboxModel cm = new CheckboxModel(true,true);
        return new Form(requiredPersonalia,optionalPersonalia,requiredWorkplace,optionalWorkplace,inputQuestions,cm);
    }

    public ArrayList<InputParameter> generateRequiredPersonalia(){
        ArrayList<InputParameter> list = new ArrayList<InputParameter>();
        String a = "Input";
        for (int i = 0; i < 5; i++){
            list.add(new InputParameter(i+"ReqPers",a));
        }
        return list;
    }
    public ArrayList<InputParameter> generateRequiredWorkplace(){
        ArrayList<InputParameter> list = new ArrayList<InputParameter>();
        String a = "Input";
        for (int i = 0; i < 5; i++){
            list.add(new InputParameter(i+"ReqWork",a));
        }
        return list;
    }
    public ArrayList<InputParameter> generateOptionalPersonalia(){
        ArrayList<InputParameter> list = new ArrayList<InputParameter>();
        String a = "Input";
        for (int i = 0; i < 5; i++){
            list.add(new InputParameter(i+"OptPers",a));
        }
        return list;
    }
    public ArrayList<InputParameter> generateOptionalWorkplace(){
        ArrayList<InputParameter> list = new ArrayList<InputParameter>();
        String a = "Input";
        for (int i = 0; i < 5; i++){
            list.add(new InputParameter(i+"OptWork",a));
        }
        return list;
    }
    public ArrayList<InputParameter> generateInputQuestions(){
        ArrayList<InputParameter> list = new ArrayList<InputParameter>();
        String a = "Input";
        for (int i = 0; i < 5; i++){
            list.add(new InputParameter(i+"InputQuestion",a));
        }
        return list;
    }


    public Course generateTemplate(){
        ArrayList<InputParameter> requiredPersonalia = new ArrayList<InputParameter>();
        InputParameter a = new InputParameter("Fornavn", "Input");
        InputParameter b = new InputParameter("Etternavn", "Input");
        InputParameter c = new InputParameter("Telefonnummer", "Input");
        InputParameter d = new InputParameter("E-postadresse", "Input");
        InputParameter e = new InputParameter("Fødselsår", "Input");
        InputParameter f = new InputParameter("Bemerkning", "Checkbox");
        requiredPersonalia.add(a);
        requiredPersonalia.add(b);
        requiredPersonalia.add(c);
        requiredPersonalia.add(d);
        requiredPersonalia.add(e);
        requiredPersonalia.add(f);
        ArrayList<InputParameter> requiredWorkplace = new ArrayList<InputParameter>();
        InputParameter g = new InputParameter("Arbeidsplass", "Input");
        InputParameter h = new InputParameter("Adresse", "Input");
        InputParameter i = new InputParameter("Postnr", "Input");
        InputParameter j = new InputParameter("Sted", "Input");
        InputParameter k = new InputParameter("Ønsker faktura sendt til annen adresse", "Checkbox");
        requiredWorkplace.add(g);
        requiredWorkplace.add(h);
        requiredWorkplace.add(i);
        requiredWorkplace.add(j);
        requiredWorkplace.add(k);

        //testing
        ArrayList<InputParameter> optionalPersonalia = new ArrayList<InputParameter>();
        ArrayList<InputParameter> optionalWorkplace = new ArrayList<InputParameter>();
        ArrayList<InputParameter> extraInfo = new ArrayList<InputParameter>();
        CheckboxModel cm = new CheckboxModel(false,false);
        Form form = new Form(requiredPersonalia,optionalPersonalia,requiredWorkplace,optionalWorkplace,extraInfo,cm);
        return new Course(null,null,null,form,null,null,null,null,null,3560,1250,200,200,12);
    }

    public Form generateMockForm2(){
        ArrayList<InputParameter> requiredPersonalia = new ArrayList<InputParameter>();
        InputParameter a = new InputParameter("Fornavn", "Input");
        InputParameter b = new InputParameter("Etternavn", "Input");
        InputParameter c = new InputParameter("Telefonnummer", "Input");
        InputParameter d = new InputParameter("E-postadresse", "Input");
        InputParameter e = new InputParameter("Fødselsår", "Input");
        InputParameter f = new InputParameter("Bemerkning", "Checkbox");
        requiredPersonalia.add(a);
        requiredPersonalia.add(b);
        requiredPersonalia.add(c);
        requiredPersonalia.add(d);
        requiredPersonalia.add(e);
        requiredPersonalia.add(f);
        ArrayList<InputParameter> requiredWorkplace = new ArrayList<InputParameter>();
        InputParameter g = new InputParameter("Arbeidsplass", "Input");
        InputParameter h = new InputParameter("Adresse", "Input");
        InputParameter i = new InputParameter("Postnr", "Input");
        InputParameter j = new InputParameter("Sted", "Input");
        InputParameter k = new InputParameter("Ønsker faktura sendt til annen adresse", "Checkbox");
        requiredWorkplace.add(g);
        requiredWorkplace.add(h);
        requiredWorkplace.add(i);
        requiredWorkplace.add(j);
        requiredWorkplace.add(k);
        Form form = new Form(requiredPersonalia,null,requiredWorkplace,null,null,null);
        return form;
    }

}
