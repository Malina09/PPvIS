package actors;

import model.impl.Group;
import model.interfaces.Element;
import model.interfaces.ElementsContainer;
import model.interfaces.Model;

import java.util.List;
import java.util.function.Predicate;

public class Secretary {
    private final Model database;

    public Secretary(Model database) {
        this.database = database;
    }

    public ElementsContainer creteGroup(int courseNumber, String groupName) {
        ElementsContainer group = new Group(groupName, courseNumber);
        database.createGroup(group);
        return group;

    }

    public ElementsContainer creteGroup(ElementsContainer group) {
        database.createGroup(group);
        return group;
    }

    public int removeGroup(int courseNumber, String groupName) {
        ElementsContainer result = database.getGroup(courseNumber, groupName);
        if (result != null) {
            return database.removeGroup(result);
        } else
            return 0;
    }

    public int removeGroup(ElementsContainer group) {
        return database.removeGroup(group);
    }

    public boolean addStudentToTheGroup(Element element, ElementsContainer group) {
        return database.addStudent(element, group);
    }

    public boolean addStudentToTheGroup(Element element, int courseNumber, String groupName) {
        ElementsContainer result = database.getGroup(courseNumber, groupName);
        if (result != null) {
            return database.addStudent(element, result);
        } else
            return false;
    }

    public List<Element> getStudentsByGroup(int courseNumber, String groupName) {
        return database.getGroup(courseNumber, groupName).getStudentsList();
    }

    public List<Element> getStudentsByGroupName(ElementsContainer group) {
        return group.getStudentsList();
    }

    public List<Element> getStudentsByCourse(int courseNumber) {
        return getStudentIf(e -> e.getCourseNumber() == courseNumber);
    }

    private List<Element> getStudentIf(Predicate<ElementsContainer> condition) {
        return Util.getStudentIf(database, condition);
    }

    public Model getDatabase() {
        return database;
    }

}
