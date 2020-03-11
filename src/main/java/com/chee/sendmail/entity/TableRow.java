package com.chee.sendmail.entity;

public class TableRow {
    private String name;
    private String content;
    private String hours;
    private String remark;

    @Override
    public String toString() {
        return "TableRow{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", hours='" + hours + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
