package io.github.vwenx.common.test.tree.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OrgTreeNode extends Org {

    private List<OrgTreeNode> children;

    public static OrgTreeNode of(Org org){
        return new OrgTreeNode(org.getId(), org.getPid(), org.getName());
    }

    public OrgTreeNode(Long id, Long pid, String name) {
        super(id, pid, name);
    }

}
