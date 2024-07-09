package com.mc.erp.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mc.erp.admin.domain.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mc.erp.admin.domain.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户信息 Mapper 接口
 * </p>
 *
 * @author mrhuangzh
 * @date 2024/07/04 10:57
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    User getByAccount(@Param("account") String account);

    List<User> getPageByUsername(IPage<User> page, @Param("username") String username);

    IPage<User> getListByUser(IPage<User> page, @Param("user") UserVo user);

}
