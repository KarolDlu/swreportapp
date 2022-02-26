package com.karold.swreportapp.service.report;

import com.karold.swreportapp.model.report.Report;
import com.karold.swreportapp.model.report.SimpleReportItem;

import java.util.List;

public interface ReportGenerator {

    Report generateReport(Long id, String characterPhrase, String planetName, List<SimpleReportItem> result);
}
