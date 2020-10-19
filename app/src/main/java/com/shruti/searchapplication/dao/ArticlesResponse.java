package com.shruti.searchapplication.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

public class ArticlesResponse {

    @Expose
    @SerializedName("response")
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public static class Response {
        @Expose
        @SerializedName("docs")
        private List<Docs> docs;
        @Expose
        @SerializedName("maxScore")
        private double maxScore;
        @Expose
        @SerializedName("start")
        private int start;
        @Expose
        @SerializedName("numFound")
        private int numFound;

        public List<Docs> getDocs() {
            return docs;
        }

        public void setDocs(List<Docs> docs) {
            this.docs = docs;
        }

        public double getMaxScore() {
            return maxScore;
        }

        public void setMaxScore(double maxScore) {
            this.maxScore = maxScore;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getNumFound() {
            return numFound;
        }

        public void setNumFound(int numFound) {
            this.numFound = numFound;
        }
    }

    public static class Docs {
        @Expose
        @SerializedName("score")
        private double score;
        @Expose
        @SerializedName("title_display")
        private String title_display;
        @Expose
        @SerializedName("abstract")
        private List<String> Abstract;
        @Expose
        @SerializedName("author_display")
        private List<String> author_display;
        @Expose
        @SerializedName("article_type")
        private String article_type;
        @Expose
        @SerializedName("publication_date")
        private String publication_date;
        @Expose
        @SerializedName("eissn")
        private String eissn;
        @Expose
        @SerializedName("journal")
        private String journal;
        @Expose
        @SerializedName("id")
        private String id;

        private String AbstractData;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public String getTitle_display() {
            return title_display;
        }

        public void setTitle_display(String title_display) {
            this.title_display = title_display;
        }

        public List<String> getAbstract() {
            return Abstract;
        }

        public void setAbstract(List<String> Abstract) {
            this.Abstract = Abstract;
        }

        public List<String> getAuthor_display() {
            return author_display;
        }

        public void setAuthor_display(List<String> author_display) {
            this.author_display = author_display;
        }

        public String getArticle_type() {
            return article_type;
        }

        public void setArticle_type(String article_type) {
            this.article_type = article_type;
        }

        public String getPublication_date() {
            return publication_date;
        }

        public void setPublication_date(String publication_date) {
            this.publication_date = publication_date;
        }

        public String getEissn() {
            return eissn;
        }

        public void setEissn(String eissn) {
            this.eissn = eissn;
        }

        public String getJournal() {
            return journal;
        }

        public void setJournal(String journal) {
            this.journal = journal;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAbstractData() {
            return Arrays.toString(getAbstract().toArray(new String[0]));
        }

        public void setAbstractData(String abstractData) {
            AbstractData = abstractData;
        }
    }


}
