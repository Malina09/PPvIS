package util;

import java.util.Objects;

public final class Pair<T1, T2>{
    private final T1 firstElement;
    private final T2 secondElement;

    public Pair(T1 firstElement, T2 secondElement) {
        this.firstElement = firstElement;
        this.secondElement = secondElement;
    }

    public T1 getFirstElement() {
        return firstElement;
    }

    public T2 getSecondElement() {
        return secondElement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(firstElement, pair.firstElement) && Objects.equals(secondElement, pair.secondElement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstElement, secondElement);
    }
}
