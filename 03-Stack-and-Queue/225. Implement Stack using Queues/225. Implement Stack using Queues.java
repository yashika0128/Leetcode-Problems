1class MyStack {
2
3    Queue <Integer> q;
4    public MyStack() {
5        q = new LinkedList<>();
6    }
7    
8    public void push(int x) {
9        q.add(x);
10
11        for(int i=0; i<q.size()-1; i++ ){
12            q.add(q.remove());
13        }
14    }
15    
16    public int pop() {
17        return q.remove();
18    }
19    
20    public int top() {
21        return q.peek();
22    }
23    
24    public boolean empty() {
25        return q.isEmpty();
26    }
27}
28
29/**
30 * Your MyStack object will be instantiated and called as such:
31 * MyStack obj = new MyStack();
32 * obj.push(x);
33 * int param_2 = obj.pop();
34 * int param_3 = obj.top();
35 * boolean param_4 = obj.empty();
36 */