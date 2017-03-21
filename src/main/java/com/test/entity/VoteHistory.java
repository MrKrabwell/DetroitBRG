package com.test.entity;

import java.sql.Timestamp;

/**
 * Created by yosuk on 3/20/2017.
 */
public class VoteHistory {
    private int historyId;
    private int photoId;
    private String userId;
    private Byte upvote;
    private Timestamp timestamp;
    private Integer totalVotes;

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Byte getUpvote() {
        return upvote;
    }

    public void setUpvote(Byte upvote) {
        this.upvote = upvote;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(Integer totalVotes) {
        this.totalVotes = totalVotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VoteHistory that = (VoteHistory) o;

        if (historyId != that.historyId) return false;
        if (photoId != that.photoId) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (upvote != null ? !upvote.equals(that.upvote) : that.upvote != null) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;
        if (totalVotes != null ? !totalVotes.equals(that.totalVotes) : that.totalVotes != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = historyId;
        result = 31 * result + photoId;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (upvote != null ? upvote.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (totalVotes != null ? totalVotes.hashCode() : 0);
        return result;
    }
}
