package domain;

/**
 * Created by eiriksandberg on 25.04.2016.
 */
public class CourseUserResolver {
    private User user;
    private Course course;

    public CourseUserResolver(){};


    public CourseUserResolver(User user, Course course) {
        this.user = user;
        this.course = course;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "CourseUserResolver{" +
                "course=" + course +
                ", user=" + user +
                '}';
    }
}
