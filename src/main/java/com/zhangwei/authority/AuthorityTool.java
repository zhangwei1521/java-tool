package com.zhangwei.authority;

import com.zhangwei.jsontool.JsonTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class AuthorityTool {
    @Autowired
    private RestTemplate restTemplate;

    public List<ResourceEntity> getResourcesByParam(String reqUrl,String sysCode,String sid,String id,String cookie){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie",cookie);
        headers.set("Accept","application/json, text/javascript, */*; q=0.01");
        MultiValueMap<String,String> param = new LinkedMultiValueMap<>();
        param.add("sysCode",sysCode);
        param.add("id",id);
        param.add("sid",sid);

        HttpEntity<MultiValueMap<String,String>> requestEntity = new HttpEntity<>(param,headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(reqUrl,requestEntity,String.class);
        List<ResourceEntity> list = JsonTool.getListFromJson(responseEntity.getBody(),ResourceEntity.class);
        return list;
    }
}
