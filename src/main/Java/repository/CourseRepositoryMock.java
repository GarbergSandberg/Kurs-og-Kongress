package repository;

import domain.*;

import java.util.*;

/**
 * Created by eiriksandberg on 10.02.2016.
 */
public class CourseRepositoryMock implements CourseRepository {
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

    public ArrayList<Course> makeCourses(){
        ArrayList<Course> courses = new ArrayList<Course>();

        // Course 1
        ArrayList<Session> sessions = new ArrayList<Session>();
        sessions.add(generateSessionMock("Kurs i franske oster", "Dette er et kurs i franske oster. Oster fra Bordeux", "Trondheim", 0));
        sessions.add(generateSessionMock("Kurs i vinsmaking", "Dette er et kurs i vinsmaking", "Trondheim", 1));
        sessions.add(generateSessionMock("Kurs i Chablis", "Dette er et kurs om hvitvin. Nærmere bestemt Chablis", "Trondheim", 2));
        ArrayList<Event> events = new ArrayList<Event>();
        events.add(generateEventMock("Konsert med Kaja Gunnufsen", 20, 250, "Byscenen", 0));
        events.add(generateEventMock("Topptur på Byåsen", 70, 99, "Byåsens eldorado", 1));
        events.add(generateEventMock("Oktoberfest i dødens dal", 10, 499, "Dødens dal", 2));
        ArrayList<String> roles = generateRoleArray("Hobby", "Profesjonell");
        Form form = generateMockForm();
        String title = "Kurs i gastronomi";
        String location = "Trondheim";
        String description = "Dette er et kurs i forskjellige matopplevelser fra Frankrike";
        Date startDate = new Date();
        Date endDate = new Date();
        int maxNumber = 200;
        int id = 0;
        courses.add(new Course(sessions,events,roles,form,title,location,description,startDate,endDate,maxNumber,id));

        //Course 2
        ArrayList<Session> sessions2 = new ArrayList<Session>();
        sessions2.add(generateSessionMock("Kurs i Javaprogrammering", "Dette er et kurs i Javaprogrammering. Dette kurset passer for viderekommende", "Trondheim", 0));
        sessions2.add(generateSessionMock("Kurs i PHP", "Dette er et kurs i PHP", "Trondheim", 1));
        sessions2.add(generateSessionMock("Kurs i HTML", "Dette er et kurs i HTML 5", "Trondheim", 2));
        ArrayList<Event> events2 = new ArrayList<Event>();
        events2.add(generateEventMock("Java-zone", 20, 799, "Samfundet", 0));
        events2.add(generateEventMock("Foredrag om AI", 100, 50, "Work-Work", 1));
        events2.add(generateEventMock("Fordrag med Bill Gates", 50, 0, "NTNU Gløshaugen", 2));
        ArrayList<String> roles2 = generateRoleArray("Elev", "Lærer");
        Form form2 = generateMockForm2();
        String title2 = "Kurs i programmering";
        String location2 = "Trondheim";
        String description2 = "Dette er et kurs i forskjellige programmeringsspråk";
        Date startDate2 = new Date();
        Date endDate2 = new Date();
        int maxNumber2 = 100;
        int id2 = 1;
        courses.add(new Course(sessions2,events2,roles2,form2,title2,location2,description2,startDate2,endDate2,maxNumber2, id2));

        //Course 3
        ArrayList<Session> sessions3 = new ArrayList<Session>();
        sessions3.add(generateSessionMock("Kurs i fingerspill", "Dette er et kurs i fingerspill til gitar. Her ser vi på mange forskjellige stilarter som inkluderer alt fra flamenco til James Taylor. Dette kurset passer for gitarister som har spilt en del fra før", "Oslo", 0));
        sessions3.add(generateSessionMock("Kurs i sweep picking", "Dette er et kurs i teknikken sweep picking", "Oslo", 1));
        sessions3.add(generateSessionMock("Kurs i arpeggios med Brent Hinds", "Dette er et kurs i arpeggios, holdt av gitaristen, Brent Hinds fra Mastodon", "Oslo", 2));
        ArrayList<Event> events3 = new ArrayList<Event>();
        events3.add(generateEventMock("Konsert med Marit Larsen", 20, 449, "Sentrum Scene", 0));
        events3.add(generateEventMock("Stevie Ray Vaughan Tribute", 200, 50, "Café Brasil", 1));
        events3.add(generateEventMock("Ølkurs med Al Ko Holiker", 50, 0, "Skansen", 2));
        ArrayList<String> roles3 = generateRoleArray("Gitarlærer", "Gitarspiller");
        Form form3 = generateMockForm2();
        String title3 = "Kurs i gitarspilling";
        String location3 = "Oslo";
        String description3 = "Dette er et kurs i gitarspilling der vi ser på forskjellige teknikker";
        Date startDate3 = new Date();
        Date endDate3 = new Date();
        int maxNumber3 = 100;
        int id3 = 2;
        courses.add(new Course(sessions3,events3,roles3,form3,title3,location3,description3,startDate3,endDate3,maxNumber3, id3));
        return courses;
    }

    public Course getMockCourse() {
        return course;
    }


    public Course generateCourseMock(String t, String l, String d, int max){
        ArrayList<Session> sessions = new ArrayList<Session>();
        sessions.add(generateSessionMock("Kurs i hjerte/lunge-redning", "Dette er et kurs i hjerte/lunge redning", "Trondheim", 1));
        ArrayList<Event> events = new ArrayList<Event>();
        events.add(generateEventMock("Konsert med Highasakite", 50, 499, "Byscenen", 1));
        ArrayList<String> roles = generateRoleArray("Lege", "Ambulansearbeider");
        Form form = generateMockForm();
        String title = t;
        String location = l;
        String description = d;
        Date startDate = new Date();
        Date endDate = new Date();
        int maxNumber = max;
        return new Course(sessions,events,roles,form,title,location,description,startDate,endDate,maxNumber,12);
    }

    public Session generateSessionMock(String t, String d, String l, int id){
        String title = t;
        String description = d;
        Date date = new Date();
        Date start = new Date();
        Date end = new Date();
        String location = l;
        return new Session(title,description,date,start,end,location,id);
    }

    public Event generateEventMock(String t, int max, double p, String l, int id){
        String title = t;
        int maxNumber = max;
        double price = p;
        String location = l;
        Date date = new Date();
        Date time = new Date();
        return new Event(title,maxNumber,price,location,date,time,id);
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
        return new Course(null,null,null,form,null,null,null,null,null,200,12);
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
