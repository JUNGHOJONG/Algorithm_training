package algorithm.swea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Swea_2477 {
    public static class Desk{
        private int index;
        private final int processTime;
        private boolean isPerson = false;
        private int elapsedTime = 0;
        private Customer customer = null;
        public Desk( int index, int processTime ){
            this.index = index;
            this.processTime = processTime;
        }
    }
    public static class Customer{
        private int index;
        private int arrivedTime = 0;
        private int receptionNumber = 0;
        private int repairNumber = 0;
        private int elapsedTime = 0;
        public Customer( int index, int arrivedTime ){
            this.index = index;
            this.arrivedTime = arrivedTime;
        }
    }
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
    private static ArrayList<Desk> receptionList = new ArrayList<>();
    private static ArrayList<Desk> repairList = new ArrayList<>();
    private static PriorityQueue<Customer> arrivedList = new PriorityQueue<>(new Comparator<Customer>() {
        @Override
        public int compare(Customer o1, Customer o2) {
            if( o1.arrivedTime == o2.arrivedTime ){
                return o1.index - o2.index;
            }else{
                return o1.arrivedTime - o2.arrivedTime;
            }
        }
    });
    private static PriorityQueue<Customer> receptionWaitList = new PriorityQueue<>(new Comparator<Customer>() {
        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.index - o2.index;
        }
    });
    private static PriorityQueue<Customer> repairWaitList = new PriorityQueue<>(new Comparator<Customer>() {
        @Override
        public int compare(Customer o1, Customer o2) {
            if( o1.elapsedTime == o2.elapsedTime ){
                return o1.receptionNumber - o2.receptionNumber;
            }else{
                return o1.elapsedTime - o2.elapsedTime;
            }
        }
    });
    private static ArrayList<Customer> finalList = new ArrayList<>();
    private static int testCase;
    public static void main(String[] args) throws IOException {
        testCase = Integer.parseInt( br.readLine() );
        for( int i=0; i<testCase; i++ ){
            StringTokenizer st = new StringTokenizer( br.readLine() );
            int receptionDeskCount = Integer.parseInt( st.nextToken() );
            int repairDeskCount = Integer.parseInt( st.nextToken() );
            int totalCustomer = Integer.parseInt( st.nextToken() );
            int receptionAnswer = Integer.parseInt( st.nextToken() );
            int repairAnswer = Integer.parseInt( st.nextToken() );
            initReceptionList( receptionDeskCount );
            initRepairList( repairDeskCount );
            initArrivedList( totalCustomer );
            doCommand();
            print( i, receptionAnswer, repairAnswer );
            receptionList.clear();
            repairList.clear();
            arrivedList.clear();
            receptionWaitList.clear();
            repairWaitList.clear();
            finalList.clear();
        }
    }

    public static void initReceptionList( int receptionDeskCount ) throws IOException{
        StringTokenizer st = new StringTokenizer( br.readLine() );
        for( int i=0; i<receptionDeskCount; i++ ){
            receptionList.add( new Desk( i+1, Integer.parseInt( st.nextToken() ) ) );
        }
    }

    public static void initRepairList( int repairDeskCount ) throws IOException{
        StringTokenizer st = new StringTokenizer( br.readLine() );
        for( int i=0; i<repairDeskCount; i++ ){
            repairList.add( new Desk( i+1, Integer.parseInt( st.nextToken() ) ) );
        }
    }

    public static void initArrivedList( int totalCustomer ) throws IOException{
        StringTokenizer st = new StringTokenizer( br.readLine() );
        for( int i=0; i<totalCustomer; i++ ){
            arrivedList.add( new Customer( i+1, Integer.parseInt( st.nextToken() ) ) );
        }
    }

    public static void doCommand(){
        int time = 0;
        int timelimit = arrivedList.peek().arrivedTime;
        while( true ){
            if( isComplete() && time > timelimit ) break;
            while( !arrivedList.isEmpty() && time == arrivedList.peek().arrivedTime ){
                receptionWaitList.add( arrivedList.poll() );
            }
            // 접수리스트에 넣기
            for( Desk desk : receptionList ){
                if( !desk.isPerson ){
                    if( !receptionWaitList.isEmpty() ){
                        desk.customer = receptionWaitList.poll();
                        desk.elapsedTime = desk.processTime;
                        desk.isPerson = true;
                    }
                }else{
                    desk.elapsedTime--;
                }
                if( desk.elapsedTime == 0 && desk.isPerson ){ // 초기화
                    desk.customer.receptionNumber = desk.index;
                    desk.customer.elapsedTime = time;
                    repairWaitList.add( desk.customer );
                    desk.isPerson = false;
                    desk.customer = null;
                    if( !receptionWaitList.isEmpty() ){ // 보류
                        desk.customer = receptionWaitList.poll();
                        desk.elapsedTime = desk.processTime;
                        desk.isPerson = true;
                    }
                }
            }
            // 정비리스트에 넣기
            for( Desk desk : repairList ){
                if( !desk.isPerson ){
                    if( !repairWaitList.isEmpty() ){
                        desk.customer = repairWaitList.poll();
                        desk.elapsedTime = desk.processTime;
                        desk.isPerson = true;
                    }
                }else{
                    desk.elapsedTime--;
                }
                if( desk.elapsedTime == 0 && desk.isPerson ){ // 초기화
                    desk.customer.repairNumber = desk.index;
                    desk.isPerson = false;
                    finalList.add( desk.customer );
                    desk.customer = null;
                    if( !repairWaitList.isEmpty() ){ // 보류
                        desk.customer = repairWaitList.poll();
                        desk.elapsedTime = desk.processTime;
                        desk.isPerson = true;
                    }
                }
            }
            time++;
        }
    }

    public static boolean isComplete(){
        for( Desk desk : receptionList ){
            if( desk.isPerson ) return false;
        }
        for( Desk desk : repairList ){
            if( desk.isPerson ) return false;
        }
        return true;
    }

    public static void print( int index, int receptionAnswer, int repairAnswer ){
        int sum = 0;
        boolean check = false;
        for( Customer customer : finalList ){
            if( customer.receptionNumber == receptionAnswer
            && customer.repairNumber == repairAnswer ) {
                sum += customer.index;
                check = true;
            }
        }
        if( !check ){
            System.out.println( "#" + (index+1) + " " + -1 );
        }else{
            System.out.println( "#" + (index+1) + " " + sum );
        }
    }
}
