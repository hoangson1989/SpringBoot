package edu.miu.cs.wdfs.model;

public class NewAPIPayload {

    private String name;

    private String url;

    private String description=null;

    private String type = "NEW";

    public NewAPIPayload() {

    }

    public NewAPIPayload(String name, String url, String description) {
        this.name = name;
        this.url = url;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "\n NewAPIPayload{" +
                "\n name='" + name + '\'' +
                "\n url='" + url + '\'' +
                "\n description='" + description + '\'' +
                "\n type='" + type + '\'' +
                '}';
    }
}
