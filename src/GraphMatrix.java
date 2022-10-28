import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GraphMatrix {

    private int countNodes;
    private int countEdges;
    private int[][] adjMatrix;
    private static final int INF = 999999;
    
    public GraphMatrix(int countNodes) {
        this.countNodes = countNodes;
        this.adjMatrix = new int[countNodes][countNodes];
    }

    public GraphMatrix(String fileName) throws IOException {
        File file = new File(fileName);
        FileReader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);

        // Read header
        String[] line = bufferedReader.readLine().split(" ");
        this.countNodes = (Integer.parseInt(line[0]));
        int fileLines = (Integer.parseInt(line[1]));

        // Create and fill adjMatrix with read edges
        this.adjMatrix = new int[this.countNodes][this.countNodes];
        for (int i = 0; i < fileLines; ++i) {
            String[] edgeInfo = bufferedReader.readLine().split(" ");
            int source = Integer.parseInt(edgeInfo[0]);
            int sink = Integer.parseInt(edgeInfo[1]);
            int weight = Integer.parseInt(edgeInfo[2]);
            addEdge(source, sink, weight);
        }
        bufferedReader.close();
        reader.close();
        
    }

    public int getCountNodes() {
        return this.countNodes;
    }

    public int getCountEdges() {
        return this.countEdges;
    }

    public int[][] getAdjMatrix() {
        return this.adjMatrix;
    }

    public String toString() {
        String str = "";
        for (int u = 0; u < this.adjMatrix.length; ++u) {
            for (int v = 0; v < this.adjMatrix[u].length; ++v) {
                str += this.adjMatrix[u][v] + "\t";
            }
            str += "\n";
        }
        return str;
    }

    public void addEdge(int source, int sink, int weight) {
        if (source < 0 || source > this.countNodes - 1
                || sink < 0 || sink > this.countNodes - 1 || weight <= 0) {
            System.err.println("Invalid edge: " + source + sink + weight);
            return;
        }
        this.adjMatrix[source][sink] = weight;
        this.countEdges++;
    }

    public void addEdgeUnoriented(int source, int sink, int weight) {
        addEdge(source, sink, weight);
        addEdge(sink, source, weight);
    }

    public int degree(int u) {
        if (u < 0 || u > this.countNodes - 1)
            System.err.println("Invalid node: " + u);
        int degree = 0;
        for (int v = 0; v < this.adjMatrix[u].length; ++v) {
            if (this.adjMatrix[u][v] != 0)
                ++degree;
        }
        return degree;
    }

    public int highestDegree() {
        int highest = 0;
        for (int u = 0; u < this.adjMatrix.length; ++u) {
            int degreeNodeU = this.degree(u);
            if (highest < degreeNodeU)
                highest = degreeNodeU;
        }
        return highest;
    }

    public int lowestDegree() {
        int lowest = this.countNodes;
        for (int u = 0; u < this.adjMatrix.length; ++u) {
            int degreeNodeU = this.degree(u);
            if (lowest > degreeNodeU)
                lowest = degreeNodeU;
        }
        return lowest;
    }

    public GraphMatrix complement() {
        GraphMatrix g2 = new GraphMatrix(this.countNodes);
        for (int u = 0; u < this.adjMatrix.length; ++u) {
            for (int v = 0; v < this.adjMatrix[u].length; ++v) {
                if (u != v && this.adjMatrix[u][v] == 0) {
                    g2.addEdge(u, v, 1);
                }
            }
        }
        return g2;
    }

    public float density() {
        return (float) this.countEdges / (this.countNodes * (this.countNodes - 1));
    }

    public boolean subGraph(GraphMatrix g2) {
        if (g2.countNodes > this.countNodes || g2.countEdges > this.countEdges)
            return false;
        for (int u = 0; u < g2.adjMatrix.length; ++u) {
            for (int v = 0; v < g2.adjMatrix[u].length; ++v) {
                if (g2.adjMatrix[u][v] != 0 && this.adjMatrix[u][v] == 0)
                    return false;
            }
        }
        return true;
    }

    public ArrayList<Integer> bfs(int s) { // breadth-first search
        // Initialization
        int[] desc = new int[this.countNodes];
        ArrayList<Integer> Q = new ArrayList<>();
        Q.add(s);
        ArrayList<Integer> R = new ArrayList<>();
        R.add(s);
        desc[s] = 1;
        // Main loop
        while (Q.size() > 0) {
            int u = Q.remove(0);
            for (int v = 0; v < this.adjMatrix[u].length; ++v) {
                if (this.adjMatrix[u][v] != 0) { // Edge (u, v) exists
                    if (desc[v] == 0) {
                        Q.add(v);
                        R.add(v);
                        desc[v] = 1;
                    }
                }
            }
        }
        return R;
    }

    public ArrayList<Integer> dfs(int s) { // Depth-first search
        // Initialization
        int[] desc = new int[this.countNodes];
        ArrayList<Integer> S = new ArrayList<>();
        S.add(s);
        ArrayList<Integer> R = new ArrayList<>();
        R.add(s);
        desc[s] = 1;
        // Main loop
        while (S.size() > 0) {
            int u = S.get(S.size() - 1);
            boolean unstack = true;
            for (int v = 0; v < this.adjMatrix[u].length; ++v) {
                if (this.adjMatrix[u][v] != 0 && desc[v] == 0) {
                    S.add(v);
                    R.add(v);
                    desc[v] = 1;
                    unstack = false;
                    break;
                }
            }
            if (unstack) {
                S.remove(S.size() - 1);
            }
        }
        return R;
    }

    public boolean connected() {
        return this.bfs(0).size() == this.countNodes;
    }

    public boolean isOriented() {
        for (int u = 0; u < this.adjMatrix.length; ++u) {
            for (int v = u + 1; v < this.adjMatrix.length; ++v) {
                if (this.adjMatrix[u][v] != this.adjMatrix[v][u])
                    return true;
            }
        }
        return false;
    }

    public ArrayList<Integer> dfsRec(int s) {
        int[] desc = new int[this.countNodes];
        ArrayList<Integer> R = new ArrayList<>();
        dfsRecAux(s, desc, R);
        return R;
    }

    public void dfsRecAux(int u, int[] desc, ArrayList<Integer> R) {
        desc[u] = 1;
        R.add(u);
        for (int v = 0; v < this.adjMatrix[u].length; ++v) {
            if (this.adjMatrix[u][v] != 0 && desc[v] == 0) {
                dfsRecAux(v, desc, R);
            }
        }
    }

    public void floydWarshall(int s, int t) {
        int[][] dist = new int[this.countNodes][this.countNodes];
        int[][] pred = new int[this.countNodes][this.countNodes];
        for (int i = 0; i < this.adjMatrix.length; ++i) {
            for (int j = 0; j < this.adjMatrix[i].length; ++j) {
                if (i == j) {
                    dist[i][j] = 0;
                    pred[i][j] = -1;
                } else if (this.adjMatrix[i][j] != 0) { // Edge (i, j) exists
                    dist[i][j] = this.adjMatrix[i][j];
                    pred[i][j] = i;
                } else {
                    dist[i][j] = INF;
                    pred[i][j] = -1;
                }
            }
        }
        for (int k = 0; k < this.countNodes; ++k) {
            for (int i = 0; i < this.countNodes; ++i) {
                for (int j = 0; j < this.countNodes; ++j) {
                    if (dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        pred[i][j] = pred[k][j];
                    }
                }
            }
        }
        // Recovering paths
        ArrayList<Integer> C = new ArrayList<Integer>();
        C.add(t);
        int aux = t;
        while (aux != s) {
            aux = pred[s][aux];
            C.add(0, aux);
        }
        System.out.println("Caminho: " + C);
        System.out.println("Custo: " +  dist[s][t]);

    }
}