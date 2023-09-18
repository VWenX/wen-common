package io.github.vwenx.common.fieldfill.stuffer;

import io.github.vwenx.common.fieldfill.bean.FillCTX;

import java.util.List;

/**
 * 字段填充器的接口定义
 *     处理工具将会根据在 @FieldFill 注解中声明的 type ，来寻找在 FieldFillStufferRegistry 中注册的填充器实现。
 *     fill方法将接收到一批此type下的待填充任务上下文。
 *
 * @author XuWen
 */
public interface FieldFillStuffer {

    void fill(List<FillCTX> ctxList);

}
