<h2><a href="https://leetcode.com/problems/baseball-game">682. Baseball Game</a></h2>

<p>You are keeping the scores for a baseball game with strange rules. At the beginning of the game, you start with an empty record.</p>

<p>You are given a list of strings <code>operations</code>, where <code>operations[i]</code> is the <code>i<sup>th</sup></code> operation you must apply to the record and is one of the following:</p>

<ul>
	<li>An integer <code>x</code>.

	<ul>
		<li>Record a new score of <code>x</code>.</li>
	</ul>
	</li>
	<li><code>'+'</code>.
	<ul>
		<li>Record a new score that is the sum of the previous two scores.</li>
	</ul>
	</li>
	<li><code>'D'</code>.
	<ul>
		<li>Record a new score that is the double of the previous score.</li>
	</ul>
	</li>
	<li><code>'C'</code>.
	<ul>
		<li>Invalidate the previous score, removing it from the record.</li>
	</ul>
	</li>
</ul>

<p>Return <em>the sum of all the scores on the record after applying all the operations</em>.</p>

<p>The test cases are generated such that the answer and all intermediate calculations fit in a <strong>32-bit</strong> integer and that all operations are valid.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre><strong>Input:</strong> ops = ["5","2","C","D","+"]
<strong>Output:</strong> 30
<strong>Explanation:</strong>
"5" - Add 5 to the record, record is now [5].
"2" - Add 2 to the record, record is now [5, 2].
"C" - Invalidate and remove the previous score, record is now [5].
"D" - Add 2 * 5 = 10 to the record, record is now [5, 10].
"+" - Add 5 + 10 = 15 to the record, record is now [5, 10, 15].
The total sum is 5 + 10 + 15 = 30.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre><strong>Input:</strong> ops = ["5","-2","4","C","D","9","+","+"]
<strong>Output:</strong> 27
<strong>Explanation:</strong>
"5" - Add 5 to the record, record is now [5].
"-2" - Add -2 to the record, record is now [5, -2].
"4" - Add 4 to the record, record is now [5, -2, 4].
"C" - Invalidate and remove the previous score, record is now [5, -2].
"D" - Add 2 * -2 = -4 to the record, record is now [5, -2, -4].
"9" - Add 9 to the record, record is now [5, -2, -4, 9].
"+" - Add -4 + 9 = 5 to the record, record is now [5, -2, -4, 9, 5].
"+" - Add 9 + 5 = 14 to the record, record is now [5, -2, -4, 9, 5, 14].
The total sum is 5 + -2 + -4 + 9 + 5 + 14 = 27.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre><strong>Input:</strong> ops = ["1","C"]
<strong>Output:</strong> 0
<strong>Explanation:</strong>
"1" - Add 1 to the record, record is now [1].
"C" - Invalidate and remove the previous score, record is now [].
Since the record is empty, the total sum is 0.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= operations.length &lt;= 1000</code></li>
	<li><code>operations[i]</code> is <code>"C"</code>, <code>"D"</code>, <code>"+"</code>, or a string representing an integer in the range <code>[-3 * 10<sup>4</sup>, 3 * 10<sup>4</sup>]</code>.</li>
	<li>For operation <code>"+"</code>, there will always be at least two previous scores on the record.</li>
	<li>For operations <code>"C"</code> and <code>"D"</code>, there will always be at least one previous score on the record.</li>
</ul>


---

# 🛍️ Baseball-Game | Explained

## Approach 1: Stack-Based Record Simulation
### Intuition
The problem requires us to maintain a running record of scores where new scores are added, and previous scores can be removed, doubled, or added together based on specific commands. Because these operations always depend on the most recently added scores (the "last in" elements), a **Stack** data structure is the ideal real-world analogy. It behaves like a physical stack of plates: we can easily add a score to the top, look at the top score, or remove the most recent score.

### Algorithm Visualized
```mermaid
graph TD
    A[Start: Loop through operations] --> B{Current String}
    B -->| "C" | C[Pop top element from Stack]
    B -->| "D" | D[Peek top element, double it, and push to Stack]
    B -->| "+" | E[Pop top element 'top', Peek next element 'prev', push 'top' back, then push 'top + prev']
    B -->| Integer | F[Parse string to integer and push to Stack]
    C --> G{More elements?}
    D --> G
    E --> G
    F --> G
    G -->|Yes| A
    G -->|No| H[Empty stack and sum all elements]
    H --> I[Return final sum]
```

### Approach
1. **Initialize a Stack**: Create an integer stack `st` to store the active scores.
2. **Iterate Through Operations**: Loop through each string `ch` in the input array `arr`.
3. **Evaluate Operations**:
   - If the operation is `"C"`, invalidate the last score by popping it from the stack.
   - If the operation is `"D"`, double the last score. Retrieve the top element using `peek()`, multiply it by 2, and push the result.
   - If the operation is `"+"`, calculate the sum of the last two valid scores. Pop the top element to access the second-to-last element via `peek()`, add them together, and push both the popped element and the new sum back onto the stack.
   - Otherwise, the operation is an integer score. Parse it using `Integer.parseInt(ch)` and push it onto the stack.
