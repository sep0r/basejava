package com.urise.webapp;

import com.urise.webapp.model.*;
import com.urise.webapp.storage.ArrayStorage;
import com.urise.webapp.storage.Storage;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    private final static Storage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        Resume resume = new Resume("123", "Григорий Кислин");

        Map<ContactType, String> contact = new EnumMap<>(ContactType.class);
        Map<SectionType, AbstractSection> textSection = new EnumMap<>(SectionType.class);

///////////////////////////////////////ContactType//////////////////////////////////////////////////////////////////
        contact.put(ContactType.TELEPHONE, "+7(921)855-0482");
        contact.put(ContactType.SKYPE, "grigory.kislin");
        contact.put(ContactType.EMAIL, "gkislin@yandex.ru");
        contact.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        contact.put(ContactType.GITHUB, "https://github.com/gkislin");
        contact.put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");

        resume.setContact(contact);

///////////////////////////////////////TextSection/////////////////////////////////////////////////////////////
        AbstractSection text1 = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. " +
                "Пурист кода и архитектуры.");
        AbstractSection text2 = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");

        textSection.put(SectionType.PERSONAL, text1);
        textSection.put(SectionType.OBJECTIVE, text2);

////////////////////////////////////////ListSection////////////////////////////////////////////////////////////
        List<String> list1 = new ArrayList<>();
        list1.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. " +
                "Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". " +
                "Организация онлайн стажировок и ведение проектов. Более 1000 выпускников. ");
        list1.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk. ");
        list1.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С," +
                " Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery." +
                " Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера. ");
        list1.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT," +
                " ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга. ");
        list1.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура," +
                " JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. " +
                "Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django). ");
        list1.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), " +
                "Белоруcсии(Erip, Osmp) и Никарагуа.");

        List<String> list2 = new ArrayList<>();
        list2.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2 ");
        list2.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce ");
        list2.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");
        list2.add("MySQL, SQLite, MS SQL, HSQLDB");
        list2.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy, ");
        list2.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts, ");
        list2.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, " +
                "Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice," +
                " GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements). ");
        list2.add("Python: Django. ");
        list2.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js ");
        list2.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        list2.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC," +
                " JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT. ");
        list2.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix, ");
        list2.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, " +
                "Bonita, pgBouncer. ");
        list2.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, " +
                "функционального программирования ");
        list2.add("Родной русский, английский \"upper intermediate\"");

        AbstractSection textList1 = new ListSection(list1);
        AbstractSection textList2 = new ListSection(list2);

        textSection.put(SectionType.ACHIEVEMENT, textList1);
        textSection.put(SectionType.QUALIFICATIONS, textList2);

