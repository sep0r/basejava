package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResumeServlet extends HttpServlet {
    private Storage storage; //= Config.getINSTANCE().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getINSTANCE().getStorage();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r;
        if (uuid.equals("")) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContact().remove(type);
            }
        }
        Map<String, String[]> parameterMap = request.getParameterMap();
        int countPosition = 0;
        int countLink = 0;
        for (SectionType typeSection : SectionType.values()) {
            String value = request.getParameter(typeSection.name());
            if (value != null) {
                List<String> textList = Arrays.stream(request.getParameterValues(typeSection.name()))
                        .filter((s) -> s.trim().length() != 0 && !s.equals("#exist#"))
                        .collect(Collectors.toList());
                if (textList.isEmpty()) {
                    r.getTextSection().remove(typeSection);
                } else
                    switch (typeSection) {
                        case PERSONAL:
                        case OBJECTIVE:
                            r.addSection(typeSection, new TextSection(value));
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            r.addSection(typeSection, new ListSection(textList));
                            break;
                        case EDUCATION:
                        case EXPERIENCE:
                            String[] arrayOrganization = parameterMap.get(typeSection.name());
                            List<Organization> listOrganization = new ArrayList<>();
                            Organization organization = null;
                            List<Organization.Position> listPosition = new ArrayList<>();
                            String lastName = "";
                            for (String name : arrayOrganization) {
                                switch (name) {
                                    case "#exist#":
                                        String position = parameterMap.get("position")[countPosition];
                                        if (position.equals("") || lastName.equals("")) {
                                            countPosition++;
                                            continue;
                                        }
                                        String start = parameterMap.get("startDate")[countPosition];
                                        LocalDate startDate = LocalDate.parse(start.equals("") ? "0001-01-01" : start);
                                        String finish = parameterMap.get("finishDate")[countPosition];
                                        LocalDate finishDate = LocalDate.parse(finish.equals("") ? "0001-01-01" : finish);

                                        if (typeSection.equals(SectionType.EDUCATION)) {
                                            listPosition.add(new Organization.Position(position, startDate, finishDate,null));
                                        } else {
                                            String content = parameterMap.get("content")[countPosition];
                                            listPosition.add(new Organization.Position(position, startDate, finishDate, content));
                                        }
                                        countPosition++;
                                        break;
                                    case "":
                                        lastName = "";
                                        countLink++;
                                        break;
                                    default:
                                        if (!listPosition.isEmpty()) {
                                            organization.getPositions().addAll(new ArrayList<>(listPosition));
                                            listPosition.clear();
                                        }
                                        if (organization != null) {
                                            listOrganization.add(organization);
                                        }
                                        lastName = name;
                                        organization = new Organization(name,null, parameterMap.get("link")[countLink],new ArrayList<>(listPosition));
                                        countLink++;
                                        break;
                                }
                            }
                            if (!listPosition.isEmpty()) {
                                organization.getPositions().addAll(new ArrayList<>(listPosition));
                                listPosition.clear();
                            }
                            listOrganization.add(organization);
                            r.addSection(typeSection, new OrganizationSection(listOrganization));
                            break;
                    }
            } else {
                r.getTextSection().remove(typeSection);
            }
        }
        if (uuid.equals("")) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);
                break;
            case "add":
                request.setAttribute("resume", new Resume());
                request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
                return;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);

    }
}