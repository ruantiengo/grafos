import java.util.Scanner;
import java.io.IOException;

public class Main {
    
    public static void options(){
        System.out.println("Informe a tarefa: ");
        System.out.println("   1 - Caminho Mínimo: ");
        System.out.println("   2-  Sair: ");
    }
    public static int setPosition(String variableName,Scanner s){
        System.out.printf("%s : ", variableName);
        int var = s.nextInt();
        return var;
    }




    private static void getTime(long startTime){
        float totalTime = System.currentTimeMillis() - startTime;
        System.out.println("Tempo: " + totalTime / 1000 + " segundo(s).");
    }

    public static void getPath(String algorithName, GraphList graphList, int s, int d, GraphMatrix graphMatrix){
        long startTime = System.currentTimeMillis();
        System.out.println("\n" + algorithName);
        switch(algorithName){
            case "Dijkstra":
                graphList.dijkstra(s, d);
                getTime(startTime);
                break;
            case "Bellman Ford":
                graphList.bellmanFord(s, d);
                getTime(startTime);
                break;
            case "Bellman Ford Improved":
                graphList.improvedBellManFord(s, d);
                getTime(startTime);
                break;
            case "Floyd Warshall":
                graphMatrix.floydWarshall(s,d);
                getTime(startTime);
                break;
        }
        
    }
}