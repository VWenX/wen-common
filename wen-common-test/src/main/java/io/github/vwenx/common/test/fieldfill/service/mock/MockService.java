package io.github.vwenx.common.test.fieldfill.service.mock;

import io.github.vwenx.common.test.fieldfill.service.Service;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

// mock服务实现
public class MockService implements Service {

    private final MockDataRow[] mockData = {
        new MockDataRow(1L, "admin", "管理员"),
        new MockDataRow(2L, "xuwen", "许文"),
        new MockDataRow(3L, "lihua", "李华"),
    };


    public Map<Long, String> getNameByIds(Collection<Long> ids){
        HashSet<Long> idSet = new HashSet<>(ids);
        return Arrays.stream(mockData)
                .filter(o -> idSet.contains(o.getId()))
                .collect(Collectors.toMap(
                        MockDataRow::getId, MockDataRow::getName
                ));
    }


    @Data
    @AllArgsConstructor
    private static class MockDataRow {
        private Long id;
        private String account;
        private String name;
    }

}
