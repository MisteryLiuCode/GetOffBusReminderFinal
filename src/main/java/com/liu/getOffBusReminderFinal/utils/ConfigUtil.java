package com.liu.getOffBusReminderFinal.utils;


import com.liu.getOffBusReminderFinal.entity.ProjectConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class ConfigUtil {

    private static Configuration configuration;

    public static Configuration getOffBusReminderConfig() {
        if (configuration == null) {
            try {
                configuration = new PropertiesConfiguration(ProjectConstant.GETOFFREMINDER_CONFIG);
            } catch (ConfigurationException e) {
                log.error("获取配置文件异常！" + e.getMessage());
                return null;
            }
        }
        return configuration;
    }

    public static RestTemplate getTemplate() {
        // 添加拦截器，使用 gzip 编码提交
        ClientHttpRequestInterceptor interceptor = (httpRequest, bytes, execution) -> {
            httpRequest.getHeaders().set("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36");
            httpRequest.getHeaders().set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            httpRequest.getHeaders().set(HttpHeaders.ACCEPT_ENCODING, "gzip");   // 使用 gzip 编码提交
            return execution.execute(httpRequest, bytes);
        };
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build());
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        restTemplate.getInterceptors().add(interceptor);
        return restTemplate;
    }

    /**
     * 获取步行规划url
     * @return
     */
    public static String  getWalkingPlanUrl(Configuration weatherConfig,String oriLong,String oriLat) {

        String walkingPlanUrl = weatherConfig.getString("walkingPlanUrl");

        String key = weatherConfig.getString("key");

        String destinationLong = weatherConfig.getString("destinationLong");

        String destinationLat = weatherConfig.getString("destinationLat");
        // 准备参数
        String resUrl = walkingPlanUrl + "?" +
                "&" + "output=JSON" +
                "&" + "destination=" +destinationLong+ ","+destinationLat+
                "&" + "origin=" +oriLong+ ","+oriLat+
                "&" + "key=" + key;
        return resUrl;
    }
}