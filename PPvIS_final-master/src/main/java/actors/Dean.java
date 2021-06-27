package actors;

import model.interfaces.Element;
import model.interfaces.ElementsContainer;
import model.interfaces.Model;
import util.Pair;

import java.util.List;
import java.util.Objects;

public class Dean {
    private final Model database;

    public Dean(Model database) {
        this.database = database;
    }

    public boolean moveStudentToTheGroup(Element element, ElementsContainer fromGroup, ElementsContainer toGroup) {
        Objects.requireNonNull(element);
        if (fromGroup != null) {
            database.removeStudent(element, fromGroup);
        } else{
            database.removeStudent(element);
        }
        if (toGroup != null) {
            return database.addStudent(element, toGroup);
        } else{
            database.addStudent(element);
        }
        return false;
    }

    public List<Pair<Element, ElementsContainer>> getStudentsByAddress(String address) {
        List<Pair<Element, ElementsContainer>> result = Util.getPairIf(database, e -> e.getKnownAddress().equals(address));

        for (Element student : database.getNotStudents()) {
            if (student.getKnownAddress().equals(address)) {
                result.add(new Pair<>(student, null));
            }
        }
        return result;
    }

    public Model getDatabase() {
        return database;
    }

}
