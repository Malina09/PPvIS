package model.impl;

import model.interfaces.ElementsContainer;

import java.util.LinkedHashSet;

class CourseLinkedList extends LinkedHashSet<ElementsContainer> {
    private final int courseNumber;

    public CourseLinkedList(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    public int getCourseNumber() {
        return courseNumber;
    }
}
