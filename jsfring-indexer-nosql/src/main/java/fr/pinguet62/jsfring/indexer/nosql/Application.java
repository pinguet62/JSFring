package fr.pinguet62.jsfring.indexer.nosql;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import com.moviejukebox.allocine.AllocineApi;
import com.moviejukebox.allocine.model.MovieInfos;
import com.moviejukebox.allocine.model.MoviePerson;
import com.moviejukebox.allocine.model.PersonInfos;
import com.moviejukebox.allocine.model.Search;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.dao.nosql.MovieDao;
import fr.pinguet62.jsfring.dao.nosql.PersonDao;
import fr.pinguet62.jsfring.model.nosql.Casting;
import fr.pinguet62.jsfring.model.nosql.Movie;
import fr.pinguet62.jsfring.model.nosql.Person;

@Component
public class Application implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    /** Convert {@link Long} to {@link ObjectId}. */
    private static ObjectId format(long code) {
        return new ObjectId(0, 0, (int) code);
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

    private Movie getOrCreateMovie(long code) throws Exception {
        Movie movie = movieDao.findOne(format(code));
        if (movie != null)
            return movie;

        LOGGER.info("Init Movie: " + code);
        MovieInfos movieDto = api.getMovieInfos(String.valueOf(code));
        movie = new Movie();
        movie.setId(format(movieDto.getCode()));
        movie.setReleaseDate(movieDto.getReleaseDate() == null ? null : new SimpleDateFormat("yyyy-MM-dd").parse(movieDto
                .getReleaseDate()));
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
        movieDao.save(movie);

        return movie;
    }

    private Person getOrCreatePerson(long code) throws Exception {
        Person person = personDao.findOne(format(code));
        if (person != null)
            return null;

        LOGGER.info("Init Person: " + code);
        PersonInfos personDto = api.getPersonInfos(String.valueOf(code));
        person = new Person();
        person.setId(format(personDto.getCode()));
        person.setName(personDto.getFullName());
        personDao.save(person);

        return person;
    }

    @Override
    public void run(String... arg0) throws Exception {
        Search s = api.searchMovies("toto");
        List<com.moviejukebox.allocine.model.Movie> movieDtos = s.getMovies();
        for (com.moviejukebox.allocine.model.Movie movieDto : movieDtos)
            getOrCreateMovie(movieDto.getCode());
    }

    /** @see Movie */
    private void test_getMovieInfos() throws Exception {
        MovieInfos movieDto = api.getMovieInfos("222968");
        System.out.println(movieDto.getCode());
        System.out.println(movieDto.getTitle());
        System.out.println(movieDto.getTitle());
    }

    /** @see Person */
    private void test_getPersonInfos() throws Exception {
        PersonInfos personDto = api.getPersonInfos("180667");
        System.out.println(personDto.getCode());
        System.out.println(personDto.getFullName());
        System.out.println(personDto.getBirthDate());
        System.out.println(personDto.getBiography());
    }

}