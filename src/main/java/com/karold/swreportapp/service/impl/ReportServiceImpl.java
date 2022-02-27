package com.karold.swreportapp.service.impl;

import com.karold.swreportapp.model.Film;
import com.karold.swreportapp.model.Person;
import com.karold.swreportapp.model.Planet;
import com.karold.swreportapp.model.report.Report;
import com.karold.swreportapp.model.report.ReportItem;
import com.karold.swreportapp.repository.SimpleReportRepo;
import com.karold.swreportapp.service.SWApiService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ReportServiceImpl {  //TODO when error handling will be added include implements ReportService

    private final SWApiService apiService;

    private final SimpleReportRepo reportRepo;

    public ReportServiceImpl(SWApiService apiService, SimpleReportRepo reportRepo) {
        this.apiService = apiService;
        this.reportRepo = reportRepo;
    }

    public void createOrUpdate(Long id, String searchPhrase, String planetName) throws IOException, InterruptedException {
        List<ReportItem> result = new ArrayList<>();

        Planet planet = apiService.getPlanetByName(planetName);
        List<Person> persons = apiService.searchPersonByPhraseAndHomeWorld(searchPhrase, planet.getUrl());
        for (Person person : persons) {
            for (String filmUrl : person.getFilms()) {
                Film film = apiService.getFilm(filmUrl);
                ReportItem item = new ReportItem(film.getId(), film.getTitle(), person.getId(),
                        person.getName(), planet.getId(), planet.getName());
                result.add(item);
            }
        }
        Report report = new Report(id, searchPhrase, planetName, result);
        reportRepo.save(report);
    }

    public Report getReportById(Long id) throws Exception {
        return reportRepo.findById(id).orElseThrow(Exception::new); //TODO throw custom error
    }

    public List<Report> getAllReports() {
        return reportRepo.findAll();
    }

    public void deleteReportById(Long id) throws Exception {
        Report report = reportRepo.findById(id).orElseThrow((Exception::new)); // TODO throw custom error
        reportRepo.delete(report);
    }

    public void deleteAllReports() {
        reportRepo.deleteAll();
    }

}
