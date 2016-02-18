package fr.pinguet62.jsfring.util.reflection;

import static java.util.Objects.nonNull;

import java.lang.reflect.Field;
import java.util.function.Function;

/**
 * Get the target attribute value by reflection.
 * <p>
 * Resolve the target value by calling the <i>attribute</i> calls (not by
 * <i>getter</i> method)
 * <p>
 * Example: from {@code myObj} object and {@code "myObj.anAttr1.otherAttr"}
 * property, the method will return the result of
 * {@code myObj.anAttr1.otherAttr} call.
 */
public final class PropertyResolver implements Function<String, Object> {

    /** The source object. */
    private final Object obj;

    /**
     * Constructor.
     *
     * @param obj The source object.
     */
    public PropertyResolver(Object obj) {
        this.obj = obj;
    }

    /**
     * Apply the conversion: get the target attribute value.
     *
     * @param property The {@link String} property.
     * @return The {@link Object}.
     * @throws IllegalArgumentException Invalid property value: invalid field
     *             name, space, invalid character, ...
     * @throws NullPointerException The property is {@code null}.
     */
    @Override
    public Object apply(String property) {
        nonNull(property);
        if (property.isEmpty() || property.endsWith("."))
            throw new IllegalArgumentException("Invalid properties path: " + property);

        Object currentObject = obj;
        for (String attributeName : property.split("\\.")) {
            // Field
            Field field;
            try {
                field = currentObject.getClass().getDeclaredField(attributeName);
            } catch (NoSuchFieldException | SecurityException exception) {
                throw new IllegalArgumentException("Attribute not found: " + currentObject.getClass() + "#" + attributeName,
                        exception);
            }
            // Value
            try {
                currentObject = field.get(currentObject);
            } catch (IllegalArgumentException | IllegalAccessException exception) {
                throw new ReflectionException(
                        "Error during getting the attribute value: " + currentObject.getClass() + "#" + attributeName,
                        exception);
            }
        }
        return currentObject;
    }

}
