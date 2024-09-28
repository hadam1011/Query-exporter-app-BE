package net.javaguides.springboot.dto;

import javax.persistence.*;
import java.util.List;

public class QueryYaml {
    public int timeout;

    public int interval;

    public String[] databaseNames;

    public String[] metricNames;

    public String sql;

    public QueryYaml() {
    }

    public QueryYaml(int timeout, int interval, String[] databaseNames, String[] metricNames, String sql) {
        this.timeout = timeout;
        this.interval = interval;
        this.databaseNames = databaseNames;
        this.metricNames = metricNames;
        this.sql = sql;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String[] getDatabaseNames() {
        return databaseNames;
    }

    public void setDatabaseNames(String[] databaseNames) {
        this.databaseNames = databaseNames;
    }

    public String[] getMetricNames() {
        return metricNames;
    }

    public void setMetricNames(String[] metricNames) {
        this.metricNames = metricNames;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
