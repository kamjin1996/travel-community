package service.impl;

import entity.Scenic;
import service.ScenicService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 景点服务实现
 *
 * @author kamjin1996
 */
public class ScenicServiceImpl implements ScenicService {
    /**
     * 存储所有景点
     */
    private static Map<String, Scenic> scenicMap = new HashMap<>();

    @Override
    public List<Scenic> getAll() {
        return new ArrayList<>(scenicMap.values());
    }

    @Override
    public void add(Scenic scenic) {
        scenicMap.put(scenic.getName(), scenic);
    }

    @Override
    public Scenic removeByName(String name) {
        return scenicMap.remove(name);
    }

    @Override
    public Scenic fingByName(String scenicName) {
        return scenicMap.get(scenicName);
    }

}
