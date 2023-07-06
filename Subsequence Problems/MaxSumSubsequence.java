import java.util.ArrayList;
import java.util.List;

public class MaxSumSubsequence {
    public static void main(String[] args) {
        int[] array = {0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11};
        List<Integer> maxSumSubsequence = findMaxSumSubsequence(array);

        int maxSum = maxSumSubsequence.stream().mapToInt(Integer::intValue).sum();

        System.out.print("Max Sum Subsequence: ");
        for (int i = 0; i < maxSumSubsequence.size(); i++) {
            System.out.print(maxSumSubsequence.get(i));
            if (i < maxSumSubsequence.size() - 1) {
                System.out.print(", ");
            }
        }

        System.out.println();
        System.out.println("Max Sum: " + maxSum);
    }

    private static List<Integer> findMaxSumSubsequence(int[] array) {
        List<Integer> currentSubsequence = new ArrayList<>();
        List<Integer> maxSubsequence = new ArrayList<>();
        int currentSum = 0;
        int maxSum = Integer.MIN_VALUE;

        findMaxSumSubsequence(array, currentSubsequence, 0, currentSum, maxSum, maxSubsequence);

        return maxSubsequence;
    }

    private static void findMaxSumSubsequence(int[] array, List<Integer> currentSubsequence, int index, int currentSum, int maxSum, List<Integer> maxSubsequence) {
        if (index == array.length) {
            if (currentSum > maxSum) {
                maxSum = currentSum;
                maxSubsequence.clear();
                maxSubsequence.addAll(currentSubsequence);
            }
            return;
        }

        findMaxSumSubsequence(array, currentSubsequence, index + 1, currentSum, maxSum, maxSubsequence);

        if (currentSubsequence.isEmpty() || array[index] > currentSubsequence.get(currentSubsequence.size() - 1)) {
            int newSum = currentSum + array[index];
            currentSubsequence.add(array[index]);
            findMaxSumSubsequence(array, currentSubsequence, index + 1, newSum, maxSum, maxSubsequence);
            currentSubsequence.remove(currentSubsequence.size() - 1);
        }
    }
}
