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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import jhc.redsniff.webdriver.Download;
import jhc.redsniff.webdriver.download.DownloadFactory;

import org.hamcrest.Description;

public class FileContents extends Download<String>{
    private File file;

    public FileContents(File file) {
        this.file = file;
        if(!file.exists())
            throw new AssertionError(file.getAbsolutePath() + " does not exist");
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