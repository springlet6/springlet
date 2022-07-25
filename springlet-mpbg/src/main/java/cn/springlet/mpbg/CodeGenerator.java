package cn.springlet.mpbg;

import cn.springlet.core.constant.SqlConstants;
import cn.springlet.mpbg.engine.CustomTemplateEngine;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


@Slf4j
public class CodeGenerator {


    private static DataSourceConfig dsc() {
        // 数据源配置mysql
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        // mysql 8.0版本 驱动
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/demo?useUnicode=true&characterEncoding=UTF8&useSSL=false&nullCatalogMeansCurrent=true");
        dsc.setUsername("root");
        dsc.setPassword("root");
        return dsc;
    }


    // 每个系统parent 包名 【 根据需求修改 】
    private static String PARENT_PACKAGE = "mpbg.cn.springlet.fast";
    //要生成的表
    private static String[] INCLUDE = new String[]{"sys_opt_log"};

    public static String[] tablePrefix = new String[]{"mr_", "pt_", "or_", "td_", "sys_"};


    public static void main(String[] args) throws Exception {

        AutoGenerator mpg = new AutoGenerator();

        // 全局相关配置
        mpg.setGlobalConfig(gc());

        // 数据源绑定
        mpg.setDataSource(dsc());

        // 策略配置项
        StrategyConfig strategy = new StrategyConfig();

        //自定义的 baseMapper
        strategy.setSuperMapperClass("cn.springlet.mybatisplus.mapper.CustomBaseMapper");
        strategy.setSuperServiceClass("cn.springlet.mybatisplus.service.CustomBaseService");
        strategy.setSuperServiceImplClass("cn.springlet.mybatisplus.service.impl.CustomBaseServiceImpl");

        // lombok
        strategy.setEntityLombokModel(true);
        strategy.setChainModel(true);
        strategy.setRestControllerStyle(true);
        //是否生成字段常量
        //strategy.setEntityColumnConstant(true);

        // 表名生成策略 下划线 转驼峰
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setTablePrefix(tablePrefix);

        // 自动填充设置
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill(SqlConstants.CREATE_TIME, FieldFill.INSERT));
        tableFillList.add(new TableFill(SqlConstants.UPDATE_TIME, FieldFill.INSERT_UPDATE));
        tableFillList.add(new TableFill(SqlConstants.VERSION, FieldFill.INSERT));
        tableFillList.add(new TableFill(SqlConstants.IS_DELETED, FieldFill.INSERT));
        strategy.setTableFillList(tableFillList);

        //数据库字段 映射到 实体类属性 策略  下划线 转驼峰
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 需要生成的表,大小写一定要正确
        strategy.setInclude(INCLUDE);

        strategy.setVersionFieldName(SqlConstants.VERSION);
        strategy.setLogicDeleteFieldName(SqlConstants.IS_DELETED);
        mpg.setStrategy(strategy);

        //使用 带 @Mapper 注解的模板文件，原生模板 没有带这个注解，需要手动扫包
        //编译后 resources 中的文件 可以直接通过相对路径获取
        TemplateConfig templateConfig = new TemplateConfig()
                .setController("templates/controller.java")
                .setMapper("templates/mapper.java")
                .setEntity("templates/entity.java");

        //配置自定义模板
        mpg.setTemplate(templateConfig);

        // 包 相关配置
        mpg.setPackageInfo(pc());
        mpg.setTemplateEngine(new CustomTemplateEngine());
        // 执行生成
        mpg.execute();
        log.info("自动构建完成！");

    }

    private static GlobalConfig gc() {
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        final String projectPath = System.getProperty("user.dir");

        gc.setOutputDir(projectPath + OUTPUT_DIR);
        // 是否覆盖已有文件
        gc.setFileOverride(true);
        // 是否打开输出目录
        gc.setOpen(false);
        // 开发人员
        gc.setAuthor(AUTHOR);
        // 开启 ActiveRecord 模式
        gc.setActiveRecord(true);
        // 开启 BaseResultMap
        gc.setBaseResultMap(true);
        // 开启 baseColumnList
        //gc.setBaseColumnList(true);
        // 指定生成的主键的ID类型  雪花算法实现
        gc.setIdType(IdType.ASSIGN_ID);
        gc.setDateType(DateType.ONLY_DATE);
        // 各层文件名称方式，例如： %sAction 生成 UserAction %s 为占位符
        gc.setEntityName("%sDO");
        gc.setMapperName("%sDAO");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sCrud");
        gc.setServiceImplName("%sCrudImpl");
        gc.setControllerName("%sController");
        return gc;
    }


    private static PackageConfig pc() {
        // 包配置
        PackageConfig pc = new PackageConfig();
        // 父包名
        pc.setParent(PARENT_PACKAGE);
        // Entity包名
        pc.setEntity("bean.entity");
        // Mapper包名
        pc.setMapper("dao");
        // Mapper XML包名
        pc.setXml("mapper");
        pc.setService("crud");
        pc.setServiceImpl("crud.impl");
        return pc;
    }


    // 本地mybatis-plus-generator 路径 【 根据需求修改 】
    private static String OUTPUT_DIR = "\\springlet-fast\\src\\main\\java";

    // 注释模板 【 根据需求修改 】
    private static String AUTHOR = "springlet";
}
