package com.karold.swreportapp.service.report.impl;

import com.karold.swreportapp.model.report.SimpleReport;
import com.karold.swreportapp.model.report.SimpleReportItem;
import com.karold.swreportapp.service.report.ReportGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleReportGenerator implements ReportGenerator {

    @Override
    public SimpleReport generateReport(Long id, String characterPhrase, String planetName, List<SimpleReportItem> result) {
        return new SimpleReport(id, characterPhrase, planetName, result);
    }

}
