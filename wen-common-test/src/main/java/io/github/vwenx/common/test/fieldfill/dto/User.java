package io.github.vwenx.common.test.fieldfill.dto;

import io.github.vwenx.common.fieldfill.annotation.FieldFill;
import io.github.vwenx.common.fieldfill.annotation.ScanFieldFill;
import io.github.vwenx.common.fieldfill.constant.FieldFillType;
import io.github.vwenx.common.test.fieldfill.FieldFillTypes;
import io.github.vwenx.common.test.fieldfill.enums.SexEnum;
import lombok.Data;

import java.util.List;

@Data
public class User {

    private int sex;

    @FieldFill(type = FieldFillType.Enum, source = "sex", classArgs = SexEnum.class, args = "getTextByType()")
    private String sexText;

    private Long createBy;

    @FieldFill(type = FieldFillTypes.User_idToName, source = "createBy")
    private String createBy_Name;

    @ScanFieldFill
    private List<Car> cars;

}
