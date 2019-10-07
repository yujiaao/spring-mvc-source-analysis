package ${package}.${artifactId}.acceptance;


import org.junit.Before;
import org.junit.Test;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import ${package}.${artifactId}.retry.Retry;

import static org.junit.Assert.assertTrue;


public class ApplicationE2E{
    private int port = 8080;
    private RestTemplate restTemplate;
    private URL baseURL;

    @Before
    public void setUp() throws Exception {
        // replace that with UAT server host
        this.baseURL = new URL("http://localhost:" + port + "/");
        // disable proxy if you wanna run locally
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("userproxy.glb.ebc.local", 8080));
        requestFactory.setProxy(proxy);
        restTemplate = new RestTemplate();


    }
    // example of true end to end call which call UAT real endpoint
    @Test
    public void test_is_server_up() {
        assertTrue(restTemplate.getForEntity(baseURL + "/actuator/health", String.class).getStatusCode().is2xxSuccessful());

    }


}
