/*		
 * (C) Copyright 2017 Boni Garcia (http://bonigarcia.github.io/)		
 *		
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *		
 */
package io.github.bonigarcia.wdm.test.proxy;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.Assert.assertTrue;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;
import org.slf4j.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Test for proxy with mock server.
 * 
 * @since 1.7.2
 */
@Ignore
public class MockProxyTest {

    final Logger log = getLogger(lookup().lookupClass());

    private ClientAndServer proxy;
    private int proxyPort;

    @Before
    public void setup() {
        proxy = startClientAndServer();
        proxyPort = proxy.getLocalPort();
        log.debug("Started mock proxy on port {}", proxyPort);
    }

    @After
    public void teardown() {
        log.debug("Stopping mock proxy on port {}", proxyPort);
        proxy.stop();
    }

    @Test
    public void testMockProx() throws MalformedURLException {
        WebDriverManager.chromedriver().proxy("localhost:" + proxyPort)
                .proxyUser("").proxyPass("")
                .driverRepositoryUrl(
                        new URL("https://chromedriver.storage.googleapis.com/"))
                .setup();
        File driver = new File(
                WebDriverManager.chromedriver().getDownloadedDriverPath());
        assertTrue(driver.exists());
    }

}