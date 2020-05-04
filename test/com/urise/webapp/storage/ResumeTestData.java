package com.urise.webapp.storage;

import com.urise.webapp.model.*;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    private final static Storage ARRAY_STORAGE = new ArrayStorage();

    public Resume addResume(String uuid, String fullName,
                                      String telephone, String skype, String email, String linkedin, String github, String stackoverflow,
                                      String textPers, String textObj,
                                      String textAchiev, String textQualif,
                                      List<Organization> textExper, List<Organization> textEduc) {
        Resume resume = new Resume(uuid, fullName);

        Map<ContactType, String> mapContact = resume.getContact();
        Map<SectionType, AbstractSection> textSection = resume.getTextSection();

        mapContact.put(ContactType.TELEPHONE, telephone);
        mapContact.put(ContactType.SKYPE, skype);
        mapContact.put(ContactType.EMAIL, email);
        mapContact.put(ContactType.LINKEDIN, linkedin);
        mapContact.put(ContactType.GITHUB, github);
        mapContact.put(ContactType.STACKOVERFLOW, stackoverflow);

        textSection.put(SectionType.PERSONAL, new TextSection(textPers));
        textSection.put(SectionType.OBJECTIVE, new TextSection(textObj));

        textSection.put(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList(textAchiev)));
        textSection.put(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList(textQualif)));

        textSection.put(SectionType.EXPERIENCE, new OrganizationSection(textExper));
        textSection.put(SectionType.EDUCATION, new OrganizationSection(textEduc));

        return resume;
    }

    public static void main(String[] args) {
        Resume resume = new Resume("123", "Григорий Кислин");

        Map<ContactType, String> contact = resume.getContact();
        Map<SectionType, AbstractSection> textSection = resume.getTextSection();

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
        Organization organization1 = new Organization("Java Online Projects", "Javaops.", "http://javaops.ru/",
                new Organization.Position("Автор проекта.", YearMonth.of(2013, 10), YearMonth.now(),
                        "Создание, организация и проведение Java онлайн проектов и стажировок."));
        Organization organization2 = new Organization("Wrike", "Wrike", "https://www.wrike.com/",
                new Organization.Position("Старший разработчик (backend)", YearMonth.of(2014, 10), YearMonth.of(2016, 1),
                        "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis," +
                                " Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        Organization organization3 = new Organization("RIT Center", "RIT Center", null,
                new Organization.Position("Java архитектор", YearMonth.of(2014, 4), YearMonth.of(2014, 10),
                        "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование," +
                                " ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части " +
                                "системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). " +
                                "Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, " +
                                "Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"));
        Organization organization4 = new Organization("Luxoft (Deutsche Bank)", "Luxoft (Deutsche Bank)", "http://www.luxoft.ru/",
                new Organization.Position("Ведущий программист.", YearMonth.of(2010, 12), YearMonth.of(2012, 4),
                        "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle)." +
                                " Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в" +
                                " области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."));
        Organization organization5 = new Organization("Yota", "Yota", "https://www.yota.ru/",
                new Organization.Position("Ведущий специалист", YearMonth.of(2008, 6), YearMonth.of(2010, 12),
                        "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4," +
                                " JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента" +
                                " (Python/ Jython, Django, ExtJS)"));
        Organization organization6 = new Organization("Enkata", "Enkata", "http://enkata.com/",
                new Organization.Position("Разработчик ПО", YearMonth.of(2007, 3), YearMonth.of(2008, 6),
                        "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)."));
        Organization organization7 = new Organization("Siemens AG", "Siemens AG", "https://www.siemens.com/ru/ru/home.html",
                new Organization.Position("Разработчик ПО", YearMonth.of(2005, 1), YearMonth.of(2007, 2),
                        "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."));
        Organization organization8 = new Organization("Alcatel", "Alcatel", "http://www.alcatel.ru/",
                new Organization.Position("Инженер по аппаратному и программному тестированию", YearMonth.of(1997, 9), YearMonth.of(2005, 1),
                        "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."));

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

        Organization organizationED1 = new Organization("Coursera", "Coursera", "https://www.coursera.org/course/progfun",
                new Organization.Position(null, YearMonth.of(2013, 3), YearMonth.of(2013, 5),
                        "\"Functional Programming Principles in Scala\" by Martin Odersky"));
        Organization organizationED2 = new Organization("Luxoft", "Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                new Organization.Position(null, YearMonth.of(2011, 3), YearMonth.of(2011, 4),
                        "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""));
        Organization organizationED3 = new Organization("Siemens AG", "Siemens AG", "http://www.siemens.ru/",
                new Organization.Position(null, YearMonth.of(2005, 1), YearMonth.of(2005, 4),
                        "3 месяца обучения мобильным IN сетям (Берлин)"));
        Organization organizationED4 = new Organization("Alcatel", "Alcatel", "http://www.alcatel.ru/",
                new Organization.Position(null, YearMonth.of(1997, 9), YearMonth.of(1998, 3),
                        "6 месяцев обучения цифровым телефонным сетям (Москва)"));
        Organization organizationED5 = new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                "ifmo", "http://www.ifmo.ru/",
                new Organization.Position(null, YearMonth.of(1993, 9), YearMonth.of(1996, 7),
                        "Аспирантура (программист С, С++)"),
                new Organization.Position(null, YearMonth.of(1987, 9), YearMonth.of(1993, 7),
                        "Инженер (программист Fortran, C)"));
        Organization organizationED6 = new Organization("Заочная физико-техническая школа при МФТИ", "mipt", "http://www.school.mipt.ru/",
                new Organization.Position(null, YearMonth.of(1984, 9), YearMonth.of(1987, 6),
                        "Закончил с отличием"));

        List<Organization> listOrg2 = new ArrayList<>();
        listOrg2.add(organizationED1);
        listOrg2.add(organizationED2);
        listOrg2.add(organizationED3);
        listOrg2.add(organizationED4);
        listOrg2.add(organizationED5);
        listOrg2.add(organizationED6);

        AbstractSection orgText2 = new OrganizationSection(listOrg2);
        textSection.put(SectionType.EDUCATION, orgText2);

        resume.setTextSection(textSection);

        ARRAY_STORAGE.save(resume);
        System.out.println("Get: " + ARRAY_STORAGE.get("123"));
    }
}
