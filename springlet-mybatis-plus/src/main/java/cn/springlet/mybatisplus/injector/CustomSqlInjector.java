package cn.springlet.mybatisplus.injector;

import cn.springlet.mybatisplus.injector.methods.SelectByIdForUpdate;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.methods.*;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import icu.mhb.mybatisplus.plugln.injector.JoinDefaultSqlInjector;
import icu.mhb.mybatisplus.plugln.injector.methods.JoinSelectCount;
import icu.mhb.mybatisplus.plugln.injector.methods.JoinSelectList;
import icu.mhb.mybatisplus.plugln.injector.methods.JoinSelectOne;
import icu.mhb.mybatisplus.plugln.injector.methods.JoinSelectPage;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * 自定义 sql 注入器
 *
 * @author watermelon
 * @time 2020/10/23
 */
public class CustomSqlInjector extends JoinDefaultSqlInjector {


    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        Stream.Builder<AbstractMethod> builder = Stream.<AbstractMethod>builder()
                .add(new Insert())
                .add(new Delete())
                .add(new DeleteByMap())
                .add(new cn.springlet.mybatisplus.injector.methods.Update())
                .add(new SelectByMap())
                .add(new SelectCount())
                .add(new SelectMaps())
                .add(new SelectMapsPage())
                .add(new SelectObjs())
                .add(new SelectList())
                .add(new SelectPage());
        if (tableInfo.havePK()) {
            builder.add(new DeleteById())
                    .add(new DeleteBatchByIds())
                    .add(new cn.springlet.mybatisplus.injector.methods.UpdateById())
                    .add(new SelectById())
                    .add(new SelectByIdForUpdate())
                    .add(new SelectBatchByIds());
        } else {
            logger.warn(String.format("%s ,Not found @TableId annotation, Cannot use Mybatis-Plus 'xxById' Method.",
                    tableInfo.getEntityType()));
        }

        //mybatis-plus-join
        builder.add(new JoinSelectList())
                .add(new JoinSelectCount())
                .add(new JoinSelectOne())
                .add(new JoinSelectPage());

        return builder.build().collect(toList());
    }
}
