package com.cc.workflow.data;

import org.springframework.boot.actuate.trace.http.HttpTrace;

import java.util.ArrayList;

public class TraceInMem implements TraceDAO {
    private static ArrayList<HttpTrace> db = new ArrayList<>();

    @Override
    public boolean saveTrace(HttpTrace trace) {
        return db.add(trace);
    }

    @Override
    public ArrayList<HttpTrace> getAllTraces() {
        return db;
    }
}
