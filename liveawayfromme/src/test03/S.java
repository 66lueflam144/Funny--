package test03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class S {

    //1.蛮力
    public static int knapscackDFS(int[] wgt, int[] val, int i, int c) {
        long startTime = System.nanoTime();

        int result = KDFforReal(wgt, val, i, c);

        long endTime = System.nanoTime();
        double timeElapsed = (endTime - startTime) / 1_000_000.0;
        System.out.println("DFS Algorithm Time: " + timeElapsed + " ms");
        return result;
    }

    private static int KDFforReal(int[] wgt, int[] val, int i, int c) {
        if (i == 0 || c == 0) {
            return 0;
        }

        if  (wgt[i - 1] > c) {
            return KDFforReal(wgt, val, i - 1, c);
        }

        int no = KDFforReal(wgt, val, i - 1, c);
        int yes = KDFforReal(wgt, val, i - 1, c - wgt[i - 1]) + val[i - 1];


        return Math.max(no, yes);
    }



    //2.动态规划
    public static int knapscackKDP(int[] wgt, int[] val, int cap) {

        long startTime = System.nanoTime();

        int n = wgt.length;
        int[] dp = new int[cap + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = cap; j >= 1; j--) {
                if (wgt[i - 1] <= j) {
                    dp[j] = Math.max(dp[j], dp[j - wgt[i - 1]] + val[i - 1]);
                }
            }
        }

        long endTime = System.nanoTime(); // 结束计时
        double timeElapsed = (endTime - startTime) / 1_000_000.0; // 毫秒
        System.out.println("Dynamic Programming Algorithm Time: " + timeElapsed + " ms");

        return dp[cap];
    }

    //3.贪心算法
    public static List<Integer> GreedyKnapsack(int[] wgt, int[] val, int cap) {

        long startTime = System.nanoTime();

        int n = wgt.length;
        List<Integer> items = new ArrayList<>();
        int totalWeight = 0;

        // 按照价值密度(val/wgt)降序排序
        int[][] items_sorted = new int[n][2];
        for (int i = 0; i < n; i++) {
            items_sorted[i][0] = i;
            items_sorted[i][1] = val[i] / wgt[i];
        }
        Arrays.sort(items_sorted, (a, b) -> b[1] - a[1]);

        int maxvalue = 0;
        // 贪心选择
        for (int[] item : items_sorted) {
            int idx = item[0];
            if (totalWeight + wgt[idx] <= cap) {
                items.add(idx);
                maxvalue += val[idx];
                totalWeight += wgt[idx];
            }
        }

//        System.out.println(maxvalue);
        long endTime = System.nanoTime();
        double timeElapsed = (endTime - startTime) / 1_000_000.0;
        System.out.println("Greedy Algorithm Time: " + timeElapsed + " ms");
        items.add(maxvalue);
        return items;
    }



}
