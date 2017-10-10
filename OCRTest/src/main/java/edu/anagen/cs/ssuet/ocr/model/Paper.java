package edu.anagen.cs.ssuet.ocr.model;

import io.realm.RealmObject;

/**
 * Created by hp on 10/8/2017.
 */

public class Paper extends RealmObject {


    private int id;
    private String name;
    private String text;
    private int complexity;
    private float weightage;


    public Paper() {


    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getComplexity() {
        return complexity;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

    public float getWeightage() {
        return weightage;
    }

    public void setWeightage(float weightage) {
        this.weightage = weightage;
    }
}
