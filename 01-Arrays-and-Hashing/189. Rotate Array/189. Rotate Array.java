1class Solution {
2    public void rotate(int[] nums, int k) {
3        
4        int n= nums.length;
5        k= k%n;
6        reverse(nums,0,n-1);
7        reverse(nums,0,k-1);
8        reverse(nums,k,n-1);
9
10    }
11    public void reverse(int [] arr, int i, int j){
12        while(i<j){
13            int temp= arr[i];
14            arr[i] = arr[j];
15            arr[j] = temp;
16            i++;
17            j--;
18        }
19    }
20}