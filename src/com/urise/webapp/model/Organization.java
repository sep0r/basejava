package com.urise.webapp.model;

import com.urise.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Link link;
    private List<Position> positions = new ArrayList<>();

    public Organization() {
    }

    public Organization(String name, String nameLink, String url, Position... positions) {
        this.name = name;
        this.link = new Link(nameLink, url);
        this.positions = Arrays.asList(positions);
    }

    @Override
    public String toString() {
        return "Organization{ " + name + " " + link + ", positions=" + positions + '}';
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {
        private String position;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate finishDate;
        private String content;

        public Position(String position, LocalDate startDate, LocalDate finishDate, String content) {
            this.position = position;
            this.startDate = startDate;
            this.finishDate = finishDate;
            this.content = content;
        }

        public Position() {
        }

        public String getPosition() {
            return position;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getFinishDate() {
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