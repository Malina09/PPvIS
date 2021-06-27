package model.interfaces;

public interface Element extends Comparable<Element> {
    String getFirstName();

    String getSecondName();

    String getPatronymic();

    String getKnownAddress();

    boolean isStudying();

}
