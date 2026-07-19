1class Solution {
2    public int calPoints(String[] arr) {
3        int n = arr.length;
4
5        Stack <Integer> st = new Stack <>();
6        for(int i=0; i<n;i++){
7            String ch= arr[i];
8            
9            if(ch.equals(C)) st.pop();
10            else if(ch.equals(D)){
11                st.push(2*st.peek());
12            }
13            else if(ch.equals(+)){
14                int top = st.pop();
15                int sum = top+ st.peek();
16
17                st.push(top);
18                st.push(sum);
19            }
20            else{
21                st.push(Integer.parseInt(ch));
22            }
23        }
24        int ans=0;
25        while(!st.isEmpty()){
26            ans += st.pop();
27        }
28
29        return ans;
30    }
31}