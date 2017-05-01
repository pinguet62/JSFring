package fr.pinguet62.jsfring.webapp.jsf.htmlunit.field;

public class KeyLabelIndex {

    private final int index;

    private final String key;

    private final String label;

    private final boolean selected;

    public KeyLabelIndex(int index, String key, String label, boolean selected) {
        this.index = index;
        this.key = key;
        this.label = label;
        this.selected = selected;
    }

    public int getIndex() {
        return index;
    }

    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    public boolean isSelected() {
        return selected;
    }

}