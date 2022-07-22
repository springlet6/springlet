package cn.springlet.mybatisplus.annotation;


import com.baomidou.mybatisplus.core.conditions.Wrapper;

import java.lang.annotation.*;

/**
 * 禁止修改注解
 * 在 DO 中，不允许修改的属性上加上此注解，调用 {@link cn.springlet.mybatisplus.mapper.CustomBaseMapper#updateById(Object)} 方法时，
 * 不管有没有设置这个属性的值，都 不会实际修改 这个属性
 * <p>
 * 调用  {@link cn.springlet.mybatisplus.mapper.CustomBaseMapper#update(Object, Wrapper)}  时， 实体类中设置了属性值，也 不会实际 修改属性值
 * 如果使用了 wrapper 的 set 方法，如：new UpdateWrapper<ComBrandDO>().eq("com_brand_id", 139).set("brand_country","123") ，
 * 这时候 {@link DisabledModify} 注解是不生效的 ，因为  UpdateWrapper 的 set 语句 是在 运行时拼接上去的。
 * 不建议 使用  UpdateWrapper ！！！  很容易造成全表无差别修改
 *
 * @author watermelon
 * @time 2020/12/2
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DisabledModify {
}
