1class MyQueue {
2
3    Stack <Integer> in;
4    Stack <Integer> out;
5    public MyQueue() {
6        in = new Stack<>();
7        out = new Stack<>();
8    }
9    
10    public void push(int x) {
11        in.push(x);
12
13    }
14    
15    public int pop() {
16        shiftStack();
17        return out.pop();
18    }
19    
20    public int peek() {
21        shiftStack();
22        return out.peek();
23    }
24    
25    public boolean empty() {
26        return in.isEmpty() && out.isEmpty();
27    }
28
29    private void shiftStack(){
30        if(out.isEmpty()){
31            while(!in.isEmpty()){
32                out.push(in.pop());
33            }
34        }
35    }
36}
37
38/**
39 * Your MyQueue object will be instantiated and called as such:
40 * MyQueue obj = new MyQueue();
41 * obj.push(x);
42 * int param_2 = obj.pop();
43 * int param_3 = obj.peek();
44 * boolean param_4 = obj.empty();
45 */