package club.mrdaisite.torder.torderadmin.util;

import org.springframework.beans.BeanUtils;

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
     *
     * @param sourceList 被复制的列表
     * @param targetList 最终列表
     * @return 最终的列表
     */
    public List<Object> beanUtilsCopyListProperties(List<Object> sourceList, List<Object> targetList) {
        for (int i = 0; i < targetList.size(); i++) {
            BeanUtils.copyProperties(sourceList.get(i), targetList.get(i));
        }
        return targetList;
    }
}
