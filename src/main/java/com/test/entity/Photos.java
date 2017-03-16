package com.test.entity;

/**
 * Created by yosuk on 3/16/2017.
 */
public class Photos {
    private int photoId;
    private String fileName;
    private String latitude;
    private String longitude;
    private int votes;
    private String category;

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photos photos = (Photos) o;

        if (photoId != photos.photoId) return false;
        if (votes != photos.votes) return false;
        if (fileName != null ? !fileName.equals(photos.fileName) : photos.fileName != null) return false;
        if (latitude != null ? !latitude.equals(photos.latitude) : photos.latitude != null) return false;
        if (longitude != null ? !longitude.equals(photos.longitude) : photos.longitude != null) return false;
        if (category != null ? !category.equals(photos.category) : photos.category != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = photoId;
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + votes;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }
}
