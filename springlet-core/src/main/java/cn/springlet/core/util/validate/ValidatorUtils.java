package cn.springlet.core.util.validate;

import cn.springlet.core.exception.web_return.ParameterVerificationException;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 参数验证工具
 * 1.用于业务层的某个方法所定义的入参验证
 * 2.用于controller层绑定的复合对象的验证
 * 使用方法:
 * public boolean orderCheck(OrderCheckBO orderCheckBO) {
 * //对参数对象进行数据校验
 * ValidatorUtils.validate(orderCheckBO);
 * return true;
 * }
 *
 * @Data
 * @Builder public class OrderCheckBO {
 * @NotNull(message = "订单号不能为空")
 * private String orderId;
 * @Min(value = 1, message = "订单金额不能小于0")
 * private Integer orderAmount;
 * @NotNull(message = "创建人不能为空")
 * private String operator;
 * @NotNull(message = "操作时间不能为空")
 * private String operatorTime;
 * @EnumValue(enumValues = StatusEnum.class, message = "状态值不在指定范围")
 * *  private String status;
 * }
 * @Author moyan
 * @Version 1.0 Create on 2020年12月15 10:05
 */
@Slf4j
public class ValidatorUtils {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * bean整体校验，有不合规范，抛出第1个违规异常
     */
    public static void validate(Object obj, Class<?>... groups) {
        Set<ConstraintViolation<Object>> resultSet = validator.validate(obj, groups);
        if (resultSet.size() > 0) {
            //如果存在错误结果，则将其解析并进行拼凑后异常抛出
            List<String> errorMessageList = resultSet.stream().map(o -> o.getMessage()).collect(
                    Collectors.toList());
            throw new ParameterVerificationException(errorMessageList.get(0));
        }
    }

}
