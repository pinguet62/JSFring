package fr.pinguet62.jsfring.indexer.nosql;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.AndroidBrowserUserAgentSelector;
import org.yamj.api.common.http.HttpClientWrapper;

import com.moviejukebox.allocine.AllocineApi;
import com.moviejukebox.allocine.AllocineException;
import com.moviejukebox.allocine.model.MovieInfos;
import com.moviejukebox.allocine.model.MoviePerson;
import com.moviejukebox.allocine.model.PersonInfos;
import com.moviejukebox.allocine.model.Search;

import fr.pinguet62.jsfring.model.nosql.Casting;
import fr.pinguet62.jsfring.model.nosql.Movie;
import fr.pinguet62.jsfring.model.nosql.Person;

public final class Application {

    private static AllocineApi API;

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    private static String partner = "100043982026";

    private static String secret = "29d185d98c984a359e6e6f26a0474269";

    private static AllocineApi buildApi() throws AllocineException {
        DefaultHttpClient httpclient = new DefaultHttpClient();

        HttpHost proxy = new HttpHost(System.getProperty("http.proxyHost"), Integer.parseInt(System
                .getProperty("http.proxyPort")));
        httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

        HttpClientWrapper wrapper = new HttpClientWrapper(httpclient);
        wrapper.setUserAgentSelector(new AndroidBrowserUserAgentSelector());

        return new AllocineApi(partner, secret, wrapper);
    }

    private static Movie getOrCreateMovie(long code) throws Exception {
        Movie movie = null;
        if (movie != null)
            return movie;

        LOGGER.info("Init Movie: " + code);
        MovieInfos movieDto = API.getMovieInfos(String.valueOf(code));
        movie = new Movie();
        movie.setId(String.valueOf(movieDto.getCode()));
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

        return movie;
    }

    private static Person getOrCreatePerson(long code) throws Exception {
        Person person = null;
        if (person != null)
            return null;

        LOGGER.info("Init Person: " + code);
        PersonInfos personDto = API.getPersonInfos(String.valueOf(code));
        person = new Person();
        person.setId(String.valueOf(personDto.getCode()));
        person.setName(personDto.getFullName());

        return person;
    }

    public static void main(String[] args) throws Exception {
        System.setProperty("http.proxyHost", "proxy17-master.lill.fr.sopra");
        System.setProperty("http.proxyPort", "8080");

        // SpringApplication.run(SpringBootConfig.class, args);

        API = buildApi();
        test();
        run();
    }

    private static void run() throws Exception {
        Search s = API.searchMovies("toto");
        List<com.moviejukebox.allocine.model.Movie> movieDtos = s.getMovies();
        for (com.moviejukebox.allocine.model.Movie movieDto : movieDtos)
            getOrCreateMovie(movieDto.getCode());
    }

    private static void test() throws Exception {
        test_getMovieInfos();
        test_getPersonInfos();
    }

    /** @see Movie */
    private static void test_getMovieInfos() throws Exception {
        MovieInfos movieDto = API.getMovieInfos("222968");
        System.out.println(movieDto.getCode());
        System.out.println(movieDto.getTitle());
    }

    /** @see Person */
    private static void test_getPersonInfos() throws Exception {
        PersonInfos personDto = API.getPersonInfos("180667");
        System.out.println(personDto.getCode());
        System.out.println(personDto.getFullName());
        System.out.println(personDto.getBirthDate());
        System.out.println(personDto.getBiography());
    }

}