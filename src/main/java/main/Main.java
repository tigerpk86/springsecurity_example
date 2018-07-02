package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    // 앤트
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int length = scan.nextInt();
        int sizeOfAnts = scan.nextInt();
        List<Integer> antPoint = new ArrayList<>(sizeOfAnts);

        for(int i = 0; i < sizeOfAnts ; i++) {
            Integer point = scan.nextInt();
            antPoint.add(point);
            antPoint.add(length-point);
        }

        Collections.sort(antPoint);
        System.out.printf("%d %d", antPoint.get(antPoint.size()/2-1), antPoint.get(antPoint.size()-1));

    }

}
