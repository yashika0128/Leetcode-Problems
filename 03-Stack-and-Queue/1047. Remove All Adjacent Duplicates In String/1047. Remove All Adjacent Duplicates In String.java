1class Solution {
2    public String removeDuplicates(String s) {
3        int n = s.length();
4
5        Stack<Character> st = new Stack<>();
6
7        for(int i=0; i<n; i++){
8            char current = s.charAt(i);
9            if(st.isEmpty()||st.peek()!=current)st.push(current);
10            else{
11                st.pop();
12            }
13        }
14        //String ans=;
15
16        StringBuilder  ans = new StringBuilder ();
17        int si = st.size();
18        for(int i=0; i<si; i++){
19            //ans += st.pop();
20            ans.append(st.pop());
21        }
22
23        return ans.reverse().toString();
24    }
25}