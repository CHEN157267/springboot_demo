package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * UserMapper：数据访问层（DAO）
 * 继承 MyBatis-Plus 的 BaseMapper<User> 后，就自动拥有了增删改查方法：
 *   selectList(null)  -> 查全部
 *   selectById(id)    -> 按主键查一条
 *   insert(user)      -> 新增
 *   updateById(user)  -> 按主键修改
 *   deleteById(id)    -> 按主键删除
 * 一行 SQL 都不用写。
 *
 * @Mapper 告诉 Spring：这是个 Mapper 接口，启动时帮它生成实现类并放进容器。
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
