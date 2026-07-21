1class Solution {
2    public int[] runningSum(int[] nums) {
3        int n= nums.length;
4        for(int i=1;i<n;i++){
5            nums[i] =nums[i]+nums[i-1];
6        }
7        return nums;
8    }
9   
10}