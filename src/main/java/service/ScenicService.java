package service;

import entity.Scenic;

import java.util.List;

/**
 * 景点服务
 *
 * @author kamjin1996
 */
public interface ScenicService {

    List<Scenic> getAll();

    /**
     * 添加景点
     *
     * @param scenic
     */
    void add(Scenic scenic);


    /**
     * 根据名称删除
     *
     * @param name
     */
    Scenic removeByName(String name);

    /**
     * 根据名称查询
     *
     * @param scenicName
     * @return
     */
    Scenic fingByName(String scenicName);
}
