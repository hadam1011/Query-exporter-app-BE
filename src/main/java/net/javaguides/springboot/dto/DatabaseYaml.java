//package net.javaguides.springboot.dto;
//
//import jakarta.persistence.Column;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//public class DatabaseYaml {
//    public String dsn;
//    public Map<String,Object> label;
//
//    public String serviceCode;
//
//    private String hostName;
//
//
//    private String link;
//
//    private String connectSQL;
//
//    private Boolean keepConnect;
//
//    private Boolean autoCommit;
//    public DatabaseYaml() {
//    }
//
//    public DatabaseYaml(String dsn, String host, String serviceCode, ArrayList<String> label) {
//        this.dsn = dsn;
//        this.host = host;
//        this.serviceCode = serviceCode;
//        this.label = label;
//    }
//
//    public String getDsn() {
//        return dsn;
//    }
//
//    public void setDsn(String dsn) {
//        this.dsn = dsn;
//    }
//
//    public String getHost() {
//        return host;
//    }
//
//    public void setHost(String host) {
//        this.host = host;
//    }
//
//    public String getServiceCode() {
//        return serviceCode;
//    }
//
//    public void setServiceCode(String serviceCode) {
//        this.serviceCode = serviceCode;
//    }
//
//    public List<String> getLabel() {
//        return label;
//    }
//
//    public void setLabel(ArrayList<String> label) {
//        this.label = label;
//    }
//}
