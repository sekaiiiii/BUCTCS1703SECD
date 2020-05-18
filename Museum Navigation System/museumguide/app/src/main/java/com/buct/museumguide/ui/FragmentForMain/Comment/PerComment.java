package com.buct.museumguide.ui.FragmentForMain.Comment;

import java.time.DateTimeException;

public class PerComment {
    private String id;
    private String time;
    private String content;
    private String exhibition_score;
    private String environment_score;
    private String service_score;
    private String name;
    private String mail_address;

    public PerComment(String _id,
                      String _time,
                      String _content,
                      String _exhibition_score,
                      String _environment_score,
                      String _service_score,
                      String _name,
                      String _mail_address){

        setContent(_content);
        setEnvironment_score(_environment_score);
        setExhibition_score(_exhibition_score);
        setId(_id);
        setMail_address(_mail_address);
        setName(_name);
        setService_score(_service_score);
        setTime(_time);

    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getEnvironment_score() {
        return environment_score;
    }

    public String getExhibition_score() {
        return exhibition_score;
    }

    public String getMail_address() {
        return mail_address;
    }

    public String getName() {
        return name;
    }

    public String getService_score() {
        return service_score;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setEnvironment_score(String environment_score) {
        this.environment_score = environment_score;
    }

    public void setExhibition_score(String exhibition_score) {
        this.exhibition_score = exhibition_score;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMail_address(String mail_address) {
        this.mail_address = mail_address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setService_score(String service_score) {
        this.service_score = service_score;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
