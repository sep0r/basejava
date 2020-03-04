package com.urise.webapp.model;

public class Resume {
    private String uuid;

    public Resume(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "com.urise.webapp.model.Resume{" +
                "uuid='" + uuid + '\'' +
                '}';
    }
}
