package fr.pinguet62.jsfring.indexer.nosql;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.yamj.api.common.http.AndroidBrowserUserAgentSelector;
import org.yamj.api.common.http.HttpClientWrapper;

import com.moviejukebox.allocine.AllocineApi;

@Component
public class AllocineApiBean {

    private static final String partner = "100043982026";

    private static final String secret = "29d185d98c984a359e6e6f26a0474269";

    @Value("${http.proxyHost:}")
    private String proxyHost;

    @Value("${http.proxyPort:}")
    private Integer proxyPort;

    @Bean
    public AllocineApi allocineApi() throws Exception {
        HttpClient httpclient = new DefaultHttpClient();

        if (StringUtils.isNotEmpty(proxyHost) && proxyPort != null) {
            HttpHost proxy = new HttpHost(proxyHost, proxyPort);
            httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        }

        HttpClientWrapper wrapper = new HttpClientWrapper(httpclient);
        wrapper.setUserAgentSelector(new AndroidBrowserUserAgentSelector());

        return new AllocineApi(partner, secret, wrapper);
    }

}