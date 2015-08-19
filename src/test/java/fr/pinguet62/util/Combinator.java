package fr.pinguet62.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * Generate all combinations of a {@link List} of objects.
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

        for (int i = 0; i < size; i++) {
            List<T> newFil = new ArrayList<>(fil);

            // Put the next element in the 1st position
            Collections.swap(rest, 0, i);
            // Move next element to "fil"
            T elem = rest.get(0);
            List<T> newRest = rest.subList(1, size);
            newFil.add(elem);

            // Continue iteration of tree
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