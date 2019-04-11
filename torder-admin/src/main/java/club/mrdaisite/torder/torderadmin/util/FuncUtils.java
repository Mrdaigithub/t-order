package club.mrdaisite.torder.torderadmin.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * FuncUtils
 *
 * @author dai
 * @date 2019/04/11
 */
public class FuncUtils {
    /**
     * 复制列表属性
     * @param sources 被复制的列表
     * @param target  最终列表单个项目的模型
     * @return 最终的列表
     */
    public List<Object> beanUtilsCopyListProperties(List<Object> sources, Object target) {
        List<Object> targetList = new ArrayList<>();
        for (int i = 0; i < sources.size(); i++) {
            targetList.add(target);
            BeanUtils.copyProperties(sources.get(i), targetList.get(i));
        }
        return targetList;
    }
}
