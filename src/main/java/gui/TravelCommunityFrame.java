/*
 * Created by JFormDesigner on Wed Dec 25 14:23:00 CST 2019
 */

package gui;

import java.awt.event.*;
import java.util.*;
import javax.swing.table.*;

import arithmetic.GraphAdjMatrix;
import entity.Scenic;
import service.ScenicService;
import service.impl.ScenicServiceImpl;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author a
 */
public class TravelCommunityFrame extends JFrame {

    public static void main(String[] args) {
        new TravelCommunityFrame().setVisible(true);
    }

    DefaultTableModel defaultTableModel;

    DefaultListModel<String> defaultListModel;

    /**
     * 景点邻近矩阵
     */
    private GraphAdjMatrix<Scenic> graphAdjMatrix = new GraphAdjMatrix<>(100, Scenic.class);

    /**
     * 邻进矩阵 此情景下这些元素为价格
     */
    private Object[][] edges;

    /**
     * 表头 景点列表
     */
    private String[] columns;

    /**
     * 纵列 景点列表
     */
    String[] rowBeforeValues;

    /**
     * columnTypes 其实全是string
     */
    Class<?>[] columnTypes;

    /**
     * 景点服务
     */
    private ScenicService scenicService = new ScenicServiceImpl();

    /**
     * 景点列表
     */
    List<Scenic> scenicList = scenicService.getAll();

    public TravelCommunityFrame() {
        initComponents();
    }

    private void flushActionPerformed(ActionEvent e) {
        //获取名称
        String scenicText = myPlaceScenic.getText();
        Scenic scenic = scenicService.fingByName(scenicText);
        if (scenic == null) {
            JOptionPane.showMessageDialog(null, "未找到输入景点:" + scenicText + "请正确输入");
            return;
        }
        //查找此景点的所在点
        int indexOfVex = this.graphAdjMatrix.indexOfVex(scenic);

        if (scenicService.getAll().size() < 2) {
            //重新计算最省钱路线
            int[] result = this.graphAdjMatrix.dijkstra(indexOfVex);
            StringBuilder sb = new StringBuilder();
            for (int i : result) {
                sb.append(this.graphAdjMatrix.valueOfVex(i).getName());
                sb.append("=>");
            }

            zuiyouluxian.setText(sb.toString());
        } else {
            JOptionPane.showMessageDialog(null, "景点少于2个，无法规划！");
            return;
        }

        flushAll();
    }

    private void flushAll() {
        flushProp();
        //刷新ui
        defaultTableModel.fireTableDataChanged();
        matrixTable.updateUI();
    }

    private void addScenicActionPerformed(ActionEvent e) {
        int x = 0;
        int y = 0;
        try {
            x = Integer.parseInt(selectXText.getText());
            y = Integer.parseInt(selectYText.getText());
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, "坐标有误，请重新输入");
        }
        if (x == 0) {
            JOptionPane.showMessageDialog(null, "坐标不能为0");
            return;
        }

        //查找是否已存在的坐标
        if (x <= scenicList.size()) {
            JOptionPane.showMessageDialog(null, "该坐标景点已存在，添加失败，请输入景点总数+1的坐标");
            return;
        }

        String scenicName = scenicNameText.getText();
        //添加景点
        int money;
        try {
            money = Integer.parseInt(selectMoneyText.getText());
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(null, "价格输入有误！:" + selectMoneyText.getText());
            return;
        }

        defaultListModel.addElement(scenicName);
        defaultTableModel.addColumn(scenicName, new String[]{selectMoneyText.getText()});

        Scenic newScenic = new Scenic(scenicName, money);
        scenicService.add(newScenic);
        //插入顶点
        this.graphAdjMatrix.insertVex(newScenic);

        //至少两个景点时才添加边
        if (scenicList.size() > 1) {
            //插入边
            if (!this.insertEdges(x, y, money)) {
                JOptionPane.showMessageDialog(null, "添加景点失败，景点:" + scenicName + "不合法");
                return;
            }
        }

