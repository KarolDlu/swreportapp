package com.karold.swreportapp.service;

import com.karold.swreportapp.exception.JsonToObjectMappingException;
import com.karold.swreportapp.exception.ResultsCountNotEqualsOneException;
import com.karold.swreportapp.model.Film;
import com.karold.swreportapp.model.Person;
import com.karold.swreportapp.model.Planet;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SWApiServiceTest {

    @Mock
    private CustomHttpClient httpClient;

    @InjectMocks
    private SWApiService apiService;

    private static final String BASE_URL = "http://localhost:8080/api";

    @Test
    public void getFilm_ShouldReturnApiResponse_WhenJsonIsValid() {
        when(httpClient.getWithCustomUrl(BASE_URL + "/films/1/"))
                .thenReturn("{\"title\":\"A New Hope\", \"url\":\"" + BASE_URL + "/films/1/\"}");

        Film film = apiService.getFilm(BASE_URL + "/films/1/");
        Assert.assertEquals(new Film("A New Hope", BASE_URL + "/films/1/"), film);
    }

    @Test
    public void getFilm_ShouldThrowException_WhenCanNotMapJsonToObject() {
        when(httpClient.getWithCustomUrl(BASE_URL + "/films/1/"))
                .thenReturn("{\"title\":'The Empire Strikes Back\", \"url\":\"" + BASE_URL + "/films/2/\"}");

        Assert.assertThrows(JsonToObjectMappingException.class,
                () -> apiService.getFilm(BASE_URL + "/films/2/"));
    }

    @Test
    public void getPlanetByName_ShouldReturnPlanet_WhenFullPlanetNameGiven() {
        when(httpClient.get("/planets/", "search=naboo"))
                .thenReturn("{" + "\"count\":\"1\","
                        + "\"results\":"
                        + "[{\"name\":\"Naboo\", "
                        + "\"url\":\"" + BASE_URL + "/planets/3/\"}]}");
        Assert.assertEquals(new Planet("Naboo", BASE_URL + "/planets/3/"),
                apiService.getPlanetByName("naboo"));
    }

    @Test
    public void getPlanetByName_ShouldThrowException_WhenCanNotMapJsonToObject() {
        when(httpClient.get("/planets/", "search=hoth"))
                .thenReturn("{" + "\"count\":\"1\","
                        + "'results\":"
                        + "[{\"name\":\"Hoth\", "
                        + "\"url\":\"" + BASE_URL + "/planets/4/\"}]}");

        Assert.assertThrows(JsonToObjectMappingException.class, () -> apiService.getPlanetByName("hoth"));
    }

    @Test
    public void getPlanetByName_ShouldThrowException_WhenResultsCountNotEqualsOne() {
        when(httpClient.get("/planets/", "search=o"))
                .thenReturn("{\"count\":\"3\","
                        + "\"results\":"
                        + "[{\"name\":\"Tatooine\", \"url\":\"" + BASE_URL + "/films/1/\"},"
                        + "{\"name\":\"Naboo\", \"url\":\"" + BASE_URL + "/films/3/\"},"
                        + "{\"name\":\"Hoth\", \"url\":\"" + BASE_URL + "/planets/4/\"}]}");
        Assert.assertThrows(ResultsCountNotEqualsOneException.class, () -> apiService.getPlanetByName("o"));
    }

    @Test
    public void searchPersonByPhraseAndHomeWorld_ShouldReturnPersonsWithGivenPhraseAndHomeWorld() {
        when(httpClient.get("/people/", "search=a"))
                .thenReturn("{\"count\":\"3\",\"next\": null,"
                        + "\"results\":"
                        + "[{\"name\":\"Anakin Skwalker\", \"homeworld\":\"" + BASE_URL + "/planets/1/\", "
                        + "\"films\":[],"
                        + "\"url\": \"" + BASE_URL + "/people/1/\"},"
                        + "{\"name\":\"Luke Skywalker\", \"homeworld\":\"" + BASE_URL + "/planets/1/\","
                        + "\"films\":[],"
                        + "\"url\": \"" + BASE_URL + "/people/2/\"},"
                        + "{\"name\":\"Leia Organa\", \"homeworld\":\"" + BASE_URL + "/planets/2/\","
                        + "\"films\":[],"
                        + " \"url\": \"" + BASE_URL + "/people/5/\"}]}"
                );
        List<Person> expected = new ArrayList<>();
        expected.add(new Person("Leia Organa", BASE_URL + "/planets/2/",
                new ArrayList<>(), BASE_URL + "/people/5/"));
        List<Person> actual = apiService.searchPersonByPhraseAndHomeWorld("a",
                BASE_URL + "/planets/2/");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void searchPersonByPhraseAndHomeWorld_ShouldThrowExceptionWhenCanNotMapJsonToObject() {
        when(httpClient.get("/people/", "search=a")).thenReturn("{invalid json}");
        Assert.assertThrows(JsonToObjectMappingException.class, () -> apiService.searchPersonByPhraseAndHomeWorld("a", BASE_URL + "/planets/1/"));
    }
}
