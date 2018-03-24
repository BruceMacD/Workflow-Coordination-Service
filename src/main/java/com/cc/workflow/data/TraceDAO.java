package com.cc.workflow.data;

import org.springframework.boot.actuate.trace.http.HttpTrace;

import java.util.ArrayList;

public interface TraceDAO {
    boolean saveTrace(HttpTrace trace);
    ArrayList<HttpTrace> getAllTraces();
}
