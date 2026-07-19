1class Solution {
2    public int findMaxConsecutiveOnes(int[] nums) {
3        int n = nums.length;
4        int max_c = 0;
5        int count =0;
6        for(int i=0 ; i<n ; i++){
7            
8            if (nums[i] == 1) count ++;
9            else count =0;
10
11            max_c = Math.max(max_c, count);
12        }
13        return max_c;
14    }
15}