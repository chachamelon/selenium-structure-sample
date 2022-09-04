/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package selenium.structure.sample;

import java.util.List;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.collections.Lists;

public class App {

    public static void main(String[] args) {
        TestListenerAdapter tla = new TestListenerAdapter();
        TestNG testng = new TestNG();
        List<String> suites = Lists.newArrayList();
        suites.add("./app/src/main/resources/login/login.xml");//path to xml..
        suites.add("./app/src/main/resources/mail/mail.xml");
        testng.setTestSuites(suites);
        testng.run();
    }
}