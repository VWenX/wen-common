package io.github.vwenx.common.test.tree;

import io.github.vwenx.common.tree.TreeUtil;
import io.github.vwenx.common.test.tree.dto.Org;
import io.github.vwenx.common.test.tree.dto.OrgTreeNode;

import java.util.Arrays;
import java.util.List;

public class TreeTest {


    public static void main(String[] args) {
        List<Org> orgList = Arrays.asList(
                new Org(1L, 0L, "第一事业部")
                , new Org(2L, 0L, "第二事业部")
                , new Org(3L, 1L, "一事业部-研发部")
        );

        System.out.println(orgList);

        List<OrgTreeNode> tree = TreeUtil.toTree(orgList, OrgTreeNode::of, Long.class)
                .config(Org::getId, Org::getPid, OrgTreeNode::setChildren)
                .gen(0L);
        System.out.println("tree:");
        System.out.println(tree);

        List<OrgTreeNode> tree2 = TreeUtil.toTree(orgList, OrgTreeNode::of, Long.class)
                .config(Org::getId, Org::getPid, OrgTreeNode::setChildren)
                .gen(1L);
        System.out.println("tree2:");
        System.out.println(tree2);
    }

}
