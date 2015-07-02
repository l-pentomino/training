package gk.training.codeeval;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Date: 6/30/15
 */
public class CodeEvalIdenticalBits {

        public static void main (String[] args) throws IOException {
            File file = new File(args[0]);
            BufferedReader buffer = new BufferedReader(new FileReader(file));
            String line;
            while ((line = buffer.readLine()) != null) {
                line = line.trim();
                String[] arr = line.split(",\\s*");
                int num = Integer.parseInt(arr[0]);
                int p1 = Integer.parseInt(arr[1]);
                int p2 = Integer.parseInt(arr[2]);
                System.out.println(checkBits(num, p1 - 1, p2 - 1));
            }
        }

        public static boolean checkBits(int num, int p1, int p2) {
            int mask1 = num & (0x1 << p1); //butmask num with position p1
            int mask2 = num & (0x1 << p2); //bitmask num with position p2
            return ((mask1 >>> p1) == (mask2  >>> p2));
        }
    }


