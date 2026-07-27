1class Solution {
2    public int maxProduct(int[] nums) {
3        int n = nums.length;
4
5        Arrays.sort(nums);
6
7        int ans = (nums[n-1]-1)*(nums[n-2]-1);
8
9        return ans;
10    }
11}