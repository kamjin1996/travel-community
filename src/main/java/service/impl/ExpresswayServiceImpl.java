package service.impl;

import entity.Expressway;
import service.ExpresswayService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 高速公路服务类
 *
 * @author kamjin1996
 */
public class ExpresswayServiceImpl implements ExpresswayService {

    /**
     * 高速公路池
     */
    private static Map<Integer, Expressway> expresswayMap = new HashMap<>();

    @Override
    public List<Expressway> findAll() {
        return (List<Expressway>) expresswayMap.values();
    }
}