/////////////////////////////////////////OrganizationSection///////////////////////////////////////////////////////////
        Organization organization1 = new Organization("Java Online Projects",
                YearMonth.of(2013, 10), YearMonth.now(), "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок.", "http://javaops.ru/", "Java Online Projects");
        Organization organization2 = new Organization("Wrike", YearMonth.of(2014, 10),
                YearMonth.of(2016, 1), "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                        "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis).Двухфакторная аутентификация, авторизация по " +
                        "OAuth1, OAuth2, JWT SSO.", "https://www.wrike.com/", "Wrike");
        Organization organization3 = new Organization("RIT Center", YearMonth.of(2012, 4),
                YearMonth.of(2014, 10), "Java архитектор",
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins)," +
                        " миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной " +
                        "части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт" +
                        " в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin " +
                        "development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, " +
                        "Unix shell remote scripting via ssh tunnels, PL/Python", null, null);
        Organization organization4 = new Organization("Luxoft (Deutsche Bank)", YearMonth.of(2010, 12),
                YearMonth.of(2012, 4), "Ведущий программист",
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle)." +
                        " Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа " +
                        "результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.",
                "http://www.luxoft.ru/", "Luxoft (Deutsche Bank)");
        Organization organization5 = new Organization("Yota", YearMonth.of(2008, 6),
                YearMonth.of(2010, 12), "Ведущий специалист",
                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3," +
                        " JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга" +
                        " фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)", "https://www.yota.ru/", "Yota");
        Organization organization6 = new Organization("Enkata", YearMonth.of(2007, 3),
                YearMonth.of(2008, 6), "Разработчик ПО",
                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного" +
                        " J2EE приложения (OLAP, Data mining).", "http://enkata.com/", "Enkata");
        Organization organization7 = new Organization("Siemens AG", YearMonth.of(2005, 1),
                YearMonth.of(2007, 2), "Разработчик ПО",
                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе" +
                        " Siemens @vantage (Java, Unix).", "https://www.siemens.com/ru/ru/home.html", "Enkata");
        Organization organization8 = new Organization("Alcatel", YearMonth.of(1997, 9),
                YearMonth.of(2005, 1), "Инженер по аппаратному и программному тестированию",
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).",
                "http://www.alcatel.ru/", "Alcatel");

        List<Organization> listOrg1 = new ArrayList<>();
        listOrg1.add(organization1);
        listOrg1.add(organization2);
        listOrg1.add(organization3);
        listOrg1.add(organization4);
        listOrg1.add(organization5);
        listOrg1.add(organization6);
        listOrg1.add(organization7);
        listOrg1.add(organization8);

        AbstractSection orgText1 = new OrganizationSection(listOrg1);
        textSection.put(SectionType.EXPERIENCE, orgText1);

        Organization organizationED1 = new Organization("Coursera", YearMonth.of(2013, 3),
                YearMonth.of(2013, 5), null,
                "\"Functional Programming Principles in Scala\" by Martin Odersky",
                "https://www.coursera.org/course/progfun", "Coursera");
        Organization organizationED2 = new Organization("Luxoft", YearMonth.of(2011, 3),
                YearMonth.of(2011, 4), null,
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"",
                "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366", "Luxoft");
        Organization organizationED3 = new Organization("Siemens AG",
                YearMonth.of(2005, 1), YearMonth.of(2005, 4), null,
                "3 месяца обучения мобильным IN сетям (Берлин)",
                "http://www.siemens.ru/", "Siemens AG");
        Organization organizationED4 = new Organization("Alcatel", YearMonth.of(1997, 9),
                YearMonth.of(1998, 3), null, "6 месяцев обучения цифровым телефонным сетям (Москва)",
                "http://www.alcatel.ru/", "Alcatel");
        Organization organizationED51 = new Organization("Санкт-Петербургский национальный исследовательский университет" +
                " информационных технологий, механики и оптики", YearMonth.of(1993, 9), YearMonth.of(1996, 7), null,
                "Аспирантура (программист С, С++", "http://www.ifmo.ru/", "ifmo");
        Organization organizationED52 = new Organization("Санкт-Петербургский национальный исследовательский университет" +
                " информационных технологий, механики и оптики", YearMonth.of(1987, 9), YearMonth.of(1993, 7), null,
                "Инженер (программист Fortran, C)", "http://www.ifmo.ru/", "ifmo");
        Organization organizationED6 = new Organization("Заочная физико-техническая школа при МФТИ", YearMonth.of(1984, 9),
                YearMonth.of(1987, 6), null, "Закончил с отличием", "http://www.school.mipt.ru/", "school.mipt");

        List<Organization> listOrg2 = new ArrayList<>();
        listOrg2.add(organizationED1);
        listOrg2.add(organizationED2);
        listOrg2.add(organizationED3);
        listOrg2.add(organizationED4);
        listOrg2.add(organizationED51);
        listOrg2.add(organizationED52);
        listOrg2.add(organizationED6);

        AbstractSection orgText2 = new OrganizationSection(listOrg2);
        textSection.put(SectionType.EDUCATION, orgText2);

        resume.setTextSection(textSection);

        ARRAY_STORAGE.save(resume);
        System.out.println("Get: " + ARRAY_STORAGE.get("123"));
    }
}
