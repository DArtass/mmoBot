package com.wowBot.wowBot.aboutMe;

public class AboutMe {
    private String info, link, type;

    public AboutMe() {
    }

    public AboutMe(String info, String link, String type) {
        this.info = info;
        this.link = link;
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getType() {
        return type;
    }

    public void setType(String info) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
