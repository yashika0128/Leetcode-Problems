1class Solution {
2    public int[] productExceptSelf(int[] nums) {
3        // int left =1;
4        // int [] ans = new int[nums.length];
5        // for(int i=0;i<nums.length;i++){
6        //     int right=1;
7        //     for(int j=nums.length-1; j>i;j--){
8        //         right *= nums[j];
9        //     }
10        //     ans[i] = left*right;
11        //     left *= nums[i];
12        // }
13
14        int [] arr_right= new int[nums.length];
15        int right=1;
16        for(int i=nums.length-1;i>=0;i--){
17            arr_right[i]= right;
18            right *= nums[i];
19        }
20
21        int [] ans= new int[nums.length];
22        int left= 1;
23        for(int i=0;i<nums.length;i++){
24            ans[i] = left*arr_right[i];
25            left *= nums[i];
26        }
27        return ans;
28    }
29}