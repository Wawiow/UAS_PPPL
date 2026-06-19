package runner;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/DownloadRequest.feature")
@ConfigurationParameter(
        key = Constants.GLUE_PROPERTY_NAME,
        value = "steps,hooks"
)
@ConfigurationParameter(
        key = Constants.PLUGIN_PROPERTY_NAME,
        value = "pretty, json:target/cucumber-reports/download-request.json, html:target/cucumber-reports/download-request.html"
)
public class DownloadRequestTestSuite {
    // Suite khusus fitur Permohonan Unduh Dokumen (TC-PU-01 s.d TC-PU-10).
    // Hanya membaca features/DownloadRequest.feature — tidak akan menyentuh ReqContributor.
    //
    // Jalankan seluruh suite ini:
    //   mvn test -Dtest=DownloadRequestTestSuite "-Dmaven.test.failure.ignore=true"
    //
    // Jalankan subset tertentu, contoh hanya TC-PU-09 & TC-PU-10:
    //   mvn test -Dtest=DownloadRequestTestSuite "-Dmaven.test.failure.ignore=true" "-Dcucumber.filter.tags=@TC-PU-09 or @TC-PU-10"
    //
    // Laporan HTML tersedia di:
    //   target/cucumber-reports/download-request.html
}
