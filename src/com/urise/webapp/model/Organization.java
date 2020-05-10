package com.urise.webapp.model;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Link link;
    private List<Position> positions = new ArrayList<>();

    public Organization(String name, String nameLink, String url, Position... positions) {
        this.name = name;
        this.link = new Link(nameLink, url);
        this.positions = Arrays.asList(positions);
    }

    @Override
    public String toString() {
        return "Organization{ " + name + " " + link + ", positions=" + positions + '}';
    }

    public static class Position implements Serializable {
        private String position;
        private YearMonth startDate;
        private YearMonth finishDate;
        private String content;

        public Position(String position, YearMonth startDate, YearMonth finishDate, String content) {
            this.position = position;
            this.startDate = startDate;
            this.finishDate = finishDate;
            this.content = content;
        }

        public String getPosition() {
            return position;
        }

        public YearMonth getStartDate() {
            return startDate;
        }

        public YearMonth getFinishDate() {
            return finishDate;
        }

        public String getContent() {
            return content;
        }

        @Override
        public String toString() {
            return position + " " + startDate + " " + finishDate + " " + content;
        }
    }
}