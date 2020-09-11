package algorithm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Kakao_2018_01_dartGame {
    public static void main(String[] args) {
        String dartResult = "1D#2S*3S";
        Solution_dartgame processSolution = new Solution_dartgame();
        System.out.println( processSolution.solution( dartResult ) );
    }
}

class Solution_dartgame{
    public int solution(String dartResult) {
        Pattern pattern = Pattern.compile( "([\\d]+)([SDT])([*#]?)" );
        Matcher matcher = pattern.matcher( dartResult );
        int beforeNumber = 0;
        int sum = 0;
        while( matcher.find() ){
            if( matcher.group( 3 ).equals( "*" ) ){
                sum -= beforeNumber;
                sum += beforeNumber * 2;
            }
            int currentNumber = (int) Math.pow( Integer.parseInt( matcher.group( 1 ) ),
                    changeBonusToNumber( matcher.group( 2 ) ) )
                    * changeOptionToNumber( matcher.group( 3 ) );
            sum += currentNumber;
            beforeNumber = currentNumber;
        }
        return sum;
    }

    public int changeBonusToNumber( String bonus ){
        if( bonus.equals( "S" ) ){
            return 1;
        }else if( bonus.equals( "D" ) ){
            return 2;
        }else{
            return 3;
        }
    }

    public int changeOptionToNumber( String option ){
        if( option.equals( "*" ) ){
            return 2;
        }else if( option.equals( "#" ) ){
            return -1;
        }else{
            return 1;
        }
    }
}
