package fr.pinguet62.jsfring.dao.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PasswordGenerator implements Supplier<String> {

    @Override
    public String get() {
        List<String> characters = IntStream.rangeClosed('a', 'z').mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.toList());
        characters.addAll(IntStream.rangeClosed('A', 'Z').mapToObj(c -> String.valueOf((char) c)).collect(Collectors.toList()));
        List<String> letters = IntStream.rangeClosed('0', '9').mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.toList());
        List<String> specials = Arrays.asList("!", "#", "$", "%", "&", "*", "+", "-", "<", "=", ">", "?", "@", "_");

        Collections.shuffle(characters);
        Collections.shuffle(letters);
        Collections.shuffle(specials);

        List<String> choosen = new ArrayList<>();
        choosen.addAll(characters.subList(0, 8));
        choosen.add(letters.get(0));
        choosen.add(specials.get(0));

        Collections.shuffle(choosen);

        return String.join("", choosen);
    }

}