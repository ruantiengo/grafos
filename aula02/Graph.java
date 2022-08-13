package src;
public class Graph {

    private int countNodes;
    private int countEdges;
    private int[][] adjMatrix;

    public Graph(int countNodes){
        this.countNodes = countNodes;
        this.adjMatrix = new int[countNodes][countNodes];
    }  

    public int getCountNodes() {
        return countNodes;
    }

    public void setCountNodes(int countNodes) {
        this.countNodes = countNodes;
    }

    public int getCountEdges() {
        return countEdges;
    }

    public void setCountEdges(int countEdges) {
        this.countEdges = countEdges;
    }

    public int[][] getAdjMatrix() {
        return adjMatrix;
    }

    /**
     * @param adjMatrix the adjMatrix to set
     */
    public void setAdjMatrix(int[][] adjMatrix) {
        this.adjMatrix = adjMatrix;
    }

    public String toString(){
        String str = "";
        for(int i = 0; i<this.adjMatrix.length; ++i){
            for (int j = 0; j < adjMatrix.length; ++j) {
                str += this.adjMatrix[i][j] + "\t";
            }
            str += "\n";
        }
        return str;
    }
    public void addEdge(int source, int sink, int weight) {
        if(source < 0 || source > this.countNodes - 1 
        || sink < 0 || sink > this.countNodes - 1 
        || weight <= 0 ){
        System.err.println("invalid edge: " + source + " " + sink + " " + weight);
            return;
        }
        this.adjMatrix[source][sink] = weight;
        this.countEdges++;

    }

    public int degree(int node){
        if(node < 0 || node > this.countNodes - 1){
            System.err.println("invalid node: " + node);
        }
        int count = 0;
        for (int i = 0; i < adjMatrix[node].length; i++) {
            if(adjMatrix[node][i] != 0){
                count++;
            }
        }
        return count;
    }

    public int highestDegree(){
        int hd = 0;
        for (int i = 0; i < adjMatrix.length; i++) {
            int aux = this.degree(i);
            if(aux > hd){
                hd = aux;
            }
        } 
        return hd;
    }

    public int lowestDegree(){
        int hd = this.countNodes;
        for (int i = 0; i < adjMatrix.length; i++) {
            int aux = this.degree(i);
            if(aux < hd){
                hd = aux;
            }
        } 
        return hd;
    }

    public Graph complement(){
        Graph g2 = new Graph(4);
        
        for (int i = 0; i < adjMatrix.length; i++) {
           
            for(int j = 0; j < adjMatrix[i].length; j++){
                if(this.adjMatrix[i][j] == 0 && i != j){
                    g2.addEdge(i, j, 1);
                }
            }
        }
        return g2;
    }
    
    public boolean subgraph(Graph g2){
        if(g2.countEdges > this.countEdges || g2.countNodes > this.countNodes){
            return false;
        }
        for(int i = 0; i < g2.adjMatrix.length; i++){
          for(int j = 0; j < g2.adjMatrix[i].length; j++){
            if(g2.adjMatrix[i][j] != 1 && this.adjMatrix[i][j] == 0 ){
                return false;
            }
                                                                                                                                                                                                                                      
          }
        }
        return true;
    }
}

