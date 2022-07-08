package cn.springlet.core.util.easy_excel.converter;

import cn.springlet.core.lang.Money;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * Money 对象的 转换器
 *
 * @author watermelon
 * @time 2021/3/24
 */
public class MoneyConverter implements Converter<Money> {
    @Override
    public Class supportJavaTypeKey() {
        return Money.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Money convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        switch (cellData.getType()) {
            case STRING:
                return new Money(cellData.getStringValue());
            case NUMBER:
                return new Money(cellData.getNumberValue());
            default:
        }
        return null;
    }

    @Override
    public CellData convertToExcelData(Money value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if (value != null) {
            return new CellData(value.getAmount());
        }
        return null;
    }
}
