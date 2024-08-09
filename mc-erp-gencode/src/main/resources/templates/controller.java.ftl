package ${package.Controller};

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
<#assign fullPackage = package.Parent>
<#assign module = package.ModuleName>
<#assign packageParts = fullPackage?split(module)>
import ${packageParts[0]}common.response.CommonResponse;

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @date ${date}
 */
@Slf4j
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("/<#if controllerMappingHyphenStyle>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
@Tag(name = "${table.comment!} 控制器", description = "${table.comment!} 控制器")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>
    <#assign serviceName = table.serviceName>
    <#assign serviceNameFirstLower = (serviceName?substring(0, 1)?lower_case + serviceName?substring(1))>
    @Resource
    private ${table.serviceName} ${serviceNameFirstLower};

    /**
     * 通过 id 查询 ${table.serviceName}
     *
     * @param id
     * @return
     */
    @GetMapping("/info")
    @Operation(summary = "通过 id 查询 ${table.serviceName}", description = "通过 id 查询 ${table.serviceName}")
    <#assign entity = entity>
    <#assign entityFirstLower = (entity?substring(0, 1)?lower_case + entity?substring(1))>
    public CommonResponse<${entity}> info(@RequestParam("id") Long id) {
        ${entity} ${entityFirstLower} = ${serviceNameFirstLower}.getById(id);
        return CommonResponse.success(${entityFirstLower});
    }

}
</#if>
