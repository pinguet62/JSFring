package fr.pinguet62.jsfring.gui.component.filter;

public enum Operator {

    BETWEEN(2, "between"), EQUALS_TO(1, "equals to"), GREATER_THAN(1,
            "greater than"), IS_NULL(0, "is null"), LESS_THAN(1, "less than");

    public static Operator fromMessage(String value) {
        for (Operator operator : values())
            if (operator.message.equals(value))
                return operator;
        throw new IllegalArgumentException("Unknown operator: " + value);
    }

    private final String message;

    private final int numberOfParameters;

    private Operator(int numberOfParameters, String message) {
        this.numberOfParameters = numberOfParameters;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getNumberOfParameters() {
        return numberOfParameters;
    }

}