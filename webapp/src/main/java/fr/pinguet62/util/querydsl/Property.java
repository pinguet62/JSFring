package fr.pinguet62.util.querydsl;

import java.lang.reflect.Field;

import com.mysema.query.types.expr.SimpleExpression;
import com.mysema.query.types.path.EntityPathBase;

import fr.pinguet62.dictionary.model.QProfile;
import fr.pinguet62.util.querydsl.Filter.FilterException;

/** Utils for meta-model properties. */
public final class Property {

    /**
     * The meta object.<br/>
     * For example {@link QProfile#profile}.
     */
    private final EntityPathBase<?> meta;

    /**
     * Constructor.
     *
     * @param meta
     *            The The meta object.
     */
    public Property(EntityPathBase<?> meta) {
        this.meta = meta;
    }

    /**
     * Get the value corresponding to the attribute.
     * <p>
     * For example, from {@code meta} and {@code attr.subAttr}, the method will
     * return the value of attribute {@code meta.attr.subAttr}.
     *
     * @param property
     *            The {@link String} property.
     * @return The {@link SimpleExpression}.
     * @throws IllegalArgumentException
     *             Bad property value.
     * @throws NullPointerException
     *             The parameter is {@code null}.
     */
    public SimpleExpression<?> getAttribute(String property) {
        SimpleExpression<?> targetAttribute = meta;
        for (String attributeName : property.split("\\.")) {
            // Field
            Field field;
            try {
                field = targetAttribute.getClass().getDeclaredField(
                        attributeName);
            } catch (NoSuchFieldException | SecurityException exception) {
                throw new IllegalArgumentException("Attribute not found: "
                        + targetAttribute.getClass() + "#" + attributeName,
                        exception);
            }
            // Value
            try {
                targetAttribute = (SimpleExpression<?>) field
                        .get(targetAttribute);
            } catch (IllegalArgumentException | IllegalAccessException exception) {
                throw new FilterException(
                        "Error during getting the attribute value: "
                                + targetAttribute.getClass() + "#"
                                + attributeName, exception);
            }
        }
        return targetAttribute;
    }

}
