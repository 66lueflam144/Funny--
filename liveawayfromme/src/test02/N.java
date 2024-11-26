package test02;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class N {
    // 随机生成数组的方法
    static int[] generateRandomArray(int length) {
        Random random = new Random();
        Set<Integer> set = new HashSet<>();
        while (set.size() < length) {
            set.add(random.nextInt(1000));
        }
        int[] array = new int[length];
        int index = 0;
        for (int num : set) {
            array[index++] = num;
        }
        return array;
    }

    // 2. 判断数组状态
    public static int checkArrayStatus(int[] arr) {
        if (arr.length <= 1) return 0; // 单个元素或空数组无法判断状态

        boolean hasAscended = false; // 是否找到升序部分
        boolean hasDescended = false; // 是否找到降序部分
        int peak = -1; // 升序变降序的转折点
        int valley = -1; // 降序变升序的转折点

        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] < arr[i + 1]) { // 当前是升序
                hasAscended = true;
                if (hasDescended) {
                    // 如果已经出现过降序，再出现升序 -> 乱序
                    return 0;
                }
            } else if (arr[i] > arr[i + 1]) { // 当前是降序
                hasDescended = true;
                if (!hasAscended) {
                    // 如果在升序之前直接出现降序
                    valley = i;
                } else {
                    // 如果已经有升序，再出现降序
                    peak = i;
                }
            }
        }

        if (hasAscended && !hasDescended) return 1; // 严格升序
        if (!hasAscended && hasDescended) return 2; // 严格降序
        if (peak != -1 && peak < arr.length - 1 && arr[peak] > arr[peak + 1]) return 3; // 先升后降
        if (valley != -1 && valley < arr.length - 1 && arr[valley] < arr[valley + 1]) return 4; // 先降后升

        return 0; // 乱序
    }




    // 3. 顺序查找元素
    public static int sequentialSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) return i;
        }
        return -1;
    }

    // 4. 选择排序
    public static int[] selectionSort(int[] arr) {
        int[] newArr = arr.clone();
        for (int i = 0; i < newArr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < newArr.length; j++) {
                if (newArr[j] < newArr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = newArr[i];
                newArr[i] = newArr[minIndex];
                newArr[minIndex] = temp;
            }
        }
        return newArr;
    }

    // 5. 冒泡排序
    public static int[] bubbleSort(int[] arr) {
        int[] newArr = arr.clone();
        for (int i = 0; i < newArr.length - 1; i++) {
            for (int j = 0; j < newArr.length - 1 - i; j++) {
                if (newArr[j] > newArr[j + 1]) {
                    int temp = newArr[j];
                    newArr[j] = newArr[j + 1];
                    newArr[j + 1] = temp;
                }
            }
        }
        return newArr;
    }

    // 6. 二分查找
    public static int binarySearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (arr[mid] == target) return mid;
            else if (arr[mid] < target) low = mid + 1;
            else high = mid - 1;
        }
        return -1;
    }

    // 7. 三分查找
    public static int ternarySearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {

            int mid1 = low + (high - low) / 3;
            int mid2 = high - (high - low) / 3;

            if (arr[mid1] == target) return mid1;
            if (arr[mid2] == target) return mid2;

            if (target < arr[mid1]) high = mid1 - 1;
            else if (target > arr[mid2]) low = mid2 + 1;
            else {
                low = mid1 + 1;
                high = mid2 - 1;
            }
        }
        return -1;
    }

    public static int checkArrayStatusWithternary(int[] arr) {
        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {
            // 计算两个中间点
            int mid1 = low + (high - low) / 3;
            int mid2 = high - (high - low) / 3;

            // 比较 mid1 和 mid2 位置的元素
            if (arr[mid1] < arr[mid2]) {
                // 如果 mid1 < mid2, 则说明中间部分升序，说明可能是先升后降
                // 继续在右边搜索
                low = mid1 + 1;
            } else if (arr[mid1] > arr[mid2]) {
                // 如果 mid1 > mid2, 则说明中间部分降序，说明可能是先降后升
                // 继续在左边搜索
                high = mid2 - 1;
            } else {
                // 如果 mid1 == mid2，表示这两部分等于，不需要进一步判断
                return 0;
            }
        }

        return 0;
    }
}
