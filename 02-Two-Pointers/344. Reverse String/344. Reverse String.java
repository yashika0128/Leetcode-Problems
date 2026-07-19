1class Solution {
2    public void reverseString(char[] s) {
3        // int i=0;
4        // int j= s.length-1;
5        // while(i<j){
6        //     char temp = s[i];
7        //     s[i]= s[j];
8        //     s[j]= temp;
9        //     i++;
10        //     j--;
11        // }
12
13        for (int i = 0, j = s.length - 1; i < j; i++, j--) {
14            char temp = s[i];
15            s[i] = s[j];
16            s[j] = temp;
17        }
18        return;
19
20    
21    }
22}