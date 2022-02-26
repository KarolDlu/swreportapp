package com.karold.swreportapp.service.report;

import com.karold.swreportapp.model.report.Report;

public interface ReportService {

    Report createReport(String searchPhrase, String planetName);
}
