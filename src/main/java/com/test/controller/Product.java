package com.test.controller;

import com.sun.istack.internal.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

import static javax.swing.text.StyleConstants.Size;

/**
 * Created by yosuk on 3/13/2017.
 */
public class Product implements Serializable {

    private static final long serialVersionUID = 74458L;

    @NotNull
    //@Size(min=1, max=10)
    private String name;
    private String description;

    private List<MultipartFile> images;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<MultipartFile> getImages() {
        return images;
    }
    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }
}
