import java.io.IOException;
import java.util.Scanner;

class App {
    public static void main(String[] args) throws IOException {
        String fileName;
        Scanner scanner = new Scanner(System.in);
        Boolean isRunning = true;
        int option = 0;
        while (isRunning) {
            Main.options();
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    try{
                    System.out.println("Arquivo:");
                    fileName = scanner.next();
                    int s = Main.setPosition("Origem", scanner);
                    int d = Main.setPosition("Destino", scanner); 

                    // para abrir no vscode o inicio do caminho é ../files/cm/
                    // para abrir no intellij é /files/cm/
                    // é so digitar o nome de qualquer arquivo na pasta cm
                    GraphList graphList = new GraphList("../files/cm/" + fileName);
                    GraphMatrix graphMatrix = new GraphMatrix("../files/cm/" + fileName);
                    
                    
                    Main.getPath("Dijkstra", graphList, s, d, graphMatrix);
                    Main.getPath("Bellman Ford", graphList, s ,d, graphMatrix);
                    Main.getPath("Bellman Ford Improved", graphList, s , d, graphMatrix);
                    Main.getPath("Floyd Warshall", graphList, s, d,  graphMatrix);

                    } catch(Exception e){
                        System.out.println("Erro. O arquivo não existe ou é invalido.");
                    }
                    break;

                case 2:
                    isRunning = false;
                    break;

                default:
                    System.out.println("Opção invalida.");
            }

        }

        scanner.close();
    }
}