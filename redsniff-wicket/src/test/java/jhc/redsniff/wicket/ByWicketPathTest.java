package jhc.redsniff.wicket;

import static jhc.redsniff.wicket.ByWicketPath.convertToWicketXPath;

import org.junit.Assert;
import org.junit.Test;

public class ByWicketPathTest {

    @Test
    public void testConvertFormatPath() {
        Assert.assertEquals("//w{panel}//w{fieldSetList}[2]//w{fieldSet}//w{fieldList}[1]//w{field}//w{value}",
                convertToWicketXPath("panel:fieldSetList:1:fieldSet:fieldList:0:field:value"));
    }
}
