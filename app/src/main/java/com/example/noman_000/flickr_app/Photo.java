package com.example.noman_000.flickr_app;


import java.io.Serializable;

class Photo implements Serializable{
    private static final long serialVersionUID= 1L;
    private String title;
    private String author;
    private String authorId;
    private String link;
    private String imageUrl;
    private String tags;

    public Photo(String title, String author, String authorId, String link, String imageUrl, String tags) {
        this.title = title;
        this.author = author;
        this.authorId = authorId;
        this.link = link;
        this.imageUrl = imageUrl;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getLink() {
        return link;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "title : " + title + "\n" + "author : " + author;
    }

}
