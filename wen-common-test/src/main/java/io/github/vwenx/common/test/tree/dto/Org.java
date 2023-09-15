package io.github.vwenx.common.test.tree.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Org {

    private Long id;

    private Long pid;

    private String name;

    public Org(Long id, Long pid, String name) {
        this.id = id;
        this.pid = pid;
        this.name = name;
    }

}
