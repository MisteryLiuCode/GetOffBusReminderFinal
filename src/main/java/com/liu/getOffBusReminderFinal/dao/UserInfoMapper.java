package com.liu.getOffBusReminderFinal.dao;

import com.liu.getOffBusReminderFinal.dao.entity.UserInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liushuaibiao
 * @date 2023/4/17 18:48
 */
@Mapper
@Repository
public interface UserInfoMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    UserInfoDO queryByUserId(String userId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<UserInfoDO> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param student 实例对象
     * @return 对象列表
     */
    List<UserInfoDO> queryAll(UserInfoDO student);

    /**
     * 新增数据
     *
     * @param userInfoDO 实例对象
     * @return 影响行数
     */
    int insert(UserInfoDO userInfoDO);

    /**
     * 修改数据
     *
     * @param userInfoDO 实例对象
     * @return 影响行数
     */
    int update(UserInfoDO userInfoDO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);
}
