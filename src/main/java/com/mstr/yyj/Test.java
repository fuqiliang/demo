package com.mstr.yyj;

import java.util.*;

public class Test {
    public static void main(String... args){
       String s = longestPalindrome("cbbd");
       System.out.println(s);
    }

    public static int pathCount(int i, int j) {
        if (i < 0 || j < 0) return 0;
        if (i == 0 && j == 0) return 1;

        return pathCount(i - 1, j) + pathCount(i, j - 1);
    }


    public static int totalFruit(int[] tree) {
        if (tree.length < 3) return tree.length;

        Map<Integer, Integer> set = new HashMap<Integer, Integer>();
        int max = 0;
        int count = 0;
        for (int i = 0; i < tree.length; i++) {
            boolean contains = set.containsKey(tree[i]);
            if (!contains && set.size() == 2){
                max = Math.max(max, count);
                int lastIndex = set.get(tree[i-1]);
                set.clear();
                set.put(tree[i-1], lastIndex);
                set.put(tree[i], i);
                count = i - lastIndex + 1;
            } else if (contains) {
                count++;
            } else if (!contains) {
                count++;
                set.put(tree[i], i);
            }
        }

        return Math.max(max, count);
    }

    public static String multiply(String num1, String num2) {
        int len1 = num1.length() - 1;
        int len2 = num2.length() - 1;
        int maxLen = len1 + len2 + 2;

        int[] results = new int[maxLen];
        int[] carrys = new int[maxLen + 1];
        for (int i = 0; i <= len2; i++) {
            int multi = num2.charAt(len2 - i) - '0';
            for (int j = 0; j <= len1; j++) {
                int tmp = (num1.charAt(len1 - i) - '0') * multi;
                int pos = i+j;

                int low = tmp % 10;
                int high = tmp / 10;

                update(pos, results, carrys, low);
                update(pos + 1, results, carrys, high);
            }
        }

        StringBuilder sb = new StringBuilder();
        int  k =  maxLen - 1;
        while(k >= 0 && results[k] == 0) k--;
        while (k >= 0) {
            sb.append(results[k--]);
        }

        return sb.toString();
    }

    private static void update(int index, int[] results, int[] carrys, int toAdd) {
        int sum = results[index] + toAdd + carrys[index];
        results[index] = sum % 10;
        carrys[index] = 0;
        carrys[index + 1] =  carrys[index + 1] + sum / 10;
    }

    public static int minSubArrayLen(int s, int[] nums) {
        int len  = nums.length;
        if (len == 0) return 0;
        if (len == 1) return (nums[0] >= s) ? 1 : 0 ;

        int i = 0, j = 0;
        long sum = 0;
        int minCount = 0;

        while (j < len) {
            while(j < len && sum + nums[j] < s) {
                sum += nums[j];
                j++;
            }
            if (j == len) break;
            minCount = j - i + 1;
            System.out.println("i:" + i + ",j :" + j);
            sum += nums[j];
            while(i <= j && sum - nums[i] >= s) {
                sum -= nums[i];
                i++;
            }
            minCount = Math.min(minCount, j - i + 1);
            System.out.println("i:" + i + ",j :" + j);
            if (minCount == 1) break;

            sum = sum - nums[i];
            i++;
            j++;
        }

        return minCount;
    }

    public static void quickSort(int[] arr) {
        loopR(arr,0, arr.length - 1);
    }

    public static String longestPalindrome(String s) {
        if (s == null || s.length() <= 1) return s;

        int maxLength = 0;
        int curLeft = 0;
        int curRight = 0;
        int maxIndex = s.length() - 1;
        for (int i = 0; i <= maxIndex; i++) {
            int gap = 0;
            int left = i;
            int right = i;
            while (left >= 0 && right <= maxIndex) {
                if (s.charAt(left) != s.charAt(right)) {
                    break;
                }

                gap++;
                left = i - gap;
                right = i + gap;
            }

            int curLength = 1 + 2 * (gap - 1);
            if (curLength > maxLength) {
                maxLength = curLength;
                curLeft = left + 1;
                curRight = right - 1;
            }
        }

        for (int i = 0; i <= maxIndex; i++) {
            int gap = 0;
            int left = i;
            int right = i + 1;

            while (left >= 0 && right <= maxIndex) {
                if (s.charAt(left) != s.charAt(right)) {
                    break;
                }

                gap++;
                left = i - gap;
                right = i + gap;
            }

            int curLength = 2 + 2 * (gap - 1);
            if (curLength > maxLength) {
                maxLength = curLength;
                curLeft = left + 1;
                curRight = right - 1;
            }
        }

        if (maxLength == 0) return s.charAt(0) + "" ;
        return s.substring(curLeft, curRight+1);
    }

    private static void loopR(int[] arr, int low, int high) {
        if (low >= high) return;

        int i = low;
        int j = high;
        while(i < j) {
            while( i < j && arr[j] >= arr[i]) j--;
            if (arr[j] < arr[i]) {
                swap(arr, i, j);
            }
            while (i < j && arr[j] >= arr[i]) i++;
            if (arr[j] < arr[i]) {
                swap(arr, i, j);
            }
        }

        loopR(arr, low, i-1);
        loopR(arr, i+1, high);

    }

    private static void loop(int[] arr, int low, int high) {

        Stack<Integer> stack = new Stack<Integer>();
        stack.push(high);
        stack.push(low);

        while (!stack.empty()) {
            int tmpLow = stack.pop();
            int tmpHigh = stack.pop();

            if (tmpLow >= tmpHigh) continue;
            int i = tmpLow;
            int j = tmpHigh;

            while (i < j){
                while (i < j && arr[j] >= arr[i]) {
                    j--;
                }
                if (arr[j] < arr[i]) {
                    swap(arr, i, j);
                }
                while(i < j && arr[j] >= arr[i]) {
                    i++;
                }

                if (arr[j] < arr[i]) {
                    swap(arr, i, j);
                }
            }

            stack.push(j - 1);
            stack.push(tmpLow);

            stack.push(tmpHigh);
            stack.push(j + 1);
        }
    }

    private static void  swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

}


