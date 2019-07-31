package com.joebase.datastructs;

public interface Graph {

    public void createGraph(); // 创建一个图

    public int getVexNum(); // 返回顶点数

    public int getArcNum(); // 返回边数

    public Object getVex(int v) throws Exception; // 返回v表示结点的值， 0 <= v < vexNum

    public int locateVex(Object vex); // 给定顶点的值vex，返回其在图中的位置，如果图中不包含此顶点，则返回-1

    public int firstAdjVex(int v) throws Exception; // 返回v的第一个邻接点，若v没有邻接点，则返回-1，其中0≤v<vexNum

    public int nextAdjVex(int v, int w) throws Exception; // 返回v相对于w的下一个邻接点，若w是v的最后一个邻接点，则返回-1，其中0≤v, w<vexNum

}