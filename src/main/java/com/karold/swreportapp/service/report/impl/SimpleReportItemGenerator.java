package com.karold.swreportapp.service.report.impl;

import com.karold.swreportapp.model.Film;
import com.karold.swreportapp.model.Person;
import com.karold.swreportapp.model.Planet;
import com.karold.swreportapp.model.report.SimpleReportItem;
import com.karold.swreportapp.service.report.ReportItemGenerator;
import org.springframework.stereotype.Service;

@Service
public class SimpleReportItemGenerator implements ReportItemGenerator {

    @Override
    public SimpleReportItem generateReportItem(Planet planet, Person person, Film film) {
        return new SimpleReportItem(film.getId(), film.getTitle(), person.getId(), person.getName(), planet.getId(), planet.getName());
    }

}
