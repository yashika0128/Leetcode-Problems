1class Solution {
2    public boolean isUgly(int n) {
3        if (n <= 0) {
4            return false;
5        }
6        while (n > 1) {
7            if (n % 2 == 0) {
8                n /= 2;
9            } else if (n % 3 == 0) {
10                n /= 3;
11            } else if (n % 5 == 0) {
12                n /= 5;
13            } else {
14                return false;
15            }
16        }
17        return true;
18    }
19}