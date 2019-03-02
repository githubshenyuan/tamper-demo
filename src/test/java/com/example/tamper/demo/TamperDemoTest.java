package com.example.tamper.demo;

import com.alibaba.tamper.BeanMapping;
import com.alibaba.tamper.core.config.BeanMappingConfigHelper;
import com.example.tamper.bean.official.NestedSrcMappingObject;
import com.example.tamper.bean.official.NestedTargetMappingObject;
import com.example.tamper.bean.official.SrcMappingObject;
import com.example.tamper.bean.official.TargetMappingObject;
import com.example.tamper.bean.simple.ComponentSrc;
import com.example.tamper.bean.simple.ComponentTar;
import com.example.tamper.bean.simple.Src;
import com.example.tamper.bean.simple.Tar;
import junit.framework.TestCase;
import org.junit.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author sh on 2018/9/24
 * @version ideaIU-2017.2.5.win
 */
public class TamperDemoTest extends TestCase {

    @Test
    public void testOfficial() throws NoSuchFieldException, IllegalAccessException {
        Field field = Src.class.getField("name");
        Src src = new Src();
        src.setName("xiaoming");
        System.out.println(field.get(src));
        BeanMapping srcMapping    = null;
        BeanMapping targetMapping = null;
        BeanMappingConfigHelper.getInstance().registerConfig("mapping/mapping-official.xml");

        srcMapping = BeanMapping.create(SrcMappingObject.class, TargetMappingObject.class);
        targetMapping = BeanMapping.create(TargetMappingObject.class, SrcMappingObject.class);

        SrcMappingObject srcRef = new SrcMappingObject();
        srcRef.setIntegerValue(1);
        srcRef.setIntValue(1);
        srcRef.setName("ljh");
        srcRef.setStart(true);
        NestedSrcMappingObject nestedSrcRef = new NestedSrcMappingObject();
        nestedSrcRef.setBigDecimalValue(BigDecimal.ONE);
        srcRef.setMapping(nestedSrcRef);
        List<NestedSrcMappingObject> nestedSrcMappingObjects = new ArrayList<NestedSrcMappingObject>();
        nestedSrcMappingObjects.add(nestedSrcRef);
        srcRef.setListMapping(nestedSrcMappingObjects);
        TargetMappingObject targetRef = new TargetMappingObject();// 测试一下mapping到一个Object对象
        srcMapping.mapping(srcRef, targetRef);
        NestedTargetMappingObject nestedTargetMappingObject = targetRef.getMapping();
        SrcMappingObject newSrcRef = new SrcMappingObject();// 反过来再mapping一次
        targetMapping.mapping(targetRef, newSrcRef);
        NestedSrcMappingObject nestedSrcMappingObject= newSrcRef.getMapping();
    }
    @Test
    public void testSimple() {
        BeanMappingConfigHelper.getInstance().registerConfig("mapping/mapping-simple.xml");
        BeanMapping srcMap = BeanMapping.create(Src.class, Tar.class);
        Src src = new Src();
        src.setName("xixoxoxo");
        src.setStrDate("2018-09-24 14:36:45");
        src.setDate(new Date());

        ComponentSrc componentSrc = new ComponentSrc();
        componentSrc.setName("账号");
        componentSrc.setPassword("密码");
        src.setComponentSrc(componentSrc);

        List<ComponentSrc> componentSrcList = new ArrayList<ComponentSrc>();
        componentSrcList.add(componentSrc);
        src.setComponentSrcList(componentSrcList);
        List<ComponentSrc> a = new ArrayList<ComponentSrc>();
        ComponentSrc componentSrc1 = new ComponentSrc();
        componentSrc1.setName("123");
        componentSrc1.setPassword("pass");
        a.add(componentSrc1);
        src.setComponentSrcList1(a);

        Tar tar = new Tar();
        srcMap.mapping(src,tar);
        srcMap = BeanMapping.create(Src.class, ComponentTar.class);
        ComponentTar componentTar = new ComponentTar();
        tar.setComponentTar1(componentTar);
        srcMap.mapping(src,componentTar);

        System.out.println("结束");

    }
}