        flushAll();
    }

    private boolean insertEdges(int x, int y, int money) {
        return this.graphAdjMatrix.insertEdge(x, y, money);
    }

    private void deleteScenicActionPerformed(ActionEvent e) {
        String scenicName = scenicNameText.getText();
        int x = 0;
        int y = 0;
        try {
            x = Integer.parseInt(selectXText.getText());
            y = Integer.parseInt(selectYText.getText());
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, "坐标有误，请重新输入");
        }

        if (x == 0) {
            JOptionPane.showMessageDialog(null, "坐标不能为0");
        }

        //删除选中的景点
        boolean b = this.graphAdjMatrix.deleteEdge(x, y);

        //查询景点
        Scenic scenic = this.scenicService.fingByName(scenicName);
        //删除景点
        this.scenicService.removeByName(scenicName);
        //在数据结构中删除映射
        boolean b1 = this.graphAdjMatrix.deleteVex(scenic);

        //未删除成功
        if (!(b && b1)) {
            JOptionPane.showMessageDialog(null, "删除失败,景点:" + scenicName + "或坐标xy:" + x + "-" + y + "不存在");
        }

        flushAll();
    }

    private void initComponents() {

        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        ResourceBundle bundle = ResourceBundle.getBundle("mainJfram");
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        scenicTable = new JScrollPane();
        matrixTable = new JTable();
        scrollPane2 = new JScrollPane();
        scenicStrList = new JList<>();
        label3 = new JLabel();
        flush = new JButton();
        addScenic = new JButton();
        deleteScenic = new JButton();
        label1 = new JLabel();
        myPlaceScenic = new JTextField();
        label4 = new JLabel();
        label5 = new JLabel();
        scenicNameText = new JTextField();
        label7 = new JLabel();
        label8 = new JLabel();
        label9 = new JLabel();
        label10 = new JLabel();
        selectXText = new JTextField();
        selectMoneyText = new JTextField();
        label11 = new JLabel();
        label12 = new JLabel();
        label14 = new JLabel();
        label13 = new JLabel();
        selectYText = new JTextField();
        label15 = new JLabel();
        targetPlaceScenic = new JTextField();
        label16 = new JLabel();
        zuiyouluxian = new JLabel();

        //======== this ========
        Container contentPane = getContentPane();

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));

            //======== contentPanel ========
            {
                //======== scenicTable ========
                {

                    //---- matrixTable ----
                    defaultTableModel = new DefaultTableModel();
                    matrixTable.setModel(defaultTableModel);
                    scenicTable.setViewportView(matrixTable);
                }

                //======== scrollPane2 ========
                {

                    //---- scenicStrList ----
                    defaultListModel = new DefaultListModel<>();
                    scenicStrList.setModel(defaultListModel);
                    scrollPane2.setViewportView(scenicStrList);
                }

                //---- label3 ----
                label3.setText(bundle.getString("TravelCommunityFrame.label3.text"));

                GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
                contentPanel.setLayout(contentPanelLayout);
                contentPanelLayout.setHorizontalGroup(
                        contentPanelLayout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, contentPanelLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(contentPanelLayout.createParallelGroup()
                                                .addComponent(label3, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(scrollPane2))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(scenicTable, GroupLayout.PREFERRED_SIZE, 516, GroupLayout.PREFERRED_SIZE))
                );
                contentPanelLayout.setVerticalGroup(
                        contentPanelLayout.createParallelGroup()
                                .addGroup(contentPanelLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addComponent(scenicTable, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                                                .addGroup(contentPanelLayout.createSequentialGroup()
                                                        .addComponent(label3)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 356, GroupLayout.PREFERRED_SIZE)))
                                        .addGap(19, 19, 19))
                );
            }

            GroupLayout dialogPaneLayout = new GroupLayout(dialogPane);
            dialogPane.setLayout(dialogPaneLayout);
            dialogPaneLayout.setHorizontalGroup(
                    dialogPaneLayout.createParallelGroup()
                            .addGroup(dialogPaneLayout.createSequentialGroup()
                                    .addComponent(contentPanel, GroupLayout.PREFERRED_SIZE, 563, GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 22, Short.MAX_VALUE))
            );
            dialogPaneLayout.setVerticalGroup(
                    dialogPaneLayout.createParallelGroup()
                            .addComponent(contentPanel, GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
            );
        }

        //---- flush ----
        flush.setText(bundle.getString("TravelCommunityFrame.flush.text"));
        flush.addActionListener(e -> flushActionPerformed(e));

        //---- addScenic ----
        addScenic.setText(bundle.getString("TravelCommunityFrame.addScenic.text"));
        addScenic.addActionListener(e -> addScenicActionPerformed(e));

        //---- deleteScenic ----
        deleteScenic.setText(bundle.getString("TravelCommunityFrame.deleteScenic.text"));
        deleteScenic.addActionListener(e -> deleteScenicActionPerformed(e));

        //---- label1 ----
        label1.setText(bundle.getString("TravelCommunityFrame.label1.text"));

        //---- label4 ----
        label4.setText(bundle.getString("TravelCommunityFrame.label4.text"));

        //---- label5 ----
        label5.setText(bundle.getString("TravelCommunityFrame.label5.text"));

        //---- label8 ----
        label8.setText(bundle.getString("TravelCommunityFrame.label8.text"));

        //---- label9 ----
        label9.setText(bundle.getString("TravelCommunityFrame.label9.text"));

        //---- label10 ----
        label10.setText(bundle.getString("TravelCommunityFrame.label10.text"));

        //---- label11 ----
        label11.setText(bundle.getString("TravelCommunityFrame.label11.text"));

        //---- label12 ----
        label12.setText(bundle.getString("TravelCommunityFrame.label12.text"));

        //---- label14 ----
        label14.setText(bundle.getString("TravelCommunityFrame.label14.text"));

        //---- label13 ----
        label13.setText(bundle.getString("TravelCommunityFrame.label13.text"));

        //---- label15 ----
        label15.setText(bundle.getString("TravelCommunityFrame.label15.text"));

        //---- label16 ----
        label16.setText(bundle.getString("TravelCommunityFrame.label16.text"));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addComponent(dialogPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 64, Short.MAX_VALUE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGap(61, 61, 61)
                                                .addComponent(flush)
                                                .addGap(60, 60, 60)
                                                .addComponent(addScenic)
                                                .addGap(36, 36, 36)
                                                .addComponent(deleteScenic))
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGap(19, 19, 19)
                                                .addComponent(label5)
                                                .addGap(6, 6, 6)
                                                .addGroup(contentPaneLayout.createParallelGroup()
                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(label10, GroupLayout.Alignment.LEADING)
                                                                        .addGroup(contentPaneLayout.createParallelGroup()
                                                                                .addComponent(label13)
                                                                                .addComponent(label8)))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(contentPaneLayout.createParallelGroup()
                                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                                .addComponent(scenicNameText, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(label11)
                                                                                .addGap(114, 114, 114)
                                                                                .addComponent(label7))
                                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                                        .addComponent(selectXText, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                                                                        .addComponent(selectYText, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
                                                                                .addGap(18, 18, 18)
                                                                                .addGroup(contentPaneLayout.createParallelGroup()
                                                                                        .addComponent(label12)
                                                                                        .addComponent(label15)))))
                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                .addComponent(label9)
                                                                .addGap(14, 14, 14)
                                                                .addComponent(selectMoneyText, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(label14))))
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGroup(contentPaneLayout.createParallelGroup()
                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                .addGap(19, 19, 19)
                                                                .addComponent(label1)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED))
                                                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(label4)
                                                                .addGap(19, 19, 19)))
                                                .addGroup(contentPaneLayout.createParallelGroup()
                                                        .addComponent(zuiyouluxian)
                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                .addComponent(myPlaceScenic, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(label16)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(targetPlaceScenic, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)))))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addComponent(dialogPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGap(29, 29, 29)
                                                .addComponent(label7))
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(label5)
                                                        .addComponent(label11)
                                                        .addComponent(label10)
                                                        .addComponent(scenicNameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label8)
                                        .addComponent(label12)
                                        .addComponent(selectXText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(label13)
                                                .addComponent(selectYText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(label15, GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addComponent(label9)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(label4)
                                                        .addComponent(myPlaceScenic, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(label16)
                                                        .addComponent(targetPlaceScenic, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(label1)
                                                .addGap(7, 7, 7))
                                        .addGroup(GroupLayout.Alignment.LEADING, contentPaneLayout.createSequentialGroup()
                                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(label14)
                                                        .addComponent(selectMoneyText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)))
                                .addComponent(zuiyouluxian)
                                .addGap(29, 29, 29)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(flush)
                                        .addComponent(addScenic)
                                        .addComponent(deleteScenic))
                                .addGap(55, 55, 55))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    private void flushProp() {
        //initprop
        //矩阵赋值
        int[][] matrixEdges = graphAdjMatrix.getEdges();
        edges = new Object[matrixEdges.length][matrixEdges.length];
        for (int i = 0; i < matrixEdges.length; i++) {
            for (int j = 0; j < matrixEdges[i].length; j++) {
                edges[i][j] = matrixEdges[i][j];
            }
        }

        scenicList = scenicService.getAll();
        //列名以及列类型赋值
        columns = new String[scenicList.size()];
        scenicList.stream().map(Scenic::getName).collect(Collectors.toList()).toArray(columns);

        columnTypes = new Class<?>[scenicList.size()];
        scenicList.stream().map(x -> x.getName().getClass()).collect(Collectors.toList()).toArray(columnTypes);

        //纵列行名赋值
        rowBeforeValues = new String[scenicList.size()];
        scenicList.stream().map(Scenic::getName).collect(Collectors.toList()).toArray(rowBeforeValues);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JScrollPane scenicTable;
    private JTable matrixTable;
    private JScrollPane scrollPane2;
    private JList<String> scenicStrList;
    private JLabel label3;
    private JButton flush;
    private JButton addScenic;
    private JButton deleteScenic;
    private JLabel label1;
    private JTextField myPlaceScenic;
    private JLabel label4;
    private JLabel label5;
    private JTextField scenicNameText;
    private JLabel label7;
    private JLabel label8;
    private JLabel label9;
    private JLabel label10;
    private JTextField selectXText;
    private JTextField selectMoneyText;
    private JLabel label11;
    private JLabel label12;
    private JLabel label14;
    private JLabel label13;
    private JTextField selectYText;
    private JLabel label15;
    private JTextField targetPlaceScenic;
    private JLabel label16;
    private JLabel zuiyouluxian;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

}
