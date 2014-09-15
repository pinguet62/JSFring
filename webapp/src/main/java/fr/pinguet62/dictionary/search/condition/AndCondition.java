package fr.pinguet62.dictionary.search.condition;

public final class AndCondition extends Condition {

    private final Condition[] conditions;

    public AndCondition(Condition[] conditions) {
        this.conditions = conditions;
    }

    public Condition[] getConditions() {
        return conditions;
    }

}
