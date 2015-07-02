package gk.training.codeeval;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Date: 7/1/15
 */
public class COdeEvalTriangle {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File(System.getProperty("user.home") + "/triangle.txt"));
        List<String> list = new ArrayList<String>();
        while (sc.hasNextLine()) {
             String str = sc.nextLine().trim();
             if (!str.isEmpty()) list.add(str);
        }                       //rows       //columns
        int[][] array = new int[list.size()][list.get(list.size() -1).split("\\s+").length];
        for (int row = list.size() - 1; row >= 0; row--) {
            String[] arr = list.get(row).split("\\s+");
            for (int column = 0; column < arr.length; column++) {
                int val = Integer.parseInt(arr[column]);
                if (row == list.size() -1) array[row][column] = val;
                else {
                    int leftChildIndex = column;
                    int rightChildIndex = column + 1;
                    int sumLeft = val + array[row + 1][leftChildIndex];
                    int sumRight = val + array[row + 1][rightChildIndex];
                    array[row][column]= Math.max(sumLeft, sumRight);
                }
            }
        }
        System.out.println(array[0][0]);
    }
}
