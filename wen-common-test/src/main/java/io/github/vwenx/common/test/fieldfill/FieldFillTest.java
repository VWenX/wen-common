package io.github.vwenx.common.test.fieldfill;

import io.github.vwenx.common.fieldfill.FieldFillHelper;
import io.github.vwenx.common.fieldfill.FieldFillStufferRegistry;
import io.github.vwenx.common.fieldfill.stuffer.builder.BatchModeStufferBuilder;
import io.github.vwenx.common.test.fieldfill.dto.Car;
import io.github.vwenx.common.test.fieldfill.dto.User;
import io.github.vwenx.common.test.fieldfill.enums.SexEnum;
import io.github.vwenx.common.test.fieldfill.service.Service;
import io.github.vwenx.common.test.fieldfill.service.mock.MockService;

import java.util.Arrays;

public class FieldFillTest {

    private static final Service service = new MockService();

    public static void main(String[] args) {

        // 注册填充器
        FieldFillStufferRegistry.register(
                FieldFillTypes.User_idToName,
                BatchModeStufferBuilder.of(service::getNameByIds)
        );

        // 构造模拟数据
        User user = new User();
        user.setSex(SexEnum.女.getType());
        user.setCreateBy(2L);
        user.setCars(Arrays.asList(new Car("red"), new Car("blue"), new Car("red")));
        System.out.println("构造模拟数据:");
        System.out.println(user);

        // 调用填充
        FieldFillHelper.fill(user);
        System.out.println("调用填充后:");
        System.out.println(user);
    }


}
