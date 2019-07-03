package edu.wt.w07b;

public class Question {
    public String fullplot;
    public String plot;

    @Override
    public String toString() {

        return(fullplot);
    }

    public Question() {
    }

    public Question(String fullplot, String link, long id) {
        this.setTitle(fullplot);
        this.setLink(link);
    }

    public String getTitle() {

        return fullplot;
    }

    public void setTitle(String title) {

        this.fullplot = title;
    }

    public String getLink() {

        return plot;
    }

    public void setLink(String link) {

        this.plot = link;
    }
}