4. **Sum the Results**: After processing all operations, pop all remaining elements from the stack and accumulate their sum to obtain the final answer.

### Detailed Code Analysis
While the logic of the provided code is conceptually sound, **it contains critical compilation errors** due to unquoted character/string literals. Below is a line-by-line analysis of the exact code provided, highlighting these syntax issues:

* **Line 3 & 5:**
  ```java
  int n = arr.length;
  Stack <Integer> st = new Stack <>();
  ```
  The length of the array is stored, and a standard Java `Stack` is initialized. Note that in modern Java, `Deque<Integer> st = new ArrayDeque<>()` is preferred over `Stack` because `Stack` is synchronized and carries unnecessary overhead.

* **Lines 9-11 (Syntax Error):**
  ```java
  if(ch.equals(C)) st.pop();
  else if(ch.equals(D)){
      st.push(2*st.peek());
  }
  ```
  **Error:** `C` and `D` are written as unquoted identifiers. The compiler searches for variables named `C` and `D`, which do not exist. To fix this, they must be represented as string literals: `ch.equals("C")` and `ch.equals("D")`.
  **Logic:** `"C"` removes the last element (score invalidated). `"D"` peeks at the top element, doubles it, and pushes it back.

* **Lines 13-19 (Syntax Error):**
  ```java
  else if(ch.equals(+)){
      int top = st.pop();
      int sum = top+ st.peek();

      st.push(top);
      st.push(sum);
  }
  ```
  **Error:** `+` is an operator, resulting in a severe compile-time syntax error when passed without quotes. It must be written as `ch.equals("+")`.
  **Logic:** To sum the top two elements without permanently losing the top element, we temporarily `pop()` the top element, look at the next element using `peek()`, calculate the sum, and then push both the original `top` and the new `sum` back to the stack.

* **Lines 20-22:**
  ```java
  else{
      st.push(Integer.parseInt(ch));
  }
  ```
  If the string does not match any operator, it is parsed into an integer and stored as a base score.

* **Lines 24-29:**
  ```java
  int ans=0;
  while(!st.isEmpty()){
      ans += st.pop();
  }
  return ans;
  ```
  The stack is drained element-by-element to calculate the final cumulative sum of all valid records.

### Code
Below is the clean code snippet representing your approach with the necessary syntax fixes applied:

```java
import java.util.Stack;

class Solution {
    public int calPoints(String[] arr) {
        int n = arr.length;

        Stack<Integer> st = new Stack<>();
        for (int i = 0; i < n; i++) {
            String ch = arr[i];
            
            // Fixed syntax errors by adding double quotes around string literals
            if (ch.equals("C")) {
                st.pop();
            } else if (ch.equals("D")) {
                st.push(2 * st.peek());
            } else if (ch.equals("+")) {
                int top = st.pop();
                int sum = top + st.peek();

                st.push(top);
                st.push(sum);
            } else {
                st.push(Integer.parseInt(ch));
            }
        }
        
        int ans = 0;
        while (!st.isEmpty()) {
            ans += st.pop();
        }

        return ans;
    }
}
```

### Complexity
- **Time Complexity:** $\mathcal{O}(N)$ where $N$ is the number of operations in `arr`. We iterate through the array of size $N$ once, performing $\mathcal{O}(1)$ push, pop, or peek operations at each step. Summing up the stack at the end also takes $\mathcal{O}(N)$ time.
- **Space Complexity:** $\mathcal{O}(N)$ auxiliary space. In the worst-case scenario (where all inputs are numeric scores), we store all $N$ elements in the stack.

---

## 🕵️‍♂️ Follow-up Questions

### 1. How would you optimize the space complexity if you wanted to avoid using a standard `java.util.Stack`?
**Answer:** 
We can replace `Stack` with an integer array and an active pointer index (effectively a custom array-backed stack). This avoids the object overhead of `Integer` wrapper classes (boxing/unboxing) and the synchronization overhead of Java's legacy `Stack` class:

```java
int[] scores = new int[arr.length];
int size = 0;
for (String ch : arr) {
    if (ch.equals("C")) {
        size--;
    } else if (ch.equals("D")) {
        scores[size] = scores[size - 1] * 2;
        size++;
    } else if (ch.equals("+")) {
        scores[size] = scores[size - 1] + scores[size - 2];
        size++;
    } else {
        scores[size] = Integer.parseInt(ch);
        size++;
    }
}
```

### 2. Why is `Deque<Integer> stack = new ArrayDeque<>()` preferred over `Stack<Integer> stack = new Stack<>()` in Java?
**Answer:**
Java's `Stack` class extends `Vector`, which makes all operations synchronized. This thread-safety overhead is unnecessary for single-threaded execution contexts. `ArrayDeque` is unsynchronized, faster, and more memory-efficient because it does not pay the synchronization cost on every push and pop operation.