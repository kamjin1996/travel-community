package entity;

import java.util.List;

/**
 * 景点
 *
 * @author kamjin1996
 */
public class Scenic {
    public Scenic() {
    }

    public Scenic(String name, Integer money) {
        this.name = name;
        this.money = money;
    }

    private String name;

    private Integer money;

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
