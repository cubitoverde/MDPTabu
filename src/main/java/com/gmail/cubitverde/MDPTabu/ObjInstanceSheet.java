package com.gmail.cubitverde.MDPTabu;

public class ObjInstanceSheet {
    private String name;
    private String fileName;
    private Integer[][] data;


    public ObjInstanceSheet() {

    }

    public ObjInstanceSheet(ObjInstanceSheet sheet) {
        this.name = sheet.getName();
        this.fileName = sheet.getFileName();
        this.data = sheet.getData();
    }

    public ObjInstanceSheet(String fileName) {
        this.name = fileName.substring(0, fileName.length() - 5);
        this.fileName = fileName;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer[][] getData() {
        return data;
    }

    public void setData(Integer[][] data) {
        this.data = data;
    }
}
