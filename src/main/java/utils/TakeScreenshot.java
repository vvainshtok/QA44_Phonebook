package utils;


import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TakeScreenshot {

    public static void main(String[] args) {
        createFileNameScreenshot();
    }

    public static void takeScreenshot(TakesScreenshot screenshot) {
        String fileName = createFileNameScreenshot();
        File scrFile = screenshot.getScreenshotAs(OutputType.FILE);
        try {
            Files.copy(scrFile, new File(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String createFileNameScreenshot() {
        SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd_HH-mm-ss");
        Date date = new Date(System.currentTimeMillis());
        String currentDate = formatter.format(date);
        return "src/test/resources/screenshots/" + currentDate + ".png";
    }
}
