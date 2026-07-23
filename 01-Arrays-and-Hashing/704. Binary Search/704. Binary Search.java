1class Solution {
2    public int search(int[] nums, int target) {
3        int n = nums.length;
4
5        int low = 0;
6        int high = n-1;
7
8        while(low<=high){
9            int mid = (low+high)/2;
10
11            if(nums[mid]== target) return mid;
12            else if(nums[mid]>target) high = mid-1;
13            else low = mid + 1;
14        }
15
16        return -1;
17    }
18}