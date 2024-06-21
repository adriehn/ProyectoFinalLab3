package org.example.entity;

public class Messages {
    private String content;
    private boolean read;

    public Messages(String content) {
        this.content = content;
        this.read = false;
    }

    public String getContent() {
        return content;
    }

    public boolean isRead() {
        return read;
    }

    public void Mark_AsRead() {
        this.read = true;
    }
}
