package fr.pinguet62.jsfring.common;

import static java.lang.String.join;
import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static java.util.Collections.shuffle;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PasswordGenerator implements Supplier<String> {

    @Override
    public String get() {
        List<String> characters = rangeClosed('a', 'z').mapToObj(c -> valueOf((char) c)).collect(toList());
        characters.addAll(rangeClosed('A', 'Z').mapToObj(c -> valueOf((char) c)).collect(toList()));
        List<String> letters = rangeClosed('0', '9').mapToObj(c -> valueOf((char) c)).collect(toList());
        List<String> specials = asList("!", "#", "$", "%", "&", "*", "+", "-", "<", "=", ">", "?", "@", "_");

        shuffle(characters);
        shuffle(letters);
        shuffle(specials);

        List<String> choosen = new ArrayList<>();
        choosen.addAll(characters.subList(0, 8));
        choosen.add(letters.get(0));
        choosen.add(specials.get(0));

        shuffle(choosen);

        return join("", choosen);
    }

}