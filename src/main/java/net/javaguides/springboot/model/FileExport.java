package net.javaguides.springboot.model;

import javax.persistence.*;
import java.util.Map;

public class FileExport {

    public Map<String, Map<String,Object>> databases;

    public Map<String, Map<String,Object>> metrics;

    public Map<String, Map<String,Object>> queries;

    public FileExport() {
    }

    public FileExport(Map<String, Map<String, Object>> databases, Map<String, Map<String, Object>> metrics, Map<String, Map<String, Object>> queries) {
        this.databases = databases;
        this.metrics = metrics;
        this.queries = queries;
    }

    public Map<String, Map<String, Object>> getDatabases() {
        return databases;
    }

    public void setDatabases(Map<String, Map<String, Object>> databases) {
        this.databases = databases;
    }

    public Map<String, Map<String, Object>> getMetrics() {
        return metrics;
    }

    public void setMetrics(Map<String, Map<String, Object>> metrics) {
        this.metrics = metrics;
    }

    public Map<String, Map<String, Object>> getQueries() {
        return queries;
    }

    public void setQueries(Map<String, Map<String, Object>> queries) {
        this.queries = queries;
    }
}
