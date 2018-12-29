package com.example.tamper.demo;

import com.alibaba.tamper.BeanMapping;
import com.alibaba.tamper.core.config.BeanMappingConfigHelper;
import com.example.tamper.bean.UserSrc;
import com.example.tamper.bean.UserTar;
import com.example.tamper.bean.official.SrcMappingObject;
import com.example.tamper.bean.official.TargetMappingObject;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * @author sh 2018/12/17 18:06
 * @version ideaIU-2018.2.5.win
 */
public class SingleDemo extends TestCase {

    @Test
    public void testFor() {
        for (int i = 0; i < 100; i++) {
            testSingleDemo();
        }

    }


    public void testSingleDemo(){
        BeanMapping srcMapping    = null;
        BeanMapping targetMapping = null;
        BeanMappingConfigHelper.getInstance().registerConfig("mapping/simple.xml");

        srcMapping = BeanMapping.create(UserSrc.class, UserTar.class);
        //targetMapping = BeanMapping.create(UserTar.class, UserSrc.class);

        UserSrc userSrc = new UserSrc();
        userSrc.setSrcUuid("123");
        userSrc.setSrcUserName("123");
        userSrc.setSrcPass("123");
        UserTar userTar = new UserTar();

        srcMapping.mapping( userSrc,userTar);

        BeanMappingConfigHelper.getInstance().registerConfig("mapping/simple-1.xml");
        UserSrc userSrc1 = new UserSrc();
        UserTar userTar1 = new UserTar();

        srcMapping.mapping( userSrc1,userTar1);
        System.out.println("nihao");
    }
}
