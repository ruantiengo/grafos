package src;

public class Main {
    public static void main(String[] args) {
        Graph g1 = new Graph(4);
        g1.addEdge(0, 1, 1);
        g1.addEdge(1, 0, 1);
        g1.addEdge(0, 3, 1);
        g1.addEdge(3, 0, 1);

        Graph g2 = new Graph(4);
        g2.addEdge(0, 2, 1);
        g2.addEdge(1, 2, 1);
        g2.addEdge(2, 1, 1);
        g2.addEdge(2, 3, 1);
        g2.addEdge(3, 1, 1);
        g2.addEdge(3, 2, 1);

        



        System.out.println(g1.toString());

        System.out.println("o grau é: " + g1.degree(0));
      
        System.out.println("o maior grau é: " + g1.highestDegree());

        Graph g3 = g1.complement();
        System.out.println(g3.toString());


        System.out.println(g1.subgraph(g3));
    }
}