package cn.springlet.mybatisplus.service.impl;

import cn.springlet.core.constant.SqlConstants;
import cn.springlet.core.util.BeanUtil;
import cn.springlet.mybatisplus.mapper.CustomBaseMapper;
import cn.springlet.mybatisplus.service.CustomBaseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;
import java.util.List;

/**
 * 自定义 serviceimpl
 *
 * @author watermelon
 * @time 2020/10/23
 */
public class CustomBaseServiceImpl<M extends CustomBaseMapper<T>, T> extends ServiceImpl<M, T> implements CustomBaseService<T> {
    @Override
    public long count(T entity) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(entity);
        return super.count(queryWrapper);
    }


    @Override
    public T getByIdForUpdate(Serializable id) {
        return baseMapper.selectByIdForUpdate(id);
    }

    @Override
    public T getOneForUpdate(T entity) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(entity).last(SqlConstants.FOR_UPDATE);
        return super.getOne(queryWrapper);
    }

    @Override
    public T getOneForUpdate(QueryWrapper<T> queryWrapper) {
        queryWrapper.last(SqlConstants.FOR_UPDATE);
        return super.getOne(queryWrapper);
    }

    @Override
    public T getOne(T entity) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(entity);
        return super.getOne(queryWrapper);
    }

    @Override
    public <V> V getOneVO(QueryWrapper<T> queryWrapper, Class<V> clazz) {
        T one = super.getOne(queryWrapper);
        if (one == null) {
            return null;
        }
        return BeanUtil.toBean(one, clazz);
    }

    @Override
    public <V> V getOneVO(T entity, Class<V> clazz) {
        T one = getOne(entity);
        if (one == null) {
            return null;
        }
        return BeanUtil.toBean(one, clazz);
    }

    @Override
    public <V> V getVOById(Serializable id, Class<V> clazz) {
        T one = super.getById(id);
        if (one == null) {
            return null;
        }
        return BeanUtil.toBean(one, clazz);
    }

    @Override
    public List<T> listByEntity(T entity) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(entity);
        return super.list(queryWrapper);
    }

    @Override
    public <V> List<V> listVOByEntity(T entity, Class<V> clazz) {
        return BeanUtil.copyList(listByEntity(entity), clazz);
    }

    @Override
    public <V> List<V> listVOByEntity(QueryWrapper<T> queryWrapper, Class<V> clazz) {
        return BeanUtil.copyList(list(queryWrapper), clazz);
    }

    @Override
    public boolean remove(T entity) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(entity);
        return super.remove(queryWrapper);
    }
}
