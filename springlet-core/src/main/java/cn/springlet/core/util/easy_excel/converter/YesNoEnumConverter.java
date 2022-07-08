package cn.springlet.core.util.easy_excel.converter;

import cn.springlet.core.enums.YesNoEnum;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import lombok.extern.slf4j.Slf4j;

/**
 * YesNoEnum 枚举的转换器
 *
 * @author watermelon
 * @time 2021/3/24
 */
@Slf4j
public class YesNoEnumConverter implements Converter<YesNoEnum> {

    @Override
    public Class supportJavaTypeKey() {
        return YesNoEnum.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public YesNoEnum convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if (cellData.getStringValue() == null) {
            return null;
        }
        if (YesNoEnum.Y.getValue().equals(cellData.getStringValue())) {
            return YesNoEnum.Y;
        } else {
            return YesNoEnum.N;
        }
    }

    @Override
    public CellData convertToExcelData(YesNoEnum value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData(value.getValue());
    }
}
