package com.joebase.datastructs;

public class GraphBFS {

    private static boolean[] visited;// 访问标志数组

    // 对图G做广度优先遍历
    public static void BFSTraverse(Graph G) throws Exception {
        visited = new boolean[G.getVexNum()];// 访问标志数组
        for(int v = 0; v < G.getVexNum(); v++)
            // 访问标志数组初始化
            visited[v] = false;
        for(int v = 0; v < G.getVexNum(); v++)
            if(!visited[v]) // v尚未访问
                BFS(G, v);
    }

    private static void BFS(Graph G, int v) throws Exception {
        visited[v] = true;
        System.out.print(G.getVex(v).toString() + " ");
        LinkedQueue<Integer> Q = new LinkedQueue<Integer>();// 辅助队列Q
        Q.offer(v);// v入队列
        while(!Q.isEmpty()) {
            int u = Q.poll();// 队头元素出队列并赋值给u
            for(int w = G.firstAdjVex(u); w >= 0; w = G.nextAdjVex(u, w))
                if(!visited[w]) {// w为u的尚未访问的邻接顶点
                    visited[w] = true;
                    System.out.print(G.getVex(w).toString() + " ");
                    Q.offer(w);
                }
        }
    }

    public static void main(java.lang.String[] args) throws Exception {
        LinkedGraph G = GenerateGraph.generateLinkedGraph();
        GraphBFS.BFSTraverse(G);
    }
}
