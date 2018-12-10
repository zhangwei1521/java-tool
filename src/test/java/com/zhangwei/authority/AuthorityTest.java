package com.zhangwei.authority;

import com.zhangwei.AppConfig;
import com.zhangwei.exceltool.ExcelUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class AuthorityTest {
    @Autowired
    private AuthorityTool authorityTool;

    @Test
    public void test01(){
        String url = "";
        String sysCode = "";
        String sid = "";
        String id = "";
        String cookie = "";
        List<ResourceEntity> list = authorityTool.getResourcesByParam(url,sysCode,sid,id,cookie);
        if(list != null && list.size()>0){
            try {
                ExcelUtils.writeToExcel(list);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            System.out.println("获取资源失败");
        }
    }

}
