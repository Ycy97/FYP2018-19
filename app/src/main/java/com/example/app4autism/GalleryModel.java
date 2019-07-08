package com.example.app4autism;

public class GalleryModel {

    String name;
    int image;

    public GalleryModel(String name, int image)
    {
        this.name = name;
        this.image = image;
    }

    public String getName()
    {
        return name;
    }

    public int getImage()
    {
        return image;
    }
}
