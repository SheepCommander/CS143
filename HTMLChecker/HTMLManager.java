// Jun Ong
// CS 143
// HW Core Topics: Queues and Stacks
//
// This program will manage and fix queues of HTML tags
import java.util.*;

public class HTMLManager {
    private Queue<HTMLTag> tags;

    // pre: html is an initialized queue
    public HTMLManager(Queue<HTMLTag> html) {
        if (html == null) {
            throw new IllegalArgumentException("");
        }
        this.tags = html;
    }

    // pre: HTMLManager constructed with a valid queue
    public Queue<HTMLTag> getTags() {
        return this.tags;
    }

    // pre: HTMLManager constructed with a valid queue
    // post: queue is fixed, all opening tags have matching closing tags
    public void fixHTML() {
        Stack<HTMLTag> openingTags = new Stack<>(); // keep track of all opening tags

        int iterations = this.tags.size();
        for (int i=0; i<iterations; i++) {
            HTMLTag tag = tags.poll();
            if (tag.isSelfClosing()) {
                tags.add(tag); // add it back to queue
                continue;
            }
            if (tag.isOpening()) {
                tags.add(tag); // 1- add it back to the queue
                openingTags.add(tag); // 2- add to stack
                continue;
            }
            if (tag.isClosing()) {
                if (openingTags.isEmpty()) {
                    // 0- discard the closing tag as it has no match
                    continue;
                }
                if (openingTags.peek().matches(tag)) {
                    tags.add(tag); // 1- add it back to the queue
                    openingTags.pop(); // 2- remove the opening tag from the queue
                    continue;
                } else {
                    // 0- writer made a mistake and we will not add closing tag back to queue
                    HTMLTag correctTag = openingTags.pop().getMatching(); // 1- remove top of stack
                    tags.add(correctTag); // 2- add correct closing tag to queue
                    continue;
                }
            }
        }
        //if stack is not empty, add all matching closing tags
        while (!openingTags.isEmpty()) {
            HTMLTag openingTag = openingTags.pop();
            this.tags.add(openingTag.getMatching());
        }
    }

    @Override
    public String toString() {
        String out = "";
        for (HTMLTag tag : this.tags) {
            out += tag.toString().trim();
        }
        return out;
    }
}
/*
===============================
Processing tests/test3.html...
===============================
HTML: <br /></p></p>
Checking HTML for errors...
HTML after fix: <br />
----> Result matches Expected Output!

===============================
Processing tests/test2.html...
===============================
HTML: <a><a><a></a>
Checking HTML for errors...
HTML after fix: <a><a><a></a></a></a>
----> Result matches Expected Output!

===============================
Processing tests/test5.html...
===============================
HTML: <div><h1></h1><div><img /><p><br /><br /><br /></div></div></table>
Checking HTML for errors...
HTML after fix: <div><h1></h1><div><img /><p><br /><br /><br /></p></div></div>
----> Result matches Expected Output!

===============================
Processing tests/test4.html...
===============================
HTML: <div><div><ul><li></li><li></li><li></ul></div>
Checking HTML for errors...
HTML after fix: <div><div><ul><li></li><li></li><li></li></ul></div></div>
----> Result matches Expected Output!

===============================
Processing tests/test1.html...
===============================
HTML: <b><i><br /></b></i>
Checking HTML for errors...
HTML after fix: <b><i><br /></i></b>
----> Result matches Expected Output!

===============================
        All tests passed!
===============================
 */