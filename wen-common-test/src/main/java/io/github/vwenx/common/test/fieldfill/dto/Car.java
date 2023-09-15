package io.github.vwenx.common.test.fieldfill.dto;

import io.github.vwenx.common.fieldfill.annotation.FieldFill;
import io.github.vwenx.common.fieldfill.constant.FieldFillType;
import io.github.vwenx.common.test.fieldfill.enums.ColorEnum;
import lombok.Data;

@Data
public class Car {

    @FieldFill(type = FieldFillType.Enum, classArgs = ColorEnum.class, args = {"en", "cn"}, skipIfTargetNonNull = false)
    private String color;

    public Car(String color) {
        this.color = color;
    }

}
