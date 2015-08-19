package fr.pinguet62.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Generate all combinations of a {@link List} of objetcs.
 *
 * @param <T> The object type.
 */
public final class Combinator<T> implements Supplier<List<List<T>>> {

    private final List<T> src;

    private final List<List<T>> values = new ArrayList<>();

    public Combinator(List<T> src) {
        this.src = src;
    }

    /**
     * @param fil The already elements iterated.
     * @param rest The (unordered) next elements on iterate.
     */
    private void combine(List<T> fil, List<T> rest) {
        int size = rest.size();

        if (size == 0) {
            values.add(fil);
            return;
        }

        List<T> newRest = rest.subList(1, size);
        for (int i = 0; i < size; i++) {
            List<T> newFil = new ArrayList<>(fil);
            T elem = rest.get(i);
            newFil.add(elem);

            combine(newFil, newRest);
        }
    }

    @Override
    public List<List<T>> get() {
        if (values.isEmpty())
            combine(new ArrayList<>(), src);

        return values;
    }

}