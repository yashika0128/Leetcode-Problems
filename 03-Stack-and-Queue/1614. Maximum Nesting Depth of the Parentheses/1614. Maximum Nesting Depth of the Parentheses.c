1class Solution {
2    public int maxDepth(String s) {
3        Stack <Character> st = new Stack<>();
4        int n= s.length();
5
6        int depth = 0;
7        int maxdep = 0;
8        for(int i=0; i<n; i++){
9            char ch = s.charAt(i);
10            if(ch == '('){
11                st.push(ch);
12                depth++;
13            } 
14            else if(ch == ')'){
15                st.pop();
16                depth-- ;
17            }
18            maxdep = Math.max(maxdep, depth);
19        }
20        return maxdep;
21    }
22}