/*******************************************************************************
 * Copyright 2014 JHC Systems Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package jhc.redsniff.webdriver.download.parsers;

import static jhc.redsniff.internal.finders.OnlyFinder.only;
import static jhc.redsniff.webdriver.Finders.tableElement;

import java.io.File;
import java.io.IOException;

import jhc.redsniff.html.tables.Table;
import jhc.redsniff.html.tables.converters.csv.CsvToHtmlFileConverter;
import jhc.redsniff.internal.util.NonLoggingHtmlUnitDriver;
import jhc.redsniff.webdriver.Download;
import jhc.redsniff.webdriver.RedsniffWebDriverTester;
import jhc.redsniff.webdriver.download.DownloadFactory;

import org.hamcrest.Description;
import org.junit.Assert;

import com.gargoylesoftware.htmlunit.BrowserVersion;


/**
 * <p>Constructs a {@link Table} from a csv file.</p>
 * <p>Currently it just converts the csv file to html then runs existing parsing over that, 
 * but eventually {@link Table} will be made to be independent of WebElement so it can be used for any type of data
 * , so conversion would not be required.
 */
public class CSVTable extends Download<Table>{
    private File csvFile;
    private File convertedHtmlFile;
    private RedsniffWebDriverTester csvTester;
    
    public CSVTable(File csvFile) {
        this.csvFile = csvFile;
        Assert.assertTrue("file exists",csvFile.exists());
        csvTester = new RedsniffWebDriverTester(new NonLoggingHtmlUnitDriver(BrowserVersion.FIREFOX_17));
    }

    @Override
    public void doneWith() {
        csvTester.quit();
        deleteFileThatMightHaveLockingFileHandles(convertedHtmlFile);
        csvFile.delete();
    }

    private void deleteFileThatMightHaveLockingFileHandles(File file) {
        //workaround because HtmlUnit doesn't release handles properly
        int deleteAttempts = 0;
        while(deleteAttempts<3){
            if(file.delete())
                return;
            else {
                System.gc();//this should release the handles first go
                deleteAttempts++;
            }
        }
        throw new AssertionError("Could not delete file " + file.getAbsolutePath() + " - a handle may be locking it");
    }

    @Override
    public Table getObject() throws IOException{
        String convertedHtmlPath = convertToHtml();
        csvTester.goTo(fileUrlFromWindowsPath(convertedHtmlPath));
        return Table.fromTableElement(csvTester.find(only(tableElement())));
    }

    private String convertToHtml() throws IOException {
        String convertedHtmlPath = csvFile.getAbsolutePath().replaceAll("\\.csv$", "\\.html");
        convertedHtmlFile = new File(convertedHtmlPath);
        new CsvToHtmlFileConverter().convertToHtml(csvFile, convertedHtmlFile, true);
        Assert.assertTrue("converted to html file exists",convertedHtmlFile.exists());
        return convertedHtmlPath;
    }
    
    public static DownloadFactory<Table> parsedAsCSVTable(){
        return new DownloadFactory<Table>() {
            @Override
            public Download<Table> createDownloadFrom(File file) {
                return new CSVTable(file);
            }

            @Override
            public String getDefaultFileExtension() {
                return ".csv";
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("csv table");
            }
        };
    }
}