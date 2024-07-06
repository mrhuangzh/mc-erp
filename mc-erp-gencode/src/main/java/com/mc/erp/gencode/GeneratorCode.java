package com.mc.erp.gencode;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Types;
import java.util.Collections;

/**
 * @author: mrhuangzh
 * @date: 2024/7/3 16:27
 **/
public class GeneratorCode {
    public static void main(String[] args) throws Exception {
        URI classUri = GeneratorCode.class.getProtectionDomain().getCodeSource().getLocation().toURI();
        Path classPath = Paths.get(classUri);
        Path generatedCodePath = classPath.getParent().getParent();
        // 定义数据库连接参数
        String host = "xxx.xxx.xxx.xxx";
        String port = "3306";
        String database = "mc-erp-admin";
        String username = "root";
        String password = "password";

        String useUnicode = "true";
        String characterEncoding = "utf-8";
        String serverTimezone = "GMT%2B8";
        String remarks = "true";
        String useInformationSchema = "true";

        String url = String.format(
                "jdbc:mysql://%s:%s/%s?useUnicode=%s&characterEncoding=%s&serverTimezone=%s&remarks=%s&useInformationSchema=%s",
                host, port, database, useUnicode, characterEncoding, serverTimezone, remarks, useInformationSchema);

        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("mrhuangzh") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
//                            .fileOverride() // 覆盖已生成文件
                            .commentDate("yyyy/MM/dd hh:mm")
//                            .outputDir(System.getProperty("user.dir") + "\\src\\main\\resources\\java"); // 指定输出目录
                            .outputDir(generatedCodePath + "\\src\\main\\resources\\java"); // 指定输出目录
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    } else if (typeCode == Types.BIT){
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> {
                    builder.parent("com.mc.erp") // 设置父包名
                            .moduleName("admin") // 设置父包模块名
                            .entity("entity")
                            .service("service")
                            .serviceImpl("service.impl")
                            .controller("controller")
                            .mapper("mapper")
                            .xml("mapper")
                            .pathInfo(Collections.singletonMap(OutputFile.xml,
//                                    System.getProperty("user.dir") + "\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                                    generatedCodePath + "\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder
                            .addInclude("t_user") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_") // 设置过滤表前缀
                            .entityBuilder()
//                            .superClass("com.xxx.dao.common.entity.BaseEntity")
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            .entityBuilder()
                            .enableLombok()
                            .logicDeleteColumnName("deleted")
                            .enableTableFieldAnnotation()
                            .controllerBuilder()
                            // 映射路径使用连字符格式，而不是驼峰
                            .enableHyphenStyle()
                            .formatFileName("%sController")
                            .enableRestStyle()
                            .mapperBuilder()
                            //生成通用的resultMap
                            .enableBaseResultMap()
                            .superClass(BaseMapper.class)
                            .formatMapperFileName("%sMapper")
                            .enableMapperAnnotation()
                            .formatXmlFileName("%sMapper");
                })
                .templateConfig(config -> {
                    config.disable(TemplateType.ENTITY)
                            .controller("/templates/controller.java")
                            .entity("/templates/entity.java")
                            .mapper("/templates/mapper.java")
                            .xml("/templates/mapper.xml")
                            .service("/templates/service.java")
                            .serviceImpl("/templates/serviceImpl.java")
                            .build();
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
