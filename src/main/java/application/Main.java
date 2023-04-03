package application;

import application.data.TestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {
    @Autowired
    private TestData testData;

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Main.class, args);
        Main app = applicationContext.getBean(Main.class);
        app.doSomething();
    }

    private void doSomething() {
        testData.runMocData();
    }

}
