package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        List<Point> place = new ArrayList<Point>();
          //#if_define debug
            place.add( new Point( 10, 10 ));
            place.add( new Point( 20, 20 ));
            place.add( new Point( 30, 30 ));
            place.add( new Point( 40, 40 ));

        Scanner input_terminal = new Scanner( System.in );
        boolean flag_work = true;

        while( flag_work ) {

            System.out.print("\n\nPoint Place program. Generate by UKTC on EARTH. \r\n\t " +
                    "Menu < select opiton >: \r\n\t\t" +
                    "1 - new point \r\n\t\t" +
                    "2 - show all point \r\n\t\t" +
                    "3 - calc distance on two point \r\n\t\t" +
                    "4 - show min distance \r\n\t\t" +
                    "5 - exit app \r\n" +
                    ">");

            String selection = input_terminal.nextLine();

            if (selection.matches("^[1-5]$")) switch (Integer.parseInt(selection)) {
                // insert point in list
                case 1:
                    System.out.print("Create new point in Place. Input x and y coordinate: \n X = ");
                    String x = input_terminal.nextLine();
                    System.out.print("Y = ");
                    String y = input_terminal.nextLine();

                    if (x.matches("-?[0-9]*[.\\,0-9]+$") && y.matches("-?[0-9]*[.\\,0-9]+$")) {
                        try {
                            x.replace(",", ".");
                            y.replace(",", ".");
                            System.out.println("Point input " + (
                                    (place.add(
                                            new Point(
                                                    Double.parseDouble(x),
                                                    Double.parseDouble(y)
                                            )
                                    )
                                    ) ? "Point created on coordinate: X = " +x+ " Y = " +y : "Error. Try again."));

                        } catch (NumberFormatException ex) {
                            System.out.println(" Error input number parset. Try again. Examp input: 0.3 / 0,3 / 3 / -3");
                        }
                    } else System.out.println(" Invalid input number. Examp input: 0.3 / 0,3 / 3 / -3");

                break;

                // printing all in list
                case 2:
                    if(! place.isEmpty() ) {
                        System.out.println(" Print all points: \r\t ");
                        int n = 0;
                        for (Point point : place) {
                            System.out.println("index n: " + n++ + " / " + point);
                        }
                    } else System.out.println( "No available points");
                break;

                // calculate distance A to B point
                case 3:
                    System.out.println( "Insert point number. To show number -> select from menu < option 2 > to show all insered points " );
                    Point[] pointsForCalc = new Point[2]; // A[null], B[null]

                    for( int index = 0 ; true ; ) { // while input OK

                        System.out.print( " index > ");
                        String n_point = input_terminal.nextLine();

                        if ( n_point.matches("^[0-9]*$") ) {

                            try {

                                pointsForCalc[index] = place.get( Integer.parseInt( n_point ) );
                                System.out.println( " \t " + index + " - " + pointsForCalc[index]);

                                if( index == pointsForCalc.length -1  || index >= pointsForCalc.length ) break;

                                index++;

                            } catch (NumberFormatException ex) {
                                System.out.println("Error number parset. " + ex + " Try again.");

                            } catch (IndexOutOfBoundsException ex) {
                                System.out.println("Index out of boinds. Check your index and insert currectly! ");
                            }

                        } else System.out.println("Invalid input index point. Try again. Examp: 1[ENTER]");
                    }

                    // get distance A.to( B )
                    if( pointsForCalc[0] != null && pointsForCalc[1] != null )
                        System.out.println( "Distance = " + pointsForCalc[0].getDistanceToPoint( pointsForCalc[1] ) );

                break;

                // get minimal distance
                case 4:
                    System.out.println(" Calculating minimum distance from all point... ");

                    boolean flag_first = true;
                    double D = 0.0f; // distance D
                    HashMap<Integer[], Point[] > eqdispoints = new HashMap<>(); // index[A][B] , Point[A][B] equally_distance_bettween_points

                    // alg
                    /*
                     [0][1][2][3] [0][1] [0][2] [0][3] -> result put in MAP
                     [0][1][2][3] <1,0>  [1][2] [1][3] ->
                     [0][1][2][3] <2,0>  <2,1>  [2][3] ->
                                  <3,0>  <3,1>  <3,2>  X
                     remove mirror compares -> [0][1] or [1][0] is equals.
                    */

                    for( int a = 0; a < place.size(); a++ ){
                        Point _A = place.get( a );
                        System.out.println("");

                        for( int b = a+1; b <  place.size() ; b++ ) {
                            Point _B = place.get( b );
                            double _D = _A.getDistanceToPoint(_B);
                            if( flag_first ) {
                                D = _D;
                                flag_first = false;
                            }

                            int stat = Double.compare(D, _D);

                            System.out.println( "[" + a + "][" + b + "]  -> " + _A + "  <---D---> " + _B + " =  " + _D + " " +D  );

                            if( stat == 0 ){  // D = _D
                                eqdispoints.put( new Integer[]{ a, b }  , new Point[]{_A, _B} );

                            }else if( stat > 0 ){ // D > _D
                                eqdispoints.clear();
                                eqdispoints.put( new Integer[]{ a, b }  , new Point[]{_A, _B} );

                            }

                        }
                    }




                    /*
                    for( int a = 0; a < place.size(); a++ ){
                        Point _A = place.get( a );

                        for( int b = 1 ; b < place.size(); b++ ) {
                            Point _B = place.get( b );
                            double _D = _A.getDistanceToPoint( _B );

                            /*
                            System.out.println( " D ? " + a + _A + "  < --- > " + b+ _B + "   = " + _A.getDistanceToPoint( _B ) + "      min dist = " + D );
                            int stat = Double.compare( D , _D );
                            if( flag_first || stat >= 0  ) {
                                flag_first = false;
                                D = _D;
                                if( stat != 0 ) eqdispoints.clear();
                                else eqdispoints.put( new Integer[]{ a, b }  , new Point[]{_A, _B} );
                            }
                        }
                    }*/


                    // output
                    System.out.println( ( eqdispoints.isEmpty() )? "No result.": "Minimal distance = " + D + " with " + (( eqdispoints.size() == 1 )?" point":"points") + ": " );
                    eqdispoints.forEach( ( indexes, points ) -> {
                            System.out.print( "\tPoint A with index: " + indexes[0] + " coordinate " + points[0]  + " to " +
                                              "Point B with index: " + indexes[1] + " coordinate " + points[1]  + "\n\r" );

                        }
                    );

                break;

                // exit
                case 5:
                    System.out.println("EXIT");
                    flag_work = false;
                break;

            }
            else  System.out.println("Invalid input selection! Examp if you want to exit app < option 3 > from menu you input: 3[ENTER]");
        }
        input_terminal.close();

    }
}





class Point {
    private double x = 0.0f;
    private double y = 0.0f;

    public Point(){

    }

    public Point ( double x, double y ){
        this.x = x;
        this.y = y;
    }

    public double getDistanceToPoint ( Point b ){
        /*
                | < --- xb ----- O ( xb, yb )
                |           D   /|
                | <--- xa --> O( xa , ya )
                |             ^  |
                |         ya  |  | yb
         ------ o ---------------------
                |

           D<ab> = sqrt( ( xb - xa )^ + ( yb - ya )^ )   if the difference is equal to or less than 0 D is ZERO, next -> flip
        */
        double D = Math.sqrt( Math.pow(( b.getX() - this.getY() ), 2) + Math.pow(( b.getY() - this.getY() ), 2) );
        return (Double.compare( D, 0.0 ) == 0 )?b.getDistanceToPoint( this ):D ;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
