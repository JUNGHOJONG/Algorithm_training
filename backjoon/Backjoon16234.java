package algorithm.backjoon;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Backjoon16234 {
    public static class Country{
        private int positionX;
        private int positionY;
        private int population;
        private int identity = 0;
        public Country( int positionX, int positionY, int population ){
            this.positionX = positionX;
            this.positionY = positionY;
            this.population = population;
        }
    }
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( System.out ));
    private static int totalCountrySize;
    private static int minPopulation;
    private static int maxPopulation;
    private static Country[][] country;
    private static boolean[][] visited;
    private static int[] directionX = { 1, 0, -1, 0 };
    private static int[] directionY = { 0, 1, 0, -1 };
    private static Queue<Country> queue = new LinkedList<>();
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        totalCountrySize = Integer.parseInt(st.nextToken());
        minPopulation = Integer.parseInt(st.nextToken());
        maxPopulation = Integer.parseInt(st.nextToken());
        initCountry();
        int movementCount = 0;
        while( true ){
            if ( !isPosibleOpenGate() ) break;
            openGate();
            resetCountry();
            movementCount++;
        }
        System.out.println(movementCount);
    }

    public static void initCountry() throws IOException{
        country = new Country[totalCountrySize][totalCountrySize];
        visited = new boolean[totalCountrySize][totalCountrySize];
        for( int i=0; i<totalCountrySize; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for( int j=0; j<totalCountrySize; j++ ){
                int tempPopulation = Integer.parseInt(st.nextToken());
                country[i][j] = new Country( j, i, tempPopulation );
            }
        }
    }

    public static boolean isPosibleOpenGate(){
        for( int i=0; i<totalCountrySize; i++ ){
            for( int j=0; j<totalCountrySize; j++ ){
                for( int k=0; k<4; k++ ){
                    int nextX = j + directionX[k];
                    int nextY = i + directionY[k];
                    if( !movable( nextX, nextY ) ) continue;
                    int gap = Math.abs(country[i][j].population - country[nextY][nextX].population);
                    if( gap >= minPopulation && gap <= maxPopulation ){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean movable( int nextX, int nextY ){
        return ( nextX>=0 && nextX<totalCountrySize && nextY>=0 && nextY<totalCountrySize );
    }

    public static void openGate(){
        int tempIndex = 1;
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        visited = new boolean[totalCountrySize][totalCountrySize];
        for( int i=0; i<totalCountrySize; i++ ){
            for( int j=0; j<totalCountrySize; j++ ){
               if( !visited[i][j] ){
                   int movablePopulation = 0;
                   int movableCountry = 1;
                   movablePopulation += country[i][j].population;
                   visited[i][j] = true;
                   country[i][j].identity = tempIndex;
                   queue.add( new Country( j, i, country[i][j].population ) );
                   while(!queue.isEmpty()){
                       Country temp = queue.poll();
                       int currentX = temp.positionX;
                       int currentY = temp.positionY;
                       for( int k=0; k<4; k++ ){
                           int nextX = currentX + directionX[k];
                           int nextY = currentY + directionY[k];
                           if( !movable( nextX, nextY ) ) continue;
                           int gap = Math.abs(country[currentY][currentX].population - country[nextY][nextX].population);
                           if( !visited[nextY][nextX] && gap >= minPopulation && gap <= maxPopulation ){
                               visited[nextY][nextX] = true;
                               country[nextY][nextX].identity = tempIndex;
                               queue.add( new Country( nextX, nextY, country[nextY][nextX].population ) );
                               movablePopulation += country[nextY][nextX].population;
                               movableCountry++;
                           }
                       }
                   }
                   int tempPopulation = movablePopulation / movableCountry;
                   hashMap.put(tempIndex, tempPopulation);
                   tempIndex++;
               }
            }
        }
        movePopulation( hashMap );
    }

    public static void movePopulation( HashMap<Integer, Integer> hashMap ){
        for( int i=0; i<totalCountrySize; i++ ){
            for( int j=0; j<totalCountrySize; j++ ){
                if( hashMap.containsKey(country[i][j].identity) ){
                    country[i][j].population = hashMap.get(country[i][j].identity);
                }
            }
        }
    }

    public static void resetCountry(){
        for (int i = 0; i < totalCountrySize; i++) {
            for (int j = 0; j < totalCountrySize; j++) {
                country[i][j].identity = 0;
            }
        }
    }

}
