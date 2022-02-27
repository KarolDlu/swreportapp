package com.karold.swreportapp.service;

import com.karold.swreportapp.model.report.Report;

public interface ReportService {

    Report createReport(String searchPhrase, String planetName);
}
