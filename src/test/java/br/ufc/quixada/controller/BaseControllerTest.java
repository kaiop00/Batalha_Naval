package br.ufc.quixada.controller;

import java.util.ResourceBundle;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.BeforeAll;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import io.github.cdimascio.dotenv.Dotenv;

public abstract class BaseControllerTest extends ApplicationTest {
    protected static ResourceBundle bundle;

    @BeforeAll
    public static void setUpClass() throws TimeoutException {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        String environment = dotenv.get("ENVIRONMENT");
        if (environment != null && environment.equals("test")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
    }
}
