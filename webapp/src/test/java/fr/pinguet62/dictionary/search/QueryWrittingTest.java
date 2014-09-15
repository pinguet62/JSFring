package fr.pinguet62.dictionary.search;

import org.junit.Test;

import fr.pinguet62.dictionary.model.Profile;
import fr.pinguet62.dictionary.model.Profile_;
import fr.pinguet62.dictionary.model.Right;
import fr.pinguet62.dictionary.model.Right_;
import fr.pinguet62.dictionary.search.condition.Condition;
import fr.pinguet62.dictionary.search.function.Function;

/**
 * Test the writing of queries.
 * <p>
 * Validates the return types.
 */
public final class QueryWrittingTest {

    @Test
    public void count() {
        Result<Right, Long> result = Query.From(Right.class)
                                          .where(Condition.Equals(Right_.code, "CODE"))
                                          .select(Function.Count());
    }

    @Test
    public void list() {
        Result<Right, Right> result = Query.From(Right.class)
                                           .select();
    }

    @Test
    public void single() {
        Result<Profile, String> result = Query.From(Profile.class)
                                              .where(Condition.Equals(Profile_.id, 1))
                                              .select(Profile_.title);
    }

    @Test
    public void subQuery() {
        Result<Profile, Profile> result = Query.From(Profile.class)
                                               .where( Condition.Equals( Profile_.id,
                                                                         Query.From(Profile.class)
                                                                              .select(Function.Count())))
                                               .select();
    }
    
    @Test
    public void join() {
        
    }

}
