import actors.Secretary;
import model.impl.Database;
import model.impl.Group;
import model.impl.Student;
import model.interfaces.Element;
import model.interfaces.ElementsContainer;
import model.interfaces.Model;

import java.util.Arrays;

public class Main {
    private final Element[] STUDENTS = {
            new Student("Dennis", "Ritchie", "A", "addr"),
            new Student("Richard", "Stallman", "B", "addr1"),
            new Student("Linus", "Torvalds", "C", "addr2"),
            new Student("Ada", "Lovelace", "H", "addr"),
            new Student("Ada", "Lovelace", "H", "addr"),
            new Student("TestAda", "Lovelace", "Hhh", "addrrr"),
            new Student("John", "Von neumann", "G", "addr2"),
            new Student("Alan", "Turing", "D", "addr1"),
            new Student("Alonzo", "Church", "F", "addr1"),
            new Student("Kevin", "Mitnick", "E", "addr2"),
            new Student("Robert", "Morris", "N", "addr1"),
            new Student("404", "not found", "null", "everywhere")
    };

    private final ElementsContainer[] GROUPS = {
            new Group("programmers", 1),
            new Group("hackers", 2),
            new Group("science", 3),
            new Group("other", 1),
            new Group("for_deleting", 2)
    };


    public static void main(String[] args) {
        Main main = new Main();
        Model database = new Database();
        main.testSecretary(database);
    }

    public void testSecretary(Model database) {
        Secretary secretary = new Secretary(database);
        System.out.println("***SECRETARY TEST STARTED***");
        testSecretaryCreateGroup(secretary);
        print(database);
        testSecretaryRemoveGroup(secretary);
        print(database);
        testSecretaryAddStudentToTheGroup(secretary);
        print(database);
        testSecretaryGetStudentsByCourse(secretary);
        print(database);
        System.out.println("***SECRETARY TEST ENDED***");
    }

    public void testSecretaryCreateGroup(Secretary secretary) {
        System.out.println("\tSECRETARY CREATE GROUP TEST STARTED");
        for (ElementsContainer group : GROUPS) {
            secretary.creteGroup(group);
        }
        System.out.println("\tSECRETARY CREATE GROUP TEST ENDED");
    }

    public void testSecretaryRemoveGroup(Secretary secretary) {
        System.out.println("\tSECRETARY REMOVE GROUP TEST STARTED");
        secretary.removeGroup(GROUPS[GROUPS.length - 1]);
        System.out.println("\tSECRETARY REMOVE GROUP TEST ENDED");
    }

    public void testSecretaryAddStudentToTheGroup(Secretary secretary) {
        System.out.println("\tSECRETARY ADD STUDENT TO THE GROUP TEST STARTED");
        for (int i = 0, j = 0; i < STUDENTS.length; i++, j++) {
            secretary.addStudentToTheGroup(STUDENTS[i], GROUPS[j]);
            if (j == GROUPS.length - 2)
                j = 0;
        }

        System.out.println("\tSECRETARY ADD STUDENT TO THE GROUP TEST ENDED");
    }

    public void testSecretaryGetStudentsByCourse(Secretary secretary) {
        System.out.println("\tSECRETARY GET STUDENTS TEST STARTED");
        System.out.println("For course number = 1:");
        secretary.getStudentsByCourse(1).forEach(e->{
            System.out.println(studentToString(e));
        });
        System.out.println(Arrays.toString(secretary.getStudentsByCourse(1).toArray()));
        System.out.println("\tSECRETARY GET STUDENTS TEST ENDED");
    }

    public void print(Model database) {
        System.out.println("Database{ ");
        for (ElementsContainer group : database.getGroups()) {
            System.out.println("\t" + "Group: group number = " + group.getGroupName() + "; course number = " + group.getCourseNumber() + "{");
            for (Element student : group.getStudentsList()) {
                System.out.println("\t\t"+studentToString(student));
            }
            System.out.println("\t}");
        }
        System.out.println("\tDon't study{");
        for (Element student : database.getNotStudents()) {
            System.out.println("\t\t"+studentToString(student));
        }
        System.out.println("\t}");
        System.out.println("}");


    }

    public String studentToString(Element student){
        return  "" + "Student: first name = " + student.getFirstName() +
                "; second name = " + student.getSecondName() +
                "; patronymic = " + student.getPatronymic() +
                "; address = " + student.getKnownAddress() +
                "; isStudy = " + (student.isStudying() ? "yes" : "no");
    }
}
