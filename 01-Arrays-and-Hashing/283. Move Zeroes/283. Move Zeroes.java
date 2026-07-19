1class Solution {
2    public void moveZeroes(int[] nums) {
3        int n= nums.length;
4        // int i=0;
5        // int j=0;
6
7        // while(i<n){
8        //     if(nums[i]==0){
9        //         i++;
10        //     }else{
11        //         nums[j]= nums[i];
12        //         i++;
13        //         j++;
14        //     }
15        // }
16        // for(int k=j; k<n; k++){
17        //     nums[k]=0;
18        // }
19
20        int i= 0;
21        for(int j = 0; j<n ; j++){
22            if(nums[j] !=0){
23                int temp= nums[j];
24                nums[j]= nums[i];
25                nums[i]= temp;
26                i++;
27            }
28        }
29    }
30}