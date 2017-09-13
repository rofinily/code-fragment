package anc;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Finder finder = new Finder();
        int[] a = new int[]{14,95,83,55,71,57,87,39,44,52,68,66,92,46,63,94,18,9,86,19,7,5,47,98,15,25,53,32,21,11,6,88,23,24,8,74,81,72,70,17,34,43,4,3,16,27,13,33,61,56,78,89,22,73,93,85,67,62,30,38,76,2,10,29,97,64,80,48,31,1,40,96,58,12,75,54,51,59,26,28,69,35,91,37,99,41,36,79,42,49,84,20,65,0,82,60,77,45,90,50 };
        finder.findMth2Nth(a, 0, a.length - 1, a.length - 4, a.length - 1);
        System.out.println(Arrays.toString(a));
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));
    }
}