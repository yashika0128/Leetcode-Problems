1class Solution {
2    public boolean backspaceCompare(String s, String t) {
3        Stack <Character> st = new Stack<>();
4        Stack <Character> tt = new Stack<>();
5
6        int n1= s.length();
7        int n2 = t.length();
8
9        for(int i=0; i<n1; i++){
10            char ch = s.charAt(i);
11
12            if(ch == '#'){
13                if(!st.isEmpty()){
14                    st.pop();
15                }
16            }
17            else{
18                st.push(ch);
19            }
20        }
21
22        String s1 = ;
23        while(!st.isEmpty()){
24            s1 += st.pop();
25        }
26    
27
28        for(int i=0; i<n2; i++){
29            char ch = t.charAt(i);
30
31            if(ch == '#'){
32                if(!tt.isEmpty()){
33                    tt.pop();
34                }
35            }
36            else{
37                tt.push(ch);
38            }
39        }
40
41        String s2 = ;
42        while(!tt.isEmpty()){
43            s2 += tt.pop();
44        }
45       
46        if(s1.equals(s2)) return true;
47        else return false;
48
49    }
50}