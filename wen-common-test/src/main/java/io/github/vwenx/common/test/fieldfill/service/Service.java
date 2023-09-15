package io.github.vwenx.common.test.fieldfill.service;

import java.util.Collection;
import java.util.Map;

public interface Service {

    Map<Long, String> getNameByIds(Collection<Long> ids);

}
