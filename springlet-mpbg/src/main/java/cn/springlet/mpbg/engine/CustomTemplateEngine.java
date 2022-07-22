package cn.springlet.mpbg.engine;

import cn.hutool.core.util.StrUtil;
import cn.springlet.mpbg.CodeGenerator;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 自定义模板引擎
 *
 * @author watermelon
 * @time 2020/11/27
 */
public class CustomTemplateEngine extends AbstractTemplateEngine {
    private static final String DOT_VM = ".vm";
    private VelocityEngine velocityEngine;

    public static final List<String> moneySuffix = new ArrayList<>();

    static {
        moneySuffix.add("_price");
        moneySuffix.add("_fee");
        moneySuffix.add("_amount");
        moneySuffix.add("_price_total");
        moneySuffix.add("_fee_total");
        moneySuffix.add("_amount_total");
        moneySuffix.add("_balance");
        moneySuffix.add("_amount_subtotal");
        moneySuffix.add("_bonded_fixed");
        moneySuffix.add("_bonded_fixed_subtotal");
        moneySuffix.add("_bonded_fixed_total");
    }

    @Override
    public CustomTemplateEngine init(ConfigBuilder configBuilder) {
        super.init(configBuilder);
        if (null == velocityEngine) {
            Properties p = new Properties();
            p.setProperty(ConstVal.VM_LOAD_PATH_KEY, ConstVal.VM_LOAD_PATH_VALUE);
            p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, StringPool.EMPTY);
            p.setProperty(Velocity.ENCODING_DEFAULT, ConstVal.UTF8);
            p.setProperty(Velocity.INPUT_ENCODING, ConstVal.UTF8);
            p.setProperty("file.resource.loader.unicode", StringPool.TRUE);
            velocityEngine = new VelocityEngine(p);
        }
        return this;
    }


    @Override
    public void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {
        Template template = velocityEngine.getTemplate(templatePath, ConstVal.UTF8);
        try (FileOutputStream fos = new FileOutputStream(outputFile);
             OutputStreamWriter ow = new OutputStreamWriter(fos, ConstVal.UTF8);
             BufferedWriter writer = new BufferedWriter(ow)) {
            template.merge(new VelocityContext(objectMap), writer);
        }
        logger.debug("模板:" + templatePath + ";  文件:" + outputFile);
    }


    @Override
    public String templateFilePath(String filePath) {
        if (null == filePath || filePath.contains(DOT_VM)) {
            return filePath;
        }
        return filePath + DOT_VM;
    }


    /**
     * 输出 java xml 文件
     */
    @Override
    public AbstractTemplateEngine batchOutput() {
        try {
            List<TableInfo> tableInfoList = getConfigBuilder().getTableInfoList();
            for (TableInfo tableInfo : tableInfoList) {

                Map<String, Object> objectMap = getObjectMap(tableInfo);

                List<TableField> fields = tableInfo.getFields();
                for (TableField field : fields) {
                    String comment = field.getComment();

                    try {
                        if (StrUtil.endWithAny(field.getColumnName(), moneySuffix.toArray(new String[0])) && "Long".equals(field.getColumnType().getType())) {
                            tableInfo.getImportPackages().add("cn.springlet.core.lang.Money");
                            field.setEnumPropertyType("Money");
                        }
                    } catch (Exception e) {

                    }

                    try {
                        if (comment.contains(".") && comment.contains("[") && comment.contains("]")) {
                            int start = comment.indexOf("[");
                            int start2 = comment.lastIndexOf(".");
                            int end = comment.indexOf("]");

                            String allClassName = comment.substring(start + 1, end);
                            String enumPropertyType = comment.substring(start2 + 1, end);
                            tableInfo.getImportPackages().add(allClassName);
                            field.setEnumPropertyType(enumPropertyType);
                        }
                    } catch (Exception e) {

                    }
                }

                TableInfo table = (TableInfo) objectMap.get("table");
                objectMap.put("servicename", StrUtil.lowerFirst(table.getServiceName()));
                objectMap.put("controllerMapping", NamingStrategy.removePrefix(table.getName(), CodeGenerator.tablePrefix));

                Map<String, String> pathInfo = getConfigBuilder().getPathInfo();
                TemplateConfig template = getConfigBuilder().getTemplate();
                // 自定义内容
                InjectionConfig injectionConfig = getConfigBuilder().getInjectionConfig();
                if (null != injectionConfig) {
                    injectionConfig.initTableMap(tableInfo);
                    objectMap.put("cfg", injectionConfig.getMap());
                    List<FileOutConfig> focList = injectionConfig.getFileOutConfigList();
                    if (CollectionUtils.isNotEmpty(focList)) {
                        for (FileOutConfig foc : focList) {
                            if (isCreate(FileType.OTHER, foc.outputFile(tableInfo))) {
                                writerFile(objectMap, foc.getTemplatePath(), foc.outputFile(tableInfo));
                            }
                        }
                    }
                }
                // Mp.java
                String entityName = tableInfo.getEntityName();
                if (null != entityName && null != pathInfo.get(ConstVal.ENTITY_PATH)) {
                    String entityFile = String.format((pathInfo.get(ConstVal.ENTITY_PATH) + File.separator + "%s" + suffixJavaOrKt()), entityName);
                    if (isCreate(FileType.ENTITY, entityFile)) {
                        writerFile(objectMap, templateFilePath(template.getEntity(getConfigBuilder().getGlobalConfig().isKotlin())), entityFile);
                    }
                }
                // MpMapper.java
                if (null != tableInfo.getMapperName() && null != pathInfo.get(ConstVal.MAPPER_PATH)) {
                    String mapperFile = String.format((pathInfo.get(ConstVal.MAPPER_PATH) + File.separator + tableInfo.getMapperName() + suffixJavaOrKt()), entityName);
                    if (isCreate(FileType.MAPPER, mapperFile)) {
                        writerFile(objectMap, templateFilePath(template.getMapper()), mapperFile);
                    }
                }
                // MpMapper.xml
                if (null != tableInfo.getXmlName() && null != pathInfo.get(ConstVal.XML_PATH)) {
                    String xmlFile = String.format((pathInfo.get(ConstVal.XML_PATH) + File.separator + tableInfo.getXmlName() + ConstVal.XML_SUFFIX), entityName);
                    if (isCreate(FileType.XML, xmlFile)) {
                        writerFile(objectMap, templateFilePath(template.getXml()), xmlFile);
                    }
                }
                // IMpService.java
                if (null != tableInfo.getServiceName() && null != pathInfo.get(ConstVal.SERVICE_PATH)) {
                    String serviceFile = String.format((pathInfo.get(ConstVal.SERVICE_PATH) + File.separator + tableInfo.getServiceName() + suffixJavaOrKt()), entityName);
                    if (isCreate(FileType.SERVICE, serviceFile)) {
                        writerFile(objectMap, templateFilePath(template.getService()), serviceFile);
                    }
                }
                // MpServiceImpl.java
                if (null != tableInfo.getServiceImplName() && null != pathInfo.get(ConstVal.SERVICE_IMPL_PATH)) {
                    String implFile = String.format((pathInfo.get(ConstVal.SERVICE_IMPL_PATH) + File.separator + tableInfo.getServiceImplName() + suffixJavaOrKt()), entityName);
                    if (isCreate(FileType.SERVICE_IMPL, implFile)) {
                        writerFile(objectMap, templateFilePath(template.getServiceImpl()), implFile);
                    }
                }

                String entity = (String) objectMap.get("entity");
                Map<String, String> packageInfo = (Map<String, String>) objectMap.get("package");


                // VO.java
                objectMap.put("vo", entity.split("DO")[0] + "VO");
                objectMap.put("VoPackage", packageInfo.get("Entity").replace("entity", "vo"));
                String voPath = pathInfo.get(ConstVal.ENTITY_PATH).replace("entity", "vo");
                String voName = tableInfo.getEntityName().replace("DO", "VO");
                String voFile = String.format(voPath + File.separator + voName + suffixJavaOrKt(), entityName);
                File vofile = new File(voFile);
                boolean exist = vofile.exists();
                if (!exist) {
                    vofile.getParentFile().mkdirs();
                }
                writerFile(objectMap, templateFilePath("templates/vo.java"), voFile);

                //DTO.java
                objectMap.put("dto", entity.split("DO")[0] + "DTO");
                objectMap.put("DtoPackage", packageInfo.get("Entity").replace("entity", "dto"));
                String dtoPath = pathInfo.get(ConstVal.ENTITY_PATH).replace("entity", "dto");
                String dtoName = tableInfo.getEntityName().replace("DO", "DTO");
                String dtoFile = String.format(dtoPath + File.separator + dtoName + suffixJavaOrKt(), entityName);
                File dtofile = new File(dtoFile);
                boolean dtoExist = dtofile.exists();
                if (!dtoExist) {
                    dtofile.getParentFile().mkdirs();
                }
                writerFile(objectMap, templateFilePath("templates/dto.java"), dtoFile);

                //QUERY.java
                objectMap.put("query", entity.split("DO")[0] + "Query");
                objectMap.put("queryPackage", packageInfo.get("Entity").replace("entity", "dto.query"));
                String queryPath = pathInfo.get(ConstVal.ENTITY_PATH).replace("entity", "dto") + File.separator + "query";
                String queryName = tableInfo.getEntityName().replace("DO", "Query");
                String queryFile = String.format(queryPath + File.separator + queryName + suffixJavaOrKt(), entityName);
                File queryfile = new File(queryFile);
                boolean queryExist = queryfile.exists();
                if (!queryExist) {
                    queryfile.getParentFile().mkdirs();
                }
                writerFile(objectMap, templateFilePath("templates/query.java"), queryFile);


                objectMap.put("getPK", "get" + StrUtil.upperFirst(objectMap.get("keyPropertyName").toString()));
                // MpController.java
                if (null != tableInfo.getControllerName() && null != pathInfo.get(ConstVal.CONTROLLER_PATH)) {
                    String controllerFile = String.format((pathInfo.get(ConstVal.CONTROLLER_PATH) + File.separator + tableInfo.getControllerName() + suffixJavaOrKt()), entityName);
                    if (isCreate(FileType.CONTROLLER, controllerFile)) {
                        writerFile(objectMap, templateFilePath(template.getController()), controllerFile);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("无法创建文件，请检查配置信息！", e);
        }
        return this;
    }

}
