package com.zx.ioc.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 自定义 返回需要导入的组件
 */
public class MyImportSelector implements ImportSelector {

    /**
     * 返回值就是导入到容器中的组件全类名
     * @param importingClassMetadata : 当前标注@Import注解类的所有注解信息
     * @return
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
//        Set<String> annotationTypes = importingClassMetadata.getAnnotationTypes();
//        for (String type :annotationTypes) {
//            System.out.println("type ...... " + type);
//        }
//        importingClassMetadata.getAllAnnotationAttributes("import")

        /*ConfigurationClassParser.asSourceClasses方法使用返回数组的length 此处不能返回 null 可以返回空的 new String[0]*/
        return new String[]{"Student"};
    }
}
