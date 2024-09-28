package net.javaguides.springboot.dto;

import java.util.ArrayList;

public class MetricYaml {
    public String type;
    public String description;
    public String[] label;

    public MetricYaml() {
    }

    public MetricYaml(String type, String[] label, String description) {
        this.type = type;
        this.label = label;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getLabel() {
        return label;
    }

    public void setLabel(String[] label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
