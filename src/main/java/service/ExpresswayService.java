package service;

import entity.Expressway;

import java.util.List;

/**
 * 公路服务
 *
 * @author kamjin1996
 */
public interface ExpresswayService {
    /**
     * 查询所有高速公路
     *
     * @return
     */
    List<Expressway> findAll();

}
