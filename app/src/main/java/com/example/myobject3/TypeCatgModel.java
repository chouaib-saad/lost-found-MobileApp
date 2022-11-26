package com.example.myobject3;

public class TypeCatgModel {
    public String category;
    public String description;
    public String id;
    public String location;
    public String image;
    public String name;
    public String objectName;



    public TypeCatgModel(){}

    public TypeCatgModel( String category,String description,String id,String location,String image,String name,String objectName){

        this.category=category;
        this.description=description;
        this.id=id;
        this.location=location;
        this.image=image;
        this.name=name;
        this.objectName=objectName;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getObjectName() {
        return objectName;
    }
}
