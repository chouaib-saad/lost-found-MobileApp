package com.example.myobject3;

public class CategoryModel {
    private String name;
    private int image;

    public CategoryModel(String name, int image){
        this.name=name;
        this.image=image;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image)
    {
        this.image = image;
    }

}
