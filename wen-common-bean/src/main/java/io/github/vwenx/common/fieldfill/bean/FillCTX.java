package io.github.vwenx.common.fieldfill.bean;

/**
 * 填充任务的上下文
 *     此Class的实例对象即代表一个具体待填充的任务
 *
 * @author XuWen
 */
public class FillCTX {

    /**
     * 元信息
     */
    public final FillFieldMeta meta;
    /**
     * 所在对象
     */
    public final Object locationObject;
    /**
     * 来源的值
     */
    public final Object sourceValue;



    public FillCTX(FillFieldMeta meta, Object locationObject, Object sourceValue) {
        this.meta = meta;
        this.locationObject = locationObject;
        this.sourceValue = sourceValue;
    }

    public void setTargetVal(Object targetVal){
        meta.targetSetter.accept(locationObject, targetVal);
    }

    @Override
    public String toString() {
        return "FillCTX{" +
                "meta=" + meta +
                ", locationObject=" + locationObject +
                ", sourceValue=" + sourceValue +
                '}';
    }
}
