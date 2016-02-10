package repository;

import domain.*;

import java.util.*;

/**
 * Created by eiriksandberg on 10.02.2016.
 */
public class CourseRepositoryMock implements CourseRepository {
    private Course course = generateCourseMock();

    public Course getCourses() {
        return course;
    }


    public Course generateCourseMock(){
        ArrayList<Session> sessions = new ArrayList<Session>();
        sessions.add(generateSessionMock(1));
        ArrayList<Event> events = new ArrayList<Event>();
        events.add(generateEventMock(1));
        ArrayList<String> roles = generateRoleArray();
        Form form = generateMockForm();
        String title = "Kurs for legeforeningen";
        String location = "Oppdal";
        String description = "Dette er et kurs for legeforeningen. Kurset holder sted i Oppdal";
        Date startDate = new Date();
        Date endDate = new Date();
        int maxNumber = 200;
        return new Course(sessions,events,roles,form,title,location,description,startDate,endDate,maxNumber);
    }

    public Session generateSessionMock(int id){
        String title = "Kurs i hjerte/lungeredning";
        String description = "Dette er et kurs i hjerte/lungeredning";
        Date date = new Date();
        Date start = new Date();
        Date end = new Date();
        String location = "Trondheim";
        return new Session(title,description,date,start,end,location,id);
    }

    public Event generateEventMock(int id){
        String title = "Konsert med Highasakite";
        int maxNumber = 50;
        double price = 449;
        String location = "Byscenen";
        Date date = new Date();
        Date time = new Date();
        return new Event(title,maxNumber,price,location,date,time,id);
    }

    public ArrayList<String> generateRoleArray(){
        ArrayList<String> roles = new ArrayList<String>();
        roles.add("Lege");
        roles.add("Ambulansearbeider");
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
}
