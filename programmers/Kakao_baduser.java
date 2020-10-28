package algorithm.programmers;

import java.util.ArrayList;
import java.util.HashSet;

public class Kakao_baduser {
    private static ArrayList<String>[] bannedList;
    private static ArrayList<HashSet> setList = new ArrayList<HashSet>();
    private static int userLength;
    private static int bannedLength;
    //    private static String[] user_id = {"frodo", "fradi", "crodo", "abc123", "frodoc"};
//    private static String[] banned_id = {"fr*d*", "abc1**"};
//    private static String[] user_id = {"frodo", "fradi", "crodo", "abc123", "frodoc"};
//    private static String[] banned_id = {"fr*d*", "*rodo", "******", "******"};
    private static String[] user_id = {"frodo", "fradi", "crodo", "abc123", "frodoc"};
    private static String[] banned_id = {"*rodo", "*rodo", "******"};
    public static void main (String[] args) {
        userLength = user_id.length;
        bannedLength = banned_id.length;
        initBannedList( user_id, banned_id );
        dfs( 0, 0, new HashSet<String>() );
        System.out.println(setList.size());
    }

    public static void initBannedList( String[] user_id, String[] banned_id ){
        bannedList = new ArrayList[bannedLength];
        for( int i=0; i<bannedLength; i++ ){
            bannedList[i] = new ArrayList();
        }

        for( int i=0; i<bannedLength; i++ ){
            String bannedId = banned_id[i];
            for( int j=0; j<userLength; j++ ){
                if( bannedId.length() == user_id[j].length() ){
                    boolean check = true;
                    for( int k=0; k<bannedId.length(); k++ ){
                        if( bannedId.charAt(k) == user_id[j].charAt(k) || ( bannedId.charAt(k) == '*' || user_id[j].charAt(k) == '*' ) ){
                            continue;
                        }else{
                            check = false;
                            break;
                        }
                    }
                    if( check ) bannedList[i].add( user_id[j] );
                }
            }
        }
    }

    public static void dfs(int k, int count, HashSet<String> hashSet ){
        if( count == bannedLength ){
            if( hashSet.size() == bannedLength && notOverlap( hashSet ) ){
                setList.add( new HashSet( hashSet ) ); // 주의 new로 꼭 생성
            }
            return;
        }
        for( int i=0; i<bannedList[k].size(); i++ ){
            if( hashSet.contains( bannedList[k].get(i) ) ) continue;
            hashSet.add( bannedList[k].get(i) );
            dfs( k+1, count+1, hashSet );
            hashSet.remove( bannedList[k].get(i) );
        }
    }

    public static boolean notOverlap( HashSet<String> hashSet ){
        int size = setList.size();
        for( int i=0; i<size; i++ ){
            if( setList.get(i).containsAll( hashSet ) ){
                return false;
            }
        }
        return true;
    }
}
