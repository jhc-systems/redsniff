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
package jhc.redsniff.webdriver.download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.openqa.selenium.WebDriver;

public  class FileDownloader {

    private static final int HTTP_SUCCESS_CODE = 200;
    private static final int MAX_ATTEMPTS = 5;
    private final WebDriver driver;

    public FileDownloader(WebDriver driver){
        this.driver=driver;
    }
    
    public File downloadFrom(String downloadLocation, String extension) throws IOException {
        String fileName = "temp"+extension;
        String downloadDir = new File(".\\").getCanonicalPath() + "\\";
        String downloadFilePath = downloadDir + fileName;
        File file = new File(downloadFilePath);
        ensureFileNotPresent(file);
        doDownload( downloadFilePath, createAbsoluteUrl(downloadLocation));
        return file;
    }

    private void ensureFileNotPresent(File file) throws AssertionError {
        if (file.exists())
            if (!file.delete())
                throw new AssertionError("File exists and could not be deleted");
    }
    private HttpState mimicCookieState(Set<org.openqa.selenium.Cookie> seleniumCookieSet) {
        HttpState mimicWebDriverCookieState = new HttpState();
        for (org.openqa.selenium.Cookie seleniumCookie : seleniumCookieSet) {
            Cookie httpClientCookie = new Cookie(seleniumCookie.getDomain(), seleniumCookie.getName(), seleniumCookie.getValue(), seleniumCookie.getPath(), seleniumCookie.getExpiry(), seleniumCookie.isSecure());
            mimicWebDriverCookieState.addCookie(httpClientCookie);
        }
        return mimicWebDriverCookieState;
    }

    private HostConfiguration mimicHostConfiguration(String hostURL, int hostPort) {
        HostConfiguration hostConfig = new HostConfiguration();
        hostConfig.setHost(hostURL, hostPort);
        return hostConfig;
    }

    private String createAbsoluteUrl(String relativePath) {
        String basePart = "";
        if(!relativePath.startsWith("http")) 
            basePart+=driver.getCurrentUrl();
        if(!relativePath.toLowerCase().contains("jsessionid"))
                basePart += getJSessionString();
        return basePart + relativePath;
    }

    private String getJSessionString() {
        for(org.openqa.selenium.Cookie cookie:driver.manage().getCookies()){
            if(cookie.getName().equalsIgnoreCase("jsessionid")) {
                return ";jsessionid="+cookie.getValue();
            }
        }
        return null;
    }

    private void doDownload(String filePath, String downloadLocation)  {
        URL downloadURL = createURL(downloadLocation);
        File downloadedFile = new File(filePath);
        try{
        doDownloadUrlToFile(downloadURL, downloadedFile);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
    protected URL createURL(String downloadLocation){
        try {
            return new URL(downloadLocation);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private void doDownloadUrlToFile(URL downloadURL, File downloadFile) throws Exception {
        HttpClient client = createHttpClient(downloadURL);
        HttpMethod getRequest = new GetMethod(downloadURL.toString());
        try {
            int status = -1;
            for(int attempts=0;attempts<MAX_ATTEMPTS && status!=HTTP_SUCCESS_CODE; attempts++)
                status = client.executeMethod(getRequest);
            if(status!=HTTP_SUCCESS_CODE)
                throw new Exception("Got " + status + " status trying to download file " + downloadURL.toString() + " to " + downloadFile.toString());
            BufferedInputStream in = new BufferedInputStream(getRequest.getResponseBodyAsStream());
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(downloadFile));
            int offset = 0;
            int len = 4096;
            int bytes = 0;
            byte[] block = new byte[len];
            while ((bytes = in.read(block, offset, len)) > -1) {
                out.write(block, 0, bytes);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            throw e;
        } finally {
            getRequest.releaseConnection();
        }
    }

    private HttpClient createHttpClient(URL downloadURL) {
        HttpClient client = new HttpClient();
        client.getParams().setCookiePolicy(CookiePolicy.RFC_2965);
        client.setHostConfiguration(mimicHostConfiguration(downloadURL.getHost(), downloadURL.getPort()));
        client.setState(mimicCookieState(driver.manage().getCookies()));
        return client;
    }

}