package io.github.vwenx.common.test.fieldfill.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SexEnum {

    男(1, "男")
    ,女(2, "女")
    ;

    private final int type;
    private final String text;

    public static String getTextByType(int type){
        for (SexEnum value : values()) {
            if (value.type == type){
                return value.text;
            }
        }
        return null;
    }

}
