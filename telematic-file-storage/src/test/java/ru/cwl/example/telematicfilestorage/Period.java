package ru.cwl.example.telematicfilestorage;

import org.junit.Test;

public class Period {
    /*
    математика с периодами
 0   0  0
 1   0  0
 2   0  0
 3   0  0
 4   0  0
----------
 5   1  5
 6   1  5
 7   1  5
 8   1  5
 9   1  5
----------
10   2  10
11   2  10
     */

    @Test
    public void periodTest(){
        long len=5;
        for (long i = 0; i < 12; i++) {
            final long l = i / len;
            System.out.printf("%2d   %d  %d\n",i, l,l*len);
        }
    }
}
