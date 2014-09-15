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
package jhc.redsniff.html.tables.converters.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import au.com.bytecode.opencsv.CSVReader;

public class CsvToHtmlFileConverter {

    public void convertToHtml(File csvFile, File htmlFile, boolean withTableHeader) throws IOException {
        try (
                CSVReader csvReader = new CSVReader(new FileReader(csvFile));
                BufferedWriter htmlWriter = new BufferedWriter(new FileWriter(htmlFile))) {
            String lineOfCsvValues[];
            boolean firstLine = true;
            htmlBodyTableOpening(htmlWriter);
            while ((lineOfCsvValues = csvReader.readNext()) != null) {
                if(isBlankLine(lineOfCsvValues))
                        continue;
                String[] columns = escapeChars(lineOfCsvValues);
                if (withTableHeader == true && firstLine == true) {
                    tableHeader(htmlWriter, columns);
                    firstLine = false;
                    tableBodyOpening(htmlWriter);
                } else {

                    tableRow(htmlWriter, columns);
                }
            }
            tableBodyClosing(htmlWriter);
            htmlBodyTableClosing(htmlWriter);
            htmlWriter.close();
        }
        
    }

    private static boolean isBlankLine(String[] lineOfCsvValues) {
        return lineOfCsvValues.length==0 || (lineOfCsvValues.length==1 && lineOfCsvValues[0].equals(""));
    }

    private static void tableBodyOpening(BufferedWriter htmlWriter) throws IOException {
        htmlWriter.write("<tbody>");
    }

    private static void tableBodyClosing(BufferedWriter htmlWriter) throws IOException {
        htmlWriter.write("</tbody>");
    }

    private static String[] escapeChars(String[] values) {
        String[] escapedValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            escapedValues[i] = escapeChars(values[i]);
        }
        return escapedValues;
    }

    private static String escapeChars(String value) {
        StringBuilder sb = new StringBuilder();
        int lineLength = value.length();
        for (int i = 0; i < lineLength; i++) {
            char c = value.charAt(i);
            switch (c) {
            case '"':
                sb.append("&quot;");
                break;
            case '&':
                sb.append("&amp;");
                break;
            case '\'':
                sb.append("&apos;");
                break;
            case '<':
                sb.append("&lt;");
                break;
            case '>':
                sb.append("&gt;");
                break;
            default:
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static void tableHeader(Writer ps, String[] columns) throws IOException {
        ps.write("<thead>\n");
        ps.write("<tr>\n");
        for (int i = 0; i < columns.length; i++) {
            ps.write("<th>");
            ps.write(columns[i]);
            ps.write("</th>");
        }
        ps.write("</tr>\n");
        ps.write("</thead>\n");
    }

    private static void tableRow(Writer ps, String[] columns) throws IOException {
        ps.write("<tr>\n");
        for (int i = 0; i < columns.length; i++) {
            ps.write("<td>");
            ps.write(columns[i]);
            ps.write("</td>");
        }
        ps.write("</tr>\n");
    }

    private static void htmlBodyTableClosing(BufferedWriter htmlWriter) throws IOException {
    }

    private static void htmlBodyTableOpening(BufferedWriter htmlWriter) throws IOException {
        htmlWriter.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n");
        htmlWriter.write("<body>\n");
        htmlWriter.write("<table>\n");
    }
}
