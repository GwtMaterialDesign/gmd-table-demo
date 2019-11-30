package gmd.datatable.demo.client.widget;

import gwt.material.design.client.constants.Color;

public class Dashboard {

    private String name;
    private String description;
    private String link;
    private Color color;

    public Dashboard(String name, String description, String link, Color color) {
        this.name = name;
        this.description = description;
        this.link = link;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
