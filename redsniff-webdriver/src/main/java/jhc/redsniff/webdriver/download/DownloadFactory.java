package jhc.redsniff.webdriver.download;

import java.io.File;

import org.hamcrest.SelfDescribing;

import jhc.redsniff.webdriver.Download;

public interface DownloadFactory<F> extends SelfDescribing{
    Download<F> createDownloadFrom(File file);

    String getDefaultFileExtension();
}
