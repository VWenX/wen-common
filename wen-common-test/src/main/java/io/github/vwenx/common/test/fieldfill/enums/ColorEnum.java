package io.github.vwenx.common.test.fieldfill.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ColorEnum {

    red("red", "红")
    ,blue("blue", "蓝")
    ;

    private final String en;
    private final String cn;


}
