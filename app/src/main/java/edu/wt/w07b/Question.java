package edu.wt.w07b;

public class Question {
    public String title;
    public String plot;

    @Override
    public String toString() {

        return(title);
    }

    public Question() {
    }

    public Question(String title, String link, long id) {
        this.setTitle(title);
        this.setLink(link);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getLink() {

        return plot;
    }

    public void setLink(String link) {

        this.plot = link;
    }
}

