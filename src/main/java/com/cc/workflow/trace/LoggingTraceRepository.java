package com.cc.workflow.trace;

import com.cc.workflow.data.trace.TraceDAO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoggingTraceRepository implements HttpTraceRepository {
    private static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private TraceDAO traceDAO;

    @Override
    public void add(HttpTrace trace) {
            traceDAO.saveTrace(trace);
    }

    @Override
    public List<HttpTrace> findAll() {
        return traceDAO.getAllTraces();
    }
}
