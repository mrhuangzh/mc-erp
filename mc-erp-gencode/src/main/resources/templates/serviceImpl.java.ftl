package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @date ${date}
 */
@Slf4j
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {
    <#assign mapperName = table.mapperName>
    <#assign mapperNameFirstLower = (mapperName?substring(0, 1)?lower_case + mapperName?substring(1))>
    @Resource
    private ${table.mapperName} ${mapperNameFirstLower};

    public ${entity} getById(Long id) {
        return ${mapperNameFirstLower}.selectById(id);
    }
}
</#if>
