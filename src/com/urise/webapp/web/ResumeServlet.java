package com.urise.webapp.web;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SqlStorage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
//        response.getWriter().write(uuid == null ? "Hello Resumes!" : "Hello " + name + '!');
        List<Resume> resumeList = new SqlStorage(
                "jdbc:postgresql://localhost:5432/resumes",
                "postgres",
                "455600")
                .getAllSorted();
        StringBuilder addResume = new StringBuilder();
        Resume resume = null;
        if (uuid != null) {
            resume = new SqlStorage(
                    "jdbc:postgresql://localhost:5432/resumes",
                    "postgres",
                    "455600")
                    .get(uuid);
        }
        for (Resume r : resumeList) {
            addResume.append("<tr>\n<td>").append(r.getUuid()).append("</td>\n<td>").append(r.getFullName()).append("</td>\n</tr>\n");
        }
        response.getWriter().write(uuid == null ?
                "<html>\n" +
                        "<head>\n" +
                        "<title>Resume</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<table border=\"1\">\n" +
                        "<tr>\n" +
                        "<th>uuid</th>\n" +
                        "<th>full_name</th>\n" +
                        "</tr>\n" +
                        addResume +
                        "</table>\n" +
                        "</body>\n" +
                        "</html>" :
                "<html>\n" +
                        "<head>\n" +
                        "<title>Resume</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        resume +
                        "</body>\n" +
                        "</html>"
        );
    }
}