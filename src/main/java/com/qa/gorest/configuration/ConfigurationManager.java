package com.qa.gorest.configuration;

import com.qa.gorest.frameworkexception.APIFrameworkException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Properties;

public class ConfigurationManager {
/*    What are the different classes are required to read the config.properties file? two class are required.when we designed the framework we always use try catch block because in case any issue in framework
            Properties and File InputStream */
    private Properties prop;
    private FileInputStream ip;

    //maven@ cmd line argument -D means any variable
    //mvn clean install -Denv="qa"

    public Properties intiProp(){
        prop = new Properties();

        String envName = System.getProperty("env");
        System.out.println("Running tests on env: " + envName);
        try {
        if (envName == null) {
            System.out.println("no env is given...hence running test on QA env...");
            ip = new FileInputStream("./src/test/resources/config/config.properties");
        }
        else {
            System.out.println("Running tests on env: " + envName);
            switch(envName.toLowerCase()){
            case "qa":
                ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
                    break;
            case "stage":
                ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
                    break;
            default:
                System.out.println("Please pass the right env name..." + envName);
                throw new APIFrameworkException("NO ENV IS GIVEN");
            }
        }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        try {
            prop.load(ip);
        } catch (IOException e) {
            e.printStackTrace();;
        }
//        prop = new Properties();
//        try {
//            ip = new FileInputStream("./src/test/resources/config/config.properties");//need to add "./" before Path means go to current directory then go to same path
//            prop.load(ip);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return prop;
    }


}
