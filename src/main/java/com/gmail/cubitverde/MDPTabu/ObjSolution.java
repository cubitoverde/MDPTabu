package com.gmail.cubitverde.MDPTabu;

import java.util.List;

public class ObjSolution {
    private String instance;
    private List<Integer> elements;
    private int value;


    public ObjSolution() {
    }

    public ObjSolution(ObjSolution solution) {
        this.instance = solution.getInstance();
        this.elements = solution.getElements();
        this.value = solution.getValue();
    }

    public ObjSolution(String instance) {
        this.instance = instance;
        this.value = 0;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public List<Integer> getElements() {
        return elements;
    }

    public void setElements(List<Integer> elements) {
        this.elements = elements;
    }
}
