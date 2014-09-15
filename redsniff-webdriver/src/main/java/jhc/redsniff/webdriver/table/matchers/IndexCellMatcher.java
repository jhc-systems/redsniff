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
package jhc.redsniff.webdriver.table.matchers;

import java.util.List;

import jhc.redsniff.html.tables.TableCell;
import jhc.redsniff.html.tables.TableRow;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class IndexCellMatcher extends IsTableRowIncluding {

    public IndexCellMatcher(List<Matcher<TableCell>> indexCellMatchers) {
        super(indexCellMatchers);
    }

    @Override
    protected boolean matchesSafely(TableRow actualRow,
            Description mismatchDescription) {
        return doMatching(actualRow, mismatchDescription).isFinished();
    }
}
