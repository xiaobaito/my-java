package com.zx.ioc.config;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

public class MyTypeFilter implements TypeFilter {
    /**
     * @param metadataReader        读取到当前正在扫描的类信息
     * @param metadataReaderFactory 可以获取到其他类的任何信息
     * @return
     * @throws IOException
     */
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        //获取当前注解类的信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();

        //获取当前正在扫描的信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        // 获取当前类资源 (类路径)
        Resource resource = metadataReader.getResource();

        String name = classMetadata.getClassName();
        System.out.println("获取到的扫描的类名 ：" + name);
        if (name.contains("contro")) {
            return true;
        }

        return false;
    }
}
