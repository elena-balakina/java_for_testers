package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class SoapTests extends TestBase {

    @Test
    public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
        //получить список проектов
        Set<Project> projects = app.soap().getProjects();

        System.out.println("Projects count: " + projects.size());
        System.out.println("Projects list:");
        for (Project project : projects) {
            System.out.println(project.getName());
        }
    }

    @Test
    public void testCreateIssue() throws RemoteException, ServiceException, MalformedURLException {
        // получаем список проектов
        Set<Project> projects = app.soap().getProjects();

        // выбираем любой проект для создаваемого баг-репорта
        Issue issue = new Issue().withSummary("Test issue").withDescription("Test issue description").
                withProject(projects.iterator().next());

        Issue createdIssue = app.soap().addIssue(issue);
        assertEquals(issue.getSummary(), createdIssue.getSummary());
    }
}
