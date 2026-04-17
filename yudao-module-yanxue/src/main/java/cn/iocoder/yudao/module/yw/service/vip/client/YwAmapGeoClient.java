package cn.iocoder.yudao.module.yw.service.vip.client;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_VIPINFO_MAP_GEOCODE_FAILED;
import static cn.iocoder.yudao.module.yw.enums.ErrorCodeConstants.YW_VIPINFO_MAP_KEY_NOT_CONFIG;

@Component
public class YwAmapGeoClient {

    @Value("${yw.map.amap.key:}")
    private String amapKey;

    @Value("${yw.map.amap.geocode-url:https://restapi.amap.com/v3/geocode/geo}")
    private String geocodeUrl;

    @Resource
    private RestTemplate restTemplate;

    public GeoPoint geocode(String address) {
        if (!StringUtils.hasText(amapKey)) {
            throw exception(YW_VIPINFO_MAP_KEY_NOT_CONFIG);
        }
        if (!StringUtils.hasText(address)) {
            throw exception(YW_VIPINFO_MAP_GEOCODE_FAILED);
        }
        URI uri = UriComponentsBuilder.fromHttpUrl(geocodeUrl)
                .queryParam("key", amapKey)
                .queryParam("address", address.trim())
                .encode()
                .build()
                .toUri();
        ResponseEntity<Map> response = restTemplate.getForEntity(uri, Map.class);
        Map body = response.getBody();
        if (body == null) {
            throw exception(YW_VIPINFO_MAP_GEOCODE_FAILED);
        }
        Object status = body.get("status");
        if (!"1".equals(String.valueOf(status))) {
            throw exception(YW_VIPINFO_MAP_GEOCODE_FAILED);
        }
        Object geocodesObj = body.get("geocodes");
        if (!(geocodesObj instanceof List) || ((List<?>) geocodesObj).isEmpty()) {
            throw exception(YW_VIPINFO_MAP_GEOCODE_FAILED);
        }
        Object first = ((List<?>) geocodesObj).get(0);
        if (!(first instanceof Map)) {
            throw exception(YW_VIPINFO_MAP_GEOCODE_FAILED);
        }
        Object locationObj = ((Map<?, ?>) first).get("location");
        if (!StringUtils.hasText(String.valueOf(locationObj))) {
            throw exception(YW_VIPINFO_MAP_GEOCODE_FAILED);
        }
        String[] arr = String.valueOf(locationObj).split(",");
        if (arr.length != 2) {
            throw exception(YW_VIPINFO_MAP_GEOCODE_FAILED);
        }
        try {
            return new GeoPoint(new BigDecimal(arr[0]), new BigDecimal(arr[1]));
        } catch (Exception ex) {
            throw new ServiceException(YW_VIPINFO_MAP_GEOCODE_FAILED.getCode(), YW_VIPINFO_MAP_GEOCODE_FAILED.getMsg());
        }
    }

    @Data
    @AllArgsConstructor
    public static class GeoPoint {
        private BigDecimal longitude;
        private BigDecimal latitude;
    }
}
