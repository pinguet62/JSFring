package fr.pinguet62.jsfring.indexer.nosql;

import static java.lang.String.valueOf;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import com.moviejukebox.allocine.AllocineApi;
import com.moviejukebox.allocine.model.MovieInfos;
import com.moviejukebox.allocine.model.MoviePerson;
import com.moviejukebox.allocine.model.PersonInfos;
import com.moviejukebox.allocine.model.Review;
import com.moviejukebox.allocine.model.Writer;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.dao.nosql.MovieDao;
import fr.pinguet62.jsfring.dao.nosql.PersonDao;
import fr.pinguet62.jsfring.dao.nosql.UserDao;
import fr.pinguet62.jsfring.model.nosql.Casting;
import fr.pinguet62.jsfring.model.nosql.Comment;
import fr.pinguet62.jsfring.model.nosql.Movie;
import fr.pinguet62.jsfring.model.nosql.Person;
import fr.pinguet62.jsfring.model.nosql.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Application implements CommandLineRunner {

    /** Convert {@link Long} to {@link ObjectId}. */
    private static ObjectId format(long code) {
        return new ObjectId(0, 0, (short) 0, (int) code);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringBootConfig.class, args);
    }

    @Inject
    private AllocineApi api;

    @Inject
    private MovieDao movieDao;

    @Inject
    private PersonDao personDao;

    @Inject
    private UserDao userDao;

    /** @see Movie */
    private Movie createMoveIfNotExists(long code) throws Exception {
        Optional<Movie> foundMovie = movieDao.findById(format(code));
        if (foundMovie.isPresent())
            return foundMovie.get();

        log.info("Init {}: {}", Movie.class.getSimpleName(), code);
        MovieInfos movieDto = api.getMovieInfos(valueOf(code));
        Movie movie = new Movie();
        movie.setId(format(movieDto.getCode()));
        movie.setReleaseDate(
                movieDto.getReleaseDate() == null ? null : new SimpleDateFormat("yyyy-MM-dd").parse(movieDto.getReleaseDate()));
        movie.setSynopsis(movieDto.getSynopsis());
        movie.setTitle(movieDto.getTitle());

        // Casting
        List<Casting> castings = new ArrayList<>();
        for (MoviePerson personDto : movieDto.getActors()) {
            Casting casting = new Casting();
            casting.setRole(personDto.getRole());
            casting.setPerson(getOrCreatePerson(personDto.getCode()));
            castings.add(casting);
        }
        movie.setCastings(castings);

        // Comments
        List<Comment> comments = new ArrayList<>();
        for (Review review : movieDto.getMovie().getHelpfulNegativeReview()) {
            Comment comment = new Comment();
            comment.setContent(review.getBody());
            comment.setRating((int) review.getRating());
            comment.setUser(getOrCreateUser(review.getWriter()));
            comments.add(comment);
        }
        movie.setComments(comments);

        return movieDao.save(movie);
    }

    /** @see Person */
    private Person getOrCreatePerson(long code) throws Exception {
        Optional<Person> existingPerson = personDao.findById(format(code));
        if (existingPerson.isPresent())
            return existingPerson.get();

        log.info("Init {}: {}", Person.class.getSimpleName(), code);
        PersonInfos personDto = api.getPersonInfos(String.valueOf(code));
        Person person = new Person();
        person.setId(format(personDto.getCode()));
        person.setName(personDto.getFullName());

        return personDao.save(person);
    }

    /** @see User */
    private User getOrCreateUser(Writer writer) {
        String pseudo = writer.getName();

        User user = userDao.findByPseudo(pseudo);
        if (user != null)
            return user;

        log.info("Init {}: {}", User.class.getSimpleName(), pseudo);
        user = new User();
        user.setPseudo(pseudo);

        return userDao.save(user);
    }

    @Override
    public void run(String... arg0) throws Exception {
        for (int code = 0; code < 5; code++) {
            MovieInfos movieDto = api.getMovieInfos(String.valueOf(code));
            if (movieDto.getMovie() != null)
                createMoveIfNotExists(movieDto.getCode());
        }
    }

}