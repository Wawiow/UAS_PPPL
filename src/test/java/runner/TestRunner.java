package runner;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import io.cucumber.junit.platform.engine.Constants;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(
        key = Constants.GLUE_PROPERTY_NAME,
        value = "steps,hooks"
)
@ConfigurationParameter(
        key = Constants.PLUGIN_PROPERTY_NAME,
        value = "pretty, json:target/cucumber-reports/cucumber.json, html:target/cucumber-reports/cucumber.html"
)
@ConfigurationParameter(
        key = Constants.FILTER_TAGS_PROPERTY_NAME,
        value = "@PermohonanUnduh"
)
public class TestRunner {
    // Jalankan TC-PU-01 s.d TC-PU-10 (default):
    //   mvn verify -Dmaven.test.failure.ignore=true
    //
    // Jalankan ReqContributor saja:
    //   mvn verify -Dmaven.test.failure.ignore=true -Dcucumber.filter.tags=@PengajuanKontributor
    //
    // Jalankan semua feature sekaligus:
    //   mvn verify -Dmaven.test.failure.ignore=true -Dcucumber.filter.tags="@PermohonanUnduh or @PengajuanKontributor"
}
