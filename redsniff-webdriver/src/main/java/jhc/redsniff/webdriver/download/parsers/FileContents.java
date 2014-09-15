package jhc.redsniff.webdriver.download.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import jhc.redsniff.webdriver.Download;
import jhc.redsniff.webdriver.download.DownloadFactory;

import org.hamcrest.Description;
import org.junit.Assert;

public class FileContents extends Download<String>{
    private File file;

    public FileContents(File file) {
        this.file = file;
        Assert.assertTrue("file exists", file.exists());
    }

    @Override
    public String getObject() throws Exception {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line = null;
            while((line = reader.readLine())!=null){
                sb.append(line);
            }
        }
        return sb.toString();
    }

    @Override
    public void doneWith() {
        file.delete();
    }
    
    public static DownloadFactory<String> extractedAsStringContents(){
        return new DownloadFactory<String>() {
            @Override
            public Download<String> createDownloadFrom(File file) {
                return new FileContents(file);
            }

            @Override
            public String getDefaultFileExtension() {
                return ".txt";
            }

            @Override
            public void describeTo(Description description) {
               description.appendText("String file contents");                
            }
        };
    }
}