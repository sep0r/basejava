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
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Link link;
    private List<Position> positions = new ArrayList<>();

    public Organization(String name, String nameLink, String url) {
        this.name = name;
        if (nameLink == null) {
            this.link = new Link("", url);
        } else {
            this.link = new Link(nameLink, url);
        }
    }

    public Organization(String name, String nameLink, String url, List<Position> positions) {
        this(name, nameLink, url);
        this.positions = positions;
    }

    public Organization(String name, String nameLink, String url, Position... positions) {
        this(name, nameLink, url);
        this.positions = Arrays.asList(positions);
    }

    public String getName() {
        return name;
    }

    public Link getLink() {
        return link;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public Organization() {
    }

    @Override
    public String toString() {
        return "Organization{ " + name + " " + link + ", positions=" + positions + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(link, that.link) &&
                Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, link, positions);
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {
        private String position;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate finishDate;
        private String content;

        public Position() {
        }

        public Position(String position, LocalDate startDate, LocalDate finishDate, String content) {
            this.position = position;
            this.startDate = startDate;
            this.finishDate = finishDate;
            if (content == null) {
                this.content = "";
            } else {
                this.content = content;
            }
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position1 = (Position) o;
            return Objects.equals(position, position1.position) &&
                    Objects.equals(startDate, position1.startDate) &&
                    Objects.equals(finishDate, position1.finishDate) &&
                    Objects.equals(content, position1.content);
        }

        @Override
        public int hashCode() {
            return Objects.hash(position, startDate, finishDate, content);
        }
    }
}