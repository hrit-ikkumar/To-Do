package com.example.to_do.Model;

public class ToDoTask {
    public String task;
    public String key;

    public ToDoTask()
    {}


    public ToDoTask(String name, String key) {
        this.task = name;

        this.key = key;
    }

    public ToDoTask(String name) {
        this.task = name;
    }

    public String getName() {
        return task;
    }

    public void setName(String name) {
        this.task = name;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
