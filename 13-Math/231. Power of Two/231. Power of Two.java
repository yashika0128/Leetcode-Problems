1class Solution {
2    public boolean isPowerOfTwo(int n) {
3        // if(n<1) return false;
4        // while(n !=1){
5        //     if(n %2 ==1) return false;
6        //     n /=2;
7        // }
8        // return true;
9
10        if(n == 1) return true;
11        if(n<=0 || n%2 !=0) return false;
12
13        return isPowerOfTwo(n/2);
14    }
15}