package com.karold.swreportapp.service.report.impl;

import com.karold.swreportapp.model.Film;
import com.karold.swreportapp.model.Person;
import com.karold.swreportapp.model.Planet;
import com.karold.swreportapp.model.report.SimpleReport;
import com.karold.swreportapp.model.report.SimpleReportItem;
import com.karold.swreportapp.repository.SimpleReportRepo;
import com.karold.swreportapp.service.SWApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SimpleReportService {  //TODO when error handling will be added include implements ReportService

    private SWApiService apiService;

    private SimpleReportGenerator reportGenerator;

    private SimpleReportItemGenerator reportItemGenerator;

    private SimpleReportRepo reportRepo;

    @Autowired
    public SimpleReportService(SWApiService apiService, SimpleReportGenerator reportGenerator, SimpleReportItemGenerator reportItemGenerator, SimpleReportRepo reportRepo) {
        this.apiService = apiService;
        this.reportGenerator = reportGenerator;
        this.reportItemGenerator = reportItemGenerator;
        this.reportRepo = reportRepo;
    }

    public SimpleReport createOrUpdate(Long id, String searchPhrase, String planetName) throws IOException, InterruptedException {

        List<SimpleReportItem> result = new ArrayList<>();

        Planet planet = apiService.getPlanetByName(planetName);
        List<Person> persons = apiService.searchPersonByPhraseAndHomeWorld("a", planet.getUrl());
        for (Person person : persons) {
            for (String filmUrl : person.getFilms()) {
                Film film = apiService.getFilm(filmUrl);
                result.add(reportItemGenerator.generateReportItem(planet, person, film));
            }
        }
        return reportRepo.save(reportGenerator.generateReport(id, searchPhrase, planetName, result));
    }

    public SimpleReport getReportById(Long id) throws Exception {
        return reportRepo.findById(id).orElseThrow(Exception::new); //TODO throw custom error
    }

    public List<SimpleReport> getAllReports() {
        return reportRepo.findAll();
    }

    public boolean deleteReportById(Long id) throws Exception {
        SimpleReport report = reportRepo.findById(id).orElseThrow((Exception::new)); // TODO throw custom error
        reportRepo.delete(report);
        return true;
    }

}
