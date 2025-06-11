package com.example.artinstitueapiapp;

import java.io.Serializable;

public class Artwork implements Serializable {
    private String title, dateDisplay, artistDisplay, mediumDisplay, artworkType, imageId, dimensions, department, creditLine, placeOfOrigin, galleryTitle, galleryId;

    public String getPlaceOfOrigin() {
        return placeOfOrigin;
    }

    public void setPlaceOfOrigin(String placeOfOrigin) {
        this.placeOfOrigin = placeOfOrigin;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDateDisplay(String dateDisplay) {
        this.dateDisplay = dateDisplay;
    }

    public void setArtistDisplay(String artistDisplay) {
        this.artistDisplay = artistDisplay;
    }

    public void setMediumDisplay(String mediumDisplay) {
        this.mediumDisplay = mediumDisplay;
    }

    public String getArtworkType() {
        return artworkType;
    }

    public void setArtworkType(String artworkType) {
        this.artworkType = artworkType;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCreditLine() {
        return creditLine;
    }

    public void setCreditLine(String creditLine) {
        this.creditLine = creditLine;
    }

    public String getGalleryTitle() {
        return galleryTitle;
    }

    public void setGalleryTitle(String galleryTitle) {
        this.galleryTitle = galleryTitle;
    }

    public String getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(String galleryId) {
        this.galleryId = galleryId;
    }

    public Artwork(String title, String dateDisplay, String artistDisplay, String mediumDisplay, String artworkType, String imageId, String dimensions, String department, String creditLine, String placeOfOrigin, String galleryTitle, String galleryId) {
        this.title = title;
        this.dateDisplay = dateDisplay;
        this.artistDisplay = artistDisplay;
        this.mediumDisplay = mediumDisplay;
        this.artworkType = artworkType;
        this.imageId = imageId;
        this.dimensions = dimensions;
        this.department = department;
        this.creditLine = creditLine;
        this.placeOfOrigin = placeOfOrigin;
        this.galleryTitle = galleryTitle;
        this.galleryId = galleryId;
    }

    public String getTitle() { return title; }
    public String getDateDisplay() { return dateDisplay; }
    public String getArtistDisplay() { return artistDisplay; }
    public String getMediumDisplay() { return mediumDisplay; }
    public String getImageUrl() { return "https://www.artic.edu/iiif/2/" + imageId + "/full/200,/0/default.jpg"; }
    public String getFullImageUrl() { return "https://www.artic.edu/iiif/2/" + imageId + "/full/843,/0/default.jpg"; }

}
