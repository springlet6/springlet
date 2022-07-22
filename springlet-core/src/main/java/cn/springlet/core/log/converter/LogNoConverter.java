package cn.springlet.core.log.converter;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import cn.hutool.core.util.StrUtil;
import cn.springlet.core.enums.HeaderConstantsEnum;

/**
 * 日志流水号转换器
 *
 * @author watermelon
 * @time 2022-4-29 11:31:36
 */
public class LogNoConverter extends ClassicConverter {


    @Override
    public String convert(ILoggingEvent event) {
        String logNo = event.getMDCPropertyMap().get(HeaderConstantsEnum.LOG_NO.name());
        if (StrUtil.isBlank(logNo)) {
            return "";
        }
        return "[" + logNo + "]";
    }

}
