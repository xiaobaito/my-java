package com.zx.test.extension;

import com.zx.extension.MainConfigOfExtension;
import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ExtensionTest {

    @Test
    public void test01() {
        AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext(MainConfigOfExtension.class);
        acac.publishEvent(new ApplicationEvent("发布的事件0") {

        });
        acac.close();
    }
}
