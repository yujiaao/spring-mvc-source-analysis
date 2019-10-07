package ${package}.${artifactId}.integration;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ${package}.${artifactId}.Application;
import java.util.Collections;

/**
 * Created by yujiaao.
 */


@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("INTEGRATION_TEST")
@ContextConfiguration
public class CucumberRoot {

    @Autowired
    protected TestRestTemplate template;

    @Before
    public void before() {
        // demo to show how to add custom header Globally for the http request in spring test template , like IV user header
        template.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) -> {
            request.getHeaders()
                    .add("iv-user", "user");
            return execution.execute(request, body);
        }));
    }

}
