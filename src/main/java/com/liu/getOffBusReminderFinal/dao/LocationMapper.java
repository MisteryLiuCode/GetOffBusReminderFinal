package com.liu.getOffBusReminderFinal.dao;

import com.liu.getOffBusReminderFinal.dao.entity.LocationInfoDO;
import com.liu.getOffBusReminderFinal.dao.entity.UserInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LocationMapper {


    /**
     * 根据 id 查询位置
     * @param id
     * @return
     */
   LocationInfoDO queryById(String id);

    /**
     * 通过用户ID查询单条数据
     *
     * @param userId
     * @return 实例对象
     */
    List<LocationInfoDO> queryByUserId(String userId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<LocationInfoDO> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param student 实例对象
     * @return 对象列表
     */
    List<LocationInfoDO> queryAllByUserId(LocationInfoDO locationInfoDO);

    /**
     * 新增数据
     *
     * @param locationInfoDO 实例对象
     * @return 影响行数
     */
    int insert(LocationInfoDO locationInfoDO);

    /**
     * 修改数据
     *
     * @param locationInfoDO 实例对象
     * @return 影响行数
     */
    int update(LocationInfoDO locationInfoDO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);
}
