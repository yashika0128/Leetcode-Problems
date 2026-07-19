1class Solution {
2    public int[] twoSum(int[] arr, int target) {
3        int n = arr.length;
4        int left =0;
5        int right = n-1;
6
7        //int [] arr1 = new int [2];
8
9        while(left<right){
10            int sum = arr[left] + arr[right];
11
12            if(sum == target){
13                // arr1[0]= left+1;
14                // arr1[1] = right+1;
15                // break;
16
17                return new int[]{left+1 , right+1};
18
19            }else if(sum<target) left++;
20
21            else right--;
22        }
23        //return arr1;
24        return new int[]{-1, -1};
25    }
26}