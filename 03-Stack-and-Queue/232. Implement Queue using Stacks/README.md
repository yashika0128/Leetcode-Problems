<h2><a href="https://leetcode.com/problems/implement-queue-using-stacks">232. Implement Queue using Stacks</a></h2>

<p>Implement a first in first out (FIFO) queue using only two stacks. The implemented queue should support all the functions of a normal queue (<code>push</code>, <code>peek</code>, <code>pop</code>, and <code>empty</code>).</p>

<p>Implement the <code>MyQueue</code> class:</p>

<ul>
	<li><code>void push(int x)</code> Pushes element x to the back of the queue.</li>
	<li><code>int pop()</code> Removes the element from the front of the queue and returns it.</li>
	<li><code>int peek()</code> Returns the element at the front of the queue.</li>
	<li><code>boolean empty()</code> Returns <code>true</code> if the queue is empty, <code>false</code> otherwise.</li>
</ul>

<p><strong>Notes:</strong></p>

<ul>
	<li>You must use <strong>only</strong> standard operations of a stack, which means only <code>push to top</code>, <code>peek/pop from top</code>, <code>size</code>, and <code>is empty</code> operations are valid.</li>
	<li>Depending on your language, the stack may not be supported natively. You may simulate a stack using a list or deque (double-ended queue) as long as you use only a stack's standard operations.</li>
</ul>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre><strong>Input</strong>
["MyQueue", "push", "push", "peek", "pop", "empty"]
[[], [1], [2], [], [], []]
<strong>Output</strong>
[null, null, null, 1, 1, false]

<strong>Explanation</strong>
MyQueue myQueue = new MyQueue();
myQueue.push(1); // queue is: [1]
myQueue.push(2); // queue is: [1, 2] (leftmost is front of the queue)
myQueue.peek(); // return 1
myQueue.pop(); // return 1, queue is [2]
myQueue.empty(); // return false
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= x &lt;= 9</code></li>
	<li>At most <code>100</code>&nbsp;calls will be made to <code>push</code>, <code>pop</code>, <code>peek</code>, and <code>empty</code>.</li>
	<li>All the calls to <code>pop</code> and <code>peek</code> are valid.</li>
</ul>

<p>&nbsp;</p>
<p><strong>Follow-up:</strong> Can you implement the queue such that each operation is <strong><a href="https://en.wikipedia.org/wiki/Amortized_analysis" target="_blank">amortized</a></strong> <code>O(1)</code> time complexity? In other words, performing <code>n</code> operations will take overall <code>O(n)</code> time even if one of those operations may take longer.</p>


---

# 🛍️ Implement-Queue-using-Stacks | Explained

## Approach 1: Amortized O(1) Operations (Two Stacks - Lazy Transfer)

### Intuition
A queue operates on a **FIFO (First-In, First-Out)** basis, whereas a stack operates on a **LIFO (Last-In, First-Out)** basis. To reverse the LIFO order of a stack to match the FIFO behavior of a queue, we can use two stacks.

Imagine a warehouse logistics process:
1. **The Inbound Stack (`s1`):** All incoming inventory is stacked here as it arrives. The newest items sit on top.
2. **The Outbound Stack (`s2`):** When customers request the oldest item (FIFO), we cannot access it directly from the bottom of `s1`. Instead, we flip the entire inbound stack upside down into the outbound stack. The oldest item, which was at the very bottom of `s1`, is now at the very top of `s2`, ready to be shipped out immediately.

We only perform this transfer of elements when `s2` is completely empty. This "lazy evaluation" ensures we do not waste CPU cycles repeatedly shifting elements back and forth.

### Algorithm Visualized

Here is how the stacks behave during a sequence of `push(1)`, `push(2)`, `pop()`, `push(3)`, and `pop()` operations:

```mermaid
graph TD
    subgraph State 1: After Push 1 and Push 2
        s1_1["s1: [1, 2] (Top is 2)"] 
        s2_1["s2: [] (Empty)"]
    end

    subgraph State 2: Pop requested (Trigger Transfer s1 -> s2)
        s1_2["s1: []"]
        s2_2["s2: [2, 1] (Top is 1)"]
        pop_1["Pop returns: 1"]
    end

    subgraph State 3: After Push 3
        s1_3["s1: [3]"]
        s2_3["s2: [2] (Top is 2)"]
    end

    subgraph State 4: Pop requested (s2 is not empty, no transfer)
        s1_4["s1: [3]"]
        s2_4["s2: []"]
        pop_2["Pop returns: 2"]
    end

    State 1 -->|pop() triggers transfer| State 2
    State 2 -->|push(3)| State 3
    State 3 -->|pop() uses s2 directly| State 4
```

### Approach
1. **`push(x)`:** Always push elements directly onto the input stack `s1`. This is an $O(1)$ operation.
2. **`pop()`:** Check if the output stack `s2` is empty. 
   - If `s2` is empty, pop all elements from `s1` and push them onto `s2` one by one. This reverses their order.
   - Pop and return the top element of `s2`.
3. **`peek()`:** Similar to `pop()`, but instead of removing the element, return the top of `s2`. If `s2` is empty, perform the transfer from `s1` first.
4. **`empty()`:** The queue is empty only if both `s1` and `s2` have no elements remaining.

### Detailed Code Analysis

Let's break down the mechanics of the provided Java implementation:

* **Member Variables:**
  ```java
  private Stack<Integer> s1 = new Stack<>();
  private Stack<Integer> s2 = new Stack<>();
  ```
  Two instances of `java.util.Stack` are instantiated. 
  *(Senior Engineer's Note: In modern Java production code, `Deque<Integer> stack = new ArrayDeque<>()` is preferred over `java.util.Stack` because `Stack` is thread-safe via synchronization, which adds unnecessary overhead in single-threaded contexts).*

* **`push(int x)` Method:**
  ```java
  public void push(int x) {
      s1.push(x);
  }
  ```
  New elements are blindly accepted into the input stack `s1`. 

* **`pop()` Method:**
  ```java
  public int pop() {
      if (s2.isEmpty()) {
          while (!s1.isEmpty())
              s2.push(s1.pop());
      }
      return s2.pop();
  }
  ```
  Here, we lazily populate `s2`. If `s2` still contains elements from a previous transfer, we bypass the `while` loop entirely and pop from `s2` in $O(1)$ time. If `s2` is empty, we transfer everything from `s1` to `s2` using a loop, resetting the order.

* **`peek()` Method:**
  ```java
  public int peek() {
      if (!s2.isEmpty()) {
          return s2.peek();
      } else {
          while (!s1.isEmpty())
              s2.push(s1.pop());
      }
      return s2.peek();
  }
  ```
  This follows the same transfer pattern as `pop()`, but leaves the target element on the top of `s2` via `peek()`.
  *(Senior Engineer's Code Smell Hint: There is duplicated logic between `pop()` and `peek()`. In a production setting, `pop()` should call `peek()` to fetch the value, and then perform `s2.pop()` to remove it, keeping code DRY).*

* **`empty()` Method:**
  ```java
  public boolean empty() {
      return s1.isEmpty() && s2.isEmpty();
  }
  ```
  Since queue elements can reside in either the input or output buffers, the queue is only empty when both underlying stacks are empty.

### Code

```java
class MyQueue {
    private Stack<Integer> s1 = new Stack<>();
    private Stack<Integer> s2 = new Stack<>();

    /** Initialize your data structure here. */
    public MyQueue() {
        
    }
    
    /** Push element x to the back of queue. */
    public void push(int x) {
        s1.push(x);
    }
    
    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        if (s2.isEmpty()) {
            while (!s1.isEmpty())
                s2.push(s1.pop());
        }
        return s2.pop();
    }
    
    /** Get the front element. */
    public int peek() {
        if (!s2.isEmpty()) {
            return s2.peek();
        } else {
            while (!s1.isEmpty())
                s2.push(s1.pop());
        }
        return s2.peek();
    }
    
    /** Returns whether the queue is empty. */
    public boolean empty() {
        return s1.isEmpty() && s2.isEmpty();
    }
}
```

### Complexity

- **Time Complexity:**
  - **`push(x)`**: $O(1)$. We perform a single stack push.
  - **`pop()` / `peek()`**: **Amortized $O(1)$**, Worst-case $O(N)$.
    - *Amortized Proof*: Each element in the queue is pushed to `s1` exactly once, popped from `s1` exactly once, pushed to `s2` exactly once, and popped from `s2` exactly once. For any sequence of $N$ operations, the total work spent on transfers is $2N$ operations. Thus, the average time spent per operation is $O(1)$.
  - **`empty()`**: $O(1)$.

- **Space Complexity:**
  - **$O(N)$** auxiliary space, where $N$ is the number of elements in the queue. The elements are distributed across the two stacks `s1` and `s2`.

---

## 🕵️‍♂️ Follow-up Questions

### 1. Why is the amortized time complexity $O(1)$ for `pop` and `peek`, even though there is a nested loop inside them?
In the worst-case scenario, `s2` is empty and `s1` contains $N$ elements. In this specific call, transferring elements takes $O(N)$ time. However, this expensive operation is rare. Once the transfer completes, the next $N-1$ `pop()` or `peek()` operations will execute in strict $O(1)$ time because they retrieve elements directly from `s2`. 

If we sum the total number of operations (pushes to `s1`, transfers to `s2`, pops from `s2`) for any sequence of operations, each element is touched a constant number of times (at most 4 operations). Therefore, dividing the total cost by the number of actions yields an average (amortized) cost of $O(1)$ per operation.

### 2. How can we make this class thread-safe?
To make this queue safe for concurrent access (multi-threading):
1. **Synchronized Blocks:** Wrap the internal logic of `push`, `pop`, `peek`, and `empty` in synchronized blocks, lock on a private monitor object, or use Java's legacy `Stack` built-in synchronization (though not recommended due to coarse-grained locking).
2. **Read-Write Locks:** Use a `ReentrantReadWriteLock`. Multiple threads can call `empty()` or `peek()` concurrently (if `s2` is not empty), while write operations like `push()` and `pop()` lock the structure exclusively.