package cn.springlet.mybatisplus.service;

import cn.hutool.core.collection.CollUtil;
import cn.springlet.core.exception.web_return.DataDoesNotExistException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 自定义 service
 *
 * @author watermelon
 * @time 2020/10/23
 */
public interface CustomBaseService<T> extends IService<T> {

    /**
     * count
     *
     * @param entity
     * @return
     */
    long count(T entity);

    /**
     * 悲观锁
     *
     * @param id
     * @return
     */
    T getByIdForUpdate(Serializable id);

    /**
     * 悲观锁 不为空
     *
     * @param id
     * @return
     */
    default T getByIdForUpdateNn(Serializable id, String... failMsg) {
        T t = getByIdForUpdate(id);
        if (t == null) {
            throwFailMsg(failMsg);
        }
        return t;
    }

    /**
     * 悲观锁
     *
     * @param entity
     * @return
     */
    T getOneForUpdate(T entity);

    /**
     * 悲观锁 不为空
     *
     * @param entity
     * @return
     */
    default T getOneForUpdateNn(T entity, String... failMsg) {
        T t = getOneForUpdate(entity);
        if (t == null) {
            throwFailMsg(failMsg);
        }
        return t;
    }

    /**
     * 悲观锁
     *
     * @param queryWrapper
     * @return
     */
    T getOneForUpdate(QueryWrapper<T> queryWrapper);

    /**
     * 悲观锁 不为空
     *
     * @param queryWrapper
     * @return
     */
    default T getOneForUpdateNn(QueryWrapper<T> queryWrapper, String... failMsg) {
        T t = getOneForUpdate(queryWrapper);
        if (t == null) {
            throwFailMsg(failMsg);
        }
        return t;
    }

    /**
     * 条件 单条 单表 查询
     * 如果条件下有多条或不存在 则返回null
     * 直接将条件传入 T中 对应字段
     *
     * @param entity
     * @return
     */
    T getOne(T entity);

    /**
     * 不为空
     *
     * @param entity
     * @return
     */
    default T getOneNn(T entity, String... failMsg) {
        T t = getOne(entity);
        if (t == null) {
            throwFailMsg(failMsg);
        }
        return t;
    }

    /**
     * 不为空
     *
     * @param queryWrapper
     * @return
     */
    default T getOneNn(QueryWrapper<T> queryWrapper, String... failMsg) {
        T t = getOne(queryWrapper);
        if (t == null) {
            throwFailMsg(failMsg);
        }
        return t;
    }


    /**
     * 条件 单条 多表 查询
     * 如果条件下有多条或不存在 则返回null
     *
     * @param queryWrapper
     * @return
     */
    <V> V getOneVO(QueryWrapper<T> queryWrapper, Class<V> clazz);

    /**
     * 条件 单条 多表 查询
     * 如果条件下有多条或不存在 则null
     * 直接将条件传入 T中 对应字段
     *
     * @param entity
     * @return
     */
    <V> V getOneVO(T entity, Class<V> clazz);

    /**
     * 通过id 查询VO
     *
     * @param id
     * @return
     */
    <V> V getVOById(Serializable id, Class<V> clazz);


    /**
     * 查询（根据ID 批量查询）
     *
     * @param idList 主键ID列表
     */
    default List<T> listByIdsNn(Collection<? extends Serializable> idList, String... failMsg) {
        if (CollUtil.isEmpty(idList)) {
            throwFailMsg(failMsg);
        }
        List<T> ts = listByIds(idList);
        if (CollUtil.isEmpty(ts)) {
            throwFailMsg(failMsg);
        }
        return ts;
    }

    /**
     * 列表
     *
     * @param entity
     * @return
     */
    List<T> listByEntity(T entity);

    /**
     * 列表
     *
     * @param entity
     * @return
     */
    <V> List<V> listVOByEntity(T entity, Class<V> clazz);

    /**
     * 列表
     *
     * @param queryWrapper
     * @return
     */
    <V> List<V> listVOByEntity(QueryWrapper<T> queryWrapper, Class<V> clazz);

    /**
     * 条件删除
     *
     * @param entity
     * @return
     */
    boolean remove(T entity);


    default void throwFailMsg(String[] failMsg) {
        if (failMsg != null && failMsg.length > 0) {
            throw new DataDoesNotExistException(String.join(",", failMsg));
        } else {
            throw new DataDoesNotExistException("数据不存在");
        }
    }
}
