package actors;

import model.interfaces.Element;
import model.interfaces.ElementsContainer;
import model.interfaces.Model;
import util.Pair;

import java.util.List;

public class Teacher {
    private final Model database;

    public Teacher(Model database) {
        this.database = database;
    }

    public List<Element> getStudentsByGroup(int courseNumber, String groupName) {
        return database.getGroup(courseNumber, groupName).getStudentsList();
    }

    public List<Element> getStudentsByGroup(ElementsContainer group) {
        return group.getStudentsList();
    }

    public List<Pair<Element, ElementsContainer>> getStudentsBySecondName(String secondName) {
        return Util.getPairIf(database, e -> e.getSecondName().equals(secondName));
    }

    public Model getDatabase() {
        return database;
    }

}
