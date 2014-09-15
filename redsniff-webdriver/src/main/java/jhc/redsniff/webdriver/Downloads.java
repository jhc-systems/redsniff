package jhc.redsniff.webdriver;

import jhc.redsniff.html.tables.Table;
import jhc.redsniff.webdriver.download.DownloadFactory;
import jhc.redsniff.webdriver.download.parsers.CSVTable;
import jhc.redsniff.webdriver.download.parsers.FileContents;

public final class Downloads {

    public static DownloadFactory<Table> parsedAsCSVTable() {
        return CSVTable.parsedAsCSVTable();
    }
    public static DownloadFactory<String> extractedAsStringContents() {
        return FileContents.extractedAsStringContents();
    }
}
