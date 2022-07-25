package cn.springlet.mybatisplus.mapper;

import icu.mhb.mybatisplus.plugln.base.mapper.JoinBaseMapper;

import java.io.Serializable;

/**
 * 自定义baseMapper
 * 增加了悲观锁方法 for update
 * 注意使用 for update 的时候
 * 当条件为索引或者主键的时候，只会锁住索引或者主键对应的行
 * 当for update的字段为普通字段的时候，会锁住整张表
 *
 * @author watermelon
 * @time 2020/10/23
 */
public interface CustomBaseMapper<T> extends JoinBaseMapper<T> {
    /**
     * 根据id查询一条数据 并加上 悲观锁
     *
     * @param id 主键id
     * @return
     */
    T selectByIdForUpdate(Serializable id);
}
