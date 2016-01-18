package domain;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by eiriksandberg on 18.01.2016.
 */
public class Course {
    private ArrayList<Session> sessions;
    private String title;
    private Date startDateCourse;
    private Date endDateCourse;
    private int maxNumberOfParticipants;
    private boolean hotel;
}
