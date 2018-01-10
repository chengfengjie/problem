package problem.no.enums;

public interface BaseEnum<E extends Enum<?>, T> {
    T getCode();
    String getDisplayName();
}
