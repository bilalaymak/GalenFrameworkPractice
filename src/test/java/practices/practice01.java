package practices;

import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;



import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.GalenTestBase;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static com.galenframework.api.Galen.checkLayout;
import static org.junit.Assert.fail;
import static utils.Driver.getDriver;
import static utils.GalenTestBase.load;

public class practice01 {



    @BeforeClass
    public void setUp()
    {
        getDriver().manage().window().setSize(new Dimension(1200, 800));

    }
    @Test()
    public void test01() throws IOException {


        getDriver().get("http://www.swtestacademy.com/");

        LayoutReport layoutReport = checkLayout(getDriver(), "./specs/homepage.gspec", List.of("desktop"));

        List<GalenTestInfo> tests = new LinkedList<>();

        GalenTestInfo test = GalenTestInfo.fromString("homepage layout");
        test.getReport().layout(layoutReport, "check homepage layout");
        tests.add(test);

        HtmlReportBuilder htmlReportBuilder = new HtmlReportBuilder();
        htmlReportBuilder.build(tests, "target");

        if (layoutReport.errors() > 0)
        {
            fail("Layout test failed");
        }
    }

    @Test(dataProvider = "devices",dataProviderClass = GalenTestBase.class)
    public void welcomePage_shouldLookGood_onDevice(GalenTestBase.TestDevice device) throws IOException {
        load("/");
        LayoutReport layoutReport =  checkLayout(getDriver(),"./specs/welcomePage.gspec", device.getTags());

        List<GalenTestInfo> tests = new LinkedList<>();

        GalenTestInfo test = GalenTestInfo.fromString("homepage layout");
        test.getReport().layout(layoutReport, "check homepage layout");
        tests.add(test);

        HtmlReportBuilder htmlReportBuilder = new HtmlReportBuilder();
        htmlReportBuilder.build(tests, "target");

        if (layoutReport.errors() > 0)
        {
            fail("Layout test failed");
        }
    }

    @Test(dataProvider = "devices",dataProviderClass = GalenTestBase.class)
    public void loginPage_shouldLookGood_onDevice(GalenTestBase.TestDevice device) throws IOException {
        load("/");
        getDriver().findElement(By.xpath("//button[.='Login']")).click();
        LayoutReport layoutReport =  checkLayout(getDriver(),"./specs/loginPage.gspec", device.getTags());

        List<GalenTestInfo> tests = new LinkedList<>();

        GalenTestInfo test = GalenTestInfo.fromString("homepage layout");
        test.getReport().layout(layoutReport, "check homepage layout");
        tests.add(test);

        HtmlReportBuilder htmlReportBuilder = new HtmlReportBuilder();
        htmlReportBuilder.build(tests, "target");

        if (layoutReport.errors() > 0)
        {
            fail("Layout test failed");
        }
    }

}
