package model.impl;


import model.interfaces.Element;
import model.interfaces.ElementsContainer;
import model.interfaces.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Database implements Model {
    private final List<CourseLinkedList> coursesList = new ArrayList<>();
    private final List<Element> dontStudy = new ArrayList<>();

    //ToDo
    private final List<Element> allStudents = new ArrayList<>();

    @Override
    public synchronized boolean addStudent(Element student, ElementsContainer toGroup) {
        Objects.requireNonNull(student);
        Objects.requireNonNull(toGroup);

        if (!isGroupExists(toGroup))
            throw new RuntimeException("This group doesn't exist in database: course number = "
                    + toGroup.getCourseNumber() + "; group name = " + toGroup.getGroupName());

        boolean result = ((Group) toGroup).addStudent(student);
        allStudents.add(student);
        if (result)
            dontStudy.remove(student);
        return result;
    }

    @Override
    public List<Element> getAllStudents() {
        return Collections.unmodifiableList(allStudents);
    }

    @Override
    public synchronized boolean addStudent(Element student) {
        Objects.requireNonNull(student);
        allStudents.add(student);
        return dontStudy.add(student);
    }

    @Override
    public synchronized boolean removeStudent(Element student, ElementsContainer fromGroup) {
        Objects.requireNonNull(student);
        Objects.requireNonNull(fromGroup);
        if (!isGroupExists(fromGroup))
            throw new RuntimeException("This group doesn't exist in database: course number = "
                    + fromGroup.getCourseNumber() + "; group name = " + fromGroup.getGroupName());

        boolean result = ((Group) fromGroup).removeStudent(student);
        if (result) {
            dontStudy.add(student);
        }
        return result;
    }

    @Override
    public synchronized boolean removeStudent(Element student) {
        Objects.requireNonNull(student);
        if (!allStudents.contains(student))
            throw new RuntimeException("This student doesn't exist in database: " +
                    "first name = " + student.getFirstName() + "; " +
                    "second name = " + student.getSecondName() + "; " +
                    "patronymic = " + student.getPatronymic()
            );
        return dontStudy.remove(student);
    }

    @Override
    public synchronized boolean createGroup(ElementsContainer group) {
        Objects.requireNonNull(group);
        for (CourseLinkedList course : coursesList) {
            if (course.getCourseNumber() == group.getCourseNumber()) {
                return course.add(group);
            }
        }
        CourseLinkedList tempList = new CourseLinkedList(group.getCourseNumber());
        tempList.add(group);
        return coursesList.add(tempList);
    }

    @Override
    public ElementsContainer getGroup(int courseNumber, String groupName) {
        if (groupName.isEmpty())
            throw new RuntimeException("Group with an empty name cannot exist");
        for (CourseLinkedList course : coursesList) {
            if (course.getCourseNumber() == courseNumber) {
                for (ElementsContainer group : course) {
                    if (group.getGroupName().equals(groupName)) {
                        Group resultGroup = new Group(group.getGroupName(), group.getCourseNumber());
                        group.getStudentsList().forEach(resultGroup::addStudent);
                        return resultGroup;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<ElementsContainer> getGroups() {
        List<ElementsContainer> result = new ArrayList<>(coursesList.size() * 5);
        for (CourseLinkedList course : coursesList) {
            result.addAll(course);
        }
        return Collections.unmodifiableList(result);
    }

    public List<ElementsContainer> getGroupsByCourse(int courseNumber) {
        for (CourseLinkedList course : coursesList) {
            if (course.getCourseNumber() == courseNumber) {
                List<ElementsContainer> result = new ArrayList<>(course.size());
                result.addAll(course);
                return Collections.unmodifiableList(result);
            }
        }
        return List.of();
    }

    @Override
    public List<Element> getNotStudents() {
        ArrayList<Element> result = new ArrayList<>(dontStudy.size());
        result.addAll(dontStudy);
        return result;
    }

    @Override
    public synchronized int removeGroup(ElementsContainer group) {
        Objects.requireNonNull(group);
        int oldGroupsListSize = coursesList.size();
        for (CourseLinkedList course : coursesList) {
            if (course.getCourseNumber() == group.getCourseNumber()) {
                for (ElementsContainer gr : course) {
                    if (gr.getGroupName().equals(group.getGroupName())) {
                        List<Element> students = gr.getStudentsList();
                        ((Group) gr).clear();
                        dontStudy.addAll(students);
                        course.remove(gr);
                    }
                }
            }
        }
        return oldGroupsListSize - coursesList.size();
    }

    private boolean isGroupExists(ElementsContainer group) {
        return getGroups().contains(group);
    }

}
