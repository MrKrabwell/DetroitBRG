package com.test.models;

import javax.persistence.*;

/**
 * Created by yosuk on 3/11/2017.
 */
@Entity
@Table(name = "Photos", schema = "DetroitBRG", catalog = "")
public class PhotosEntity {
    private int photoId;
    private String fileName;
    private String latitude;
    private String longitude;
    private int votes;

    @Id
    @Column(name = "photoID", nullable = false)
    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    @Basic
    @Column(name = "fileName", nullable = true, length = 100)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "latitude", nullable = true, length = 30)
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude", nullable = true, length = 30)
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "votes", nullable = false)
    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhotosEntity that = (PhotosEntity) o;

        if (photoId != that.photoId) return false;
        if (votes != that.votes) return false;
        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) return false;
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = photoId;
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + votes;
        return result;
    }
}
