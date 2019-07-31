package com.joebase.datastructs;

public class GenerateGraph {

    public final static int INFINITY = Integer.MAX_VALUE;

    public static MatrixGraph generateMatrixGraph() {
        Object vexs[] = {"v0", "v1", "v2", "v3", "v4", "v5"};
        int[][] arcs = {
                {0, 7, 1, 5, INFINITY, INFINITY}, {7, 0, 6, INFINITY, 3, INFINITY}, {1, 6, 0, 7, 6, 4},
                {5, INFINITY, 7, 0, INFINITY, 2}, {INFINITY, 3, 6, INFINITY, 0, 7}, {INFINITY, INFINITY, 4, 2, 7, 0}};
        MatrixGraph G = new MatrixGraph(GraphKind.UDG, 6, 10, vexs, arcs);
        return G;
    }

    public static LinkedGraph generateLinkedGraph() {
        ArcNode v12 = new ArcNode(1, 6);
        ArcNode v13 = new ArcNode(2, 4, v12);
        ArcNode v14 = new ArcNode(3, 5, v13);
        VexNode v1 = new VexNode("v1", v14);

        ArcNode v25 = new ArcNode(4, 1);
        VexNode v2 = new VexNode("v2", v25);

        ArcNode v35 = new ArcNode(4, 1);
        VexNode v3 = new VexNode("v3", v35);

        ArcNode v46 = new ArcNode(5, 2);
        VexNode v4 = new VexNode("v4", v46);

        ArcNode v57 = new ArcNode(6, 9);
        ArcNode v58 = new ArcNode(7, 7, v57);
        VexNode v5 = new VexNode("v5", v58);

        ArcNode v68 = new ArcNode(7, 4);
        VexNode v6 = new VexNode("v6", v68);

        ArcNode v79 = new ArcNode(8, 2);
        VexNode v7 = new VexNode("v7", v79);

        ArcNode v89 = new ArcNode(8, 4);
        VexNode v8 = new VexNode("v8", v89);

        // ArcNode v91 = new ArcNode(0, 6);
        VexNode v9 = new VexNode("v9");

        VexNode[] vexs = {v1, v2, v3, v4, v5, v6, v7, v8, v9};
        LinkedGraph G = new LinkedGraph(GraphKind.DG, 9, 11, vexs);
        return G;
    }
}
