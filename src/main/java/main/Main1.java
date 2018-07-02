package main;

import config.DsDevConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

public class Main1 {
    public static void main(String[] args) {
//        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
//        context.getEnvironment().setActiveProfiles("dev");
//        context.load("classpath:applicationContext.xml");
//        context.refresh();

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(DsDevConfig.class);

        DataSource dataSource = context.getBean("dataSource", DataSource.class);
        System.out.println("asdjflkasjdflkjaklsjfklajsdlf");

    }
}
