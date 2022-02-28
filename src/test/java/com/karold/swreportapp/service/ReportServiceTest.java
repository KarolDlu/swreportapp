package com.karold.swreportapp.service;

import com.karold.swreportapp.exception.ResourceNotFoundException;
import com.karold.swreportapp.model.Film;
import com.karold.swreportapp.model.Person;
import com.karold.swreportapp.model.Planet;
import com.karold.swreportapp.model.report.Report;
import com.karold.swreportapp.model.report.ReportItem;
import com.karold.swreportapp.repository.ReportRepo;
import com.karold.swreportapp.service.impl.ReportServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ReportServiceTest {

    @Mock
    private ReportRepo reportRepo;

    @Mock
    private SWApiService swApiService;

    @InjectMocks
    private ReportServiceImpl reportService;

    @Test
    public void createOrUpdate_shouldCreateReport() {
        List<Person> persons = new ArrayList<>();
        List<String> films = new ArrayList<>();


        films.add("http://localhost:8080/api/films/1/");
        films.add("http://localhost:8080/api/films/2/");
        persons.add(new Person("Anakin Skywalker", "http://localhost:8080/api/planets/1/",
                films, "http://localhost:8080/api/people/2/"));
        List<String> filmsForSecondPerson = new ArrayList<>(films);
        filmsForSecondPerson.add("http://localhost:8080/api/films/3/");
        persons.add(new Person("Luke Skywalker", "http://localhost:8080/api/planets/1/",
                filmsForSecondPerson, "http://localhost:8080/api/people/1/"));

        when(swApiService.getPlanetByName("tatooine")).thenReturn(new Planet("Tatooine",
                "http://localhost:8080/api/planets/1/"));
        when(swApiService.searchPersonByPhraseAndHomeWorld(
                "skywalker", "http://localhost:8080/api/planets/1/"))
                .thenReturn(persons);
        when(swApiService.getFilm("http://localhost:8080/api/films/1/"))
                .thenReturn(new Film("A New Hope", "http://localhost:8080/api/films/1/"));
        when(swApiService.getFilm("http://localhost:8080/api/films/2/"))
                .thenReturn(new Film("The Empire Strikes Back", "http://localhost:8080/api/films/2/"));
        when(swApiService.getFilm("http://localhost:8080/api/films/3/"))
                .thenReturn(new Film("Return of the Jedi", "http://localhost:8080/api/films/3/"));

        List<ReportItem> items = new ArrayList<>();
        items.add(new ReportItem("1", "A New Hope", "2",
                "Anakin Skywalker", "1", "Tatooine"));
        items.add(new ReportItem("2", "The Empire Strikes Back", "2",
                "Anakin Skywalker", "1", "Tatooine"));
        items.add(new ReportItem("1", "A New Hope", "1",
                "Luke Skywalker", "1", "Tatooine"));
        items.add(new ReportItem("2", "The Empire Strikes Back", "1",
                "Luke Skywalker", "1", "Tatooine"));
        items.add(new ReportItem("3", "Return of the Jedi", "1",
                "Luke Skywalker", "1", "Tatooine"));
        Report expected = new Report(1L, "skywalker", "tatooine", items);

        reportService.createOrUpdate(1L, "skywalker", "tatooine");
        ArgumentCaptor<Report> argumentCaptor = ArgumentCaptor.forClass(Report.class);
        verify(reportRepo).save(argumentCaptor.capture());
        Report actual = argumentCaptor.getValue();
        Assert.assertEquals(expected, actual);


    }

    @Test
    public void getReportById_shouldReturnReportIfExist() {
        Report expected = new Report(1L, "skywalker", "tatooine", new ArrayList<>());
        when(reportRepo.findById(1L)).thenReturn(Optional.of(expected));
        Report report = reportService.getReportById(1L);
        Assert.assertEquals(expected, report);
    }

    @Test
    public void getReportById_shouldThrowExceptionIfReportNotExist() {
        when(reportRepo.findById(1L)).thenReturn(Optional.empty());
        Assert.assertThrows(ResourceNotFoundException.class, () -> reportService.getReportById(1L));
    }

    @Test
    public void getAllReports_shouldReturnAllReports() {
        List<Report> expected = new ArrayList<>();
        expected.add(new Report(1L, "jar", "naboo", new ArrayList<>()));
        expected.add(new Report(2L, "Luke", "tatooi", new ArrayList<>()));
        expected.add(new Report(3L, "Vader", "Hoth", new ArrayList<>()));
        when(reportRepo.findAll()).thenReturn(expected);
        List<Report> result = reportService.getAllReports();
        Assert.assertEquals(expected, result);
    }

    @Test
    public void deleteReportById_shouldThrowExceptionIfReportNotExist() {
        when(reportRepo.findById(1L)).thenReturn(Optional.empty());
        Assert.assertThrows(ResourceNotFoundException.class, () -> reportService.deleteReportById(1L));
    }
}
