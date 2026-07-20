<h2><a href="https://leetcode.com/problems/maximum-nesting-depth-of-the-parentheses">1614. Maximum Nesting Depth of the Parentheses</a></h2>

<p>Given a <strong>valid parentheses string</strong> <code>s</code>, return the <strong>nesting depth</strong> of<em> </em><code>s</code>. The nesting depth is the <strong>maximum</strong> number of nested parentheses.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = "(1+(2*3)+((8)/4))+1"</span></p>

<p><strong>Output:</strong> <span class="example-io">3</span></p>

<p><strong>Explanation:</strong></p>

<p>Digit 8 is inside of 3 nested parentheses in the string.</p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = "(1)+((2))+(((3)))"</span></p>

<p><strong>Output:</strong> <span class="example-io">3</span></p>

<p><strong>Explanation:</strong></p>

<p>Digit 3 is inside of 3 nested parentheses in the string.</p>
</div>

<p><strong class="example">Example 3:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = "()(())((()()))"</span></p>

<p><strong>Output:</strong> <span class="example-io">3</span></p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= s.length &lt;= 100</code></li>
	<li><code>s</code> consists of digits <code>0-9</code> and characters <code>'+'</code>, <code>'-'</code>, <code>'*'</code>, <code>'/'</code>, <code>'('</code>, and <code>')'</code>.</li>
	<li>It is guaranteed that parentheses expression <code>s</code> is a VPS.</li>
</ul>


---

# 🛍️ Maximum-Nesting-Depth-of-the-Parentheses | Explained

## Approach 1: Stack-Assisted Depth Tracking
### Intuition
Imagine walking through a set of nested Russian nesting dolls or entering successive inner rooms. Every time you encounter an opening parenthesis `(`, you are stepping one level deeper into a nested layer. Every time you encounter a closing parenthesis `)`, you exit the current layer and return to the outer one. 

To track how deep we go, we can record our entry and exit. In this approach, a stack is used to physically store the opened parentheses, mimicking the nested structure, while a counter tracks the current depth. The maximum depth achieved during this traversal is our answer.

### Algorithm Visualized
```mermaid
flowchart TD
    A[Start: depth = 0, maxdep = 0] --> B[Loop through each char in String]
    B --> C{Char == '(' ?}
    C -- Yes --> D[Push '(' to Stack\nIncrement depth]
    C -- No --> E{Char == ')' ?}
    E -- Yes --> F[Pop from Stack\nDecrement depth]
    E -- No --> G[Skip non-parenthesis char]
    D --> H[Update maxdep = max_of_maxdep_and_depth]
    F --> H
    G --> B
    H --> B
    B -- Loop Finished --> I[Return maxdep]
```

### Approach
1. **Initialize Trackers:** Create a stack to hold characters, an integer `depth` to track the current nesting level, and an integer `maxdep` to store the maximum depth encountered.
2. **Iterate:** Traverse the string character by character.
3. **Handle Opening Parenthesis `(`:** 
   - Push the character onto the stack.
   - Increment the current `depth` by 1.
4. **Handle Closing Parenthesis `)`:** 
   - Pop the matching opening parenthesis from the stack.
   - Decrement the current `depth` by 1.
5. **Update Maximum:** At each step, update `maxdep` to be the maximum of its current value and the newly calculated `depth`.
6. **Return Result:** Once the string is fully processed, return `maxdep`.

### Detailed Code Analysis
- **Line 3 (`Stack <Character> st = new Stack<>();`):** Instantiates a Stack object. While functional for tracking the parentheses, the stack's size is always identical to the `depth` variable, making it an auxiliary structure that can be optimized later.
- **Line 4 (`int n= s.length();`):** Caches the length of the string to avoid calling `.length()` repeatedly inside the loop condition, which is a good micro-optimization.
- **Lines 6-7 (`int depth = 0; int maxdep = 0;`):** Prepares the counters. `depth` monitors the active level of nesting, and `maxdep` preserves the peak depth seen at any point in the iteration.
- **Line 8 (`for(int i=0; i<n; i++)`):** Runs a standard linear scan across the string index by index.
- **Lines 10-13 (`if(ch == '('){ st.push(ch); depth++; }`):** When an opening bracket is found, we log it in our stack and go deeper (`depth++`).
- **Lines 14-17 (`else if(ch == ')'){ st.pop(); depth-- ; }`):** When a closing bracket is found, we pop the corresponding opening bracket off the stack and decrease our depth level (`depth--`).
- **Line 18 (`maxdep = Math.max(maxdep, depth);`):** This is executed during every iteration. It safely captures the highest value `depth` reaches before any decrements occur.

### Code
```java
class Solution {
    public int maxDepth(String s) {
        Stack <Character> st = new Stack<>();
        int n= s.length();

        int depth = 0;
        int maxdep = 0;
        for(int i=0; i<n; i++){
            char ch = s.charAt(i);
            if(ch == '('){
                st.push(ch);
                depth++;
            } 
            else if(ch == ')'){
                st.pop();
                depth-- ;
            }
            maxdep = Math.max(maxdep, depth);
        }
        return maxdep;
    }
}
```

### Complexity
- **Time Complexity:** $\mathcal{O}(N)$ where $N$ is the length of the string `s`. We perform a single pass over the string, and stack push/pop operations take $\mathcal{O}(1)$ time.
- **Space Complexity:** $\mathcal{O}(N)$ auxiliary space. In the worst-case scenario (e.g., a string like `(((((()))))`), the stack will hold up to $N/2$ characters simultaneously.

---

## 🕵️‍♂️ Follow-up Questions

### 1. Can we optimize the space complexity of this solution to $\mathcal{O}(1)$?
**Answer:** Yes. The stack is physically duplicating the work of the integer `depth`. Since the problem guarantees that the input string is a Valid Parenthesis String (VPS), we do not need to validate the structure using a stack. We can safely remove `Stack<Character> st` entirely. 

By only incrementing and decrementing the integer `depth` variable, the auxiliary space complexity drops from $\mathcal{O}(N)$ to $\mathcal{O}(1)$ constant space.

### 2. How would you modify this code to handle invalid parenthesis strings?
**Answer:** If the input string could be invalid, our code would crash with an `EmptyStackException` on line 15 (`st.pop()`) if a closing parenthesis `)` appeared without a matching opening parenthesis. 

To make this robust:
1. Before popping, we must check if the stack is empty (`if (st.isEmpty()) return -1;` or throw an exception).
2. After the loop completes, if the stack is not empty (`!st.isEmpty()`), it means there are unclosed opening parentheses, making the string invalid.