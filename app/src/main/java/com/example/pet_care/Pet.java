package com.example.pet_care;

public class Pet {
    private int id;
    private String name;
    private String type;
    private String sex;
    private String age;
    private String note;
    private String ownerEmail;

    public Pet(int id, String name, String type, String sex, String age, String note, String ownerEmail) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.sex = sex;
        this.age = age;
        this.note = note;
        this.ownerEmail = ownerEmail;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getSex() {
        return sex;
    }

    public String getAge() {
        return age;
    }

    public String getNote() {
        return note;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }
}
