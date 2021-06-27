package actors;


import model.interfaces.Element;
import model.interfaces.ElementsContainer;
import model.interfaces.Model;
import util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Util {
    public Util() {
        throw new AssertionError();
    }

    public static List<Element> getStudentIf(Model database, Predicate<ElementsContainer> condition) {
        Objects.requireNonNull(database);
        Objects.requireNonNull(condition);
        List<Element> result = new ArrayList<>();
        List<ElementsContainer> groups = database.getGroups();
        for (ElementsContainer group : groups) {
            if (condition.test(group)) {
                result.addAll(group.getStudentsList());
            }
        }
        return result;
    }

    public static List<Pair<Element, ElementsContainer>> getPairIf(Model database, Predicate<Element> condition) {
        Objects.requireNonNull(database);
        Objects.requireNonNull(condition);
        List<Pair<Element, ElementsContainer>> result = new ArrayList<>();
        for (ElementsContainer group : database.getGroups()) {
            for (Element student : group.getStudentsList()) {
                if (condition.test(student)) {
                    result.add(new Pair<>(student, group));
                }
            }
        }
        return result;

    }
}
