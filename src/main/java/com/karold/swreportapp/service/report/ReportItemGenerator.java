package com.karold.swreportapp.service.report;

import com.karold.swreportapp.model.Film;
import com.karold.swreportapp.model.Person;
import com.karold.swreportapp.model.Planet;
import com.karold.swreportapp.model.report.ReportItem;

public interface ReportItemGenerator {

    ReportItem generateReportItem(Planet planet, Person person, Film film);
}
