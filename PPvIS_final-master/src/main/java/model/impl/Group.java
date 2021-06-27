package model.impl;

import model.interfaces.Element;
import model.interfaces.ElementsContainer;

import java.util.*;

public class Group implements ElementsContainer {
    private final String groupName;
    private final int courseNumber;
    private final Set<Element> studentsList = new TreeSet<>();

    public Group(String groupName, int courseNumber) {
        if (groupName.isEmpty())
            throw new RuntimeException("The group name cannot be empty");
        else if (courseNumber < 0)
            throw new RuntimeException("The course number cannot be less then 0. Error value: " + courseNumber);
        this.groupName = groupName;
        this.courseNumber = courseNumber;
    }

    @Override
    public String getGroupName() {
        return groupName;
    }

    @Override
    public int getCourseNumber() {
        return courseNumber;
    }

    public synchronized boolean addStudent(Element element) {
        Objects.requireNonNull(element);
        ((Student) element).setStudying(true);
        return studentsList.add(element);
    }

    public synchronized boolean removeStudent(Element element) {
        Objects.requireNonNull(element);
        ((Student) element).setStudying(false);
        return studentsList.remove(element);
    }

    public synchronized void clear() {
        for (Element student : studentsList) {
            ((Student) student).setStudying(false);
        }
        studentsList.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Group group = (Group) o;
        return groupName.equals(group.groupName) &&
                courseNumber == group.courseNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupName, courseNumber, studentsList);
    }

    @Override
    public List<Element> getStudentsList() {
        List<Element> result = new ArrayList<>(studentsList.size());
        result.addAll(studentsList);
        return Collections.unmodifiableList(result);
    }

}
