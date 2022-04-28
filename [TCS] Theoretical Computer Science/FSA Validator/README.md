# Task

Implement a FSA validator. Given a FSA description in the `fsa.txt` (see **input file format**) your program should output the `result.txt` containing an error description (see **validation result**) or a report, indicating if FSA is complete (or incomplete) and warning (see warning messages) if any. Warnings should be sorted according to their code.

### Validation result

#### Errors:

1.  E1: A state 's' is not in the set of states
2.  E2: Some states are disjoint
3.  E3: A transition 'a' is not represented in the alphabet
4.  E4: Initial state is not defined
5.  E5: Input file is malformed

#### Report:

1.  FSA is complete/incomplete

#### Warnings:

1.  W1: Accepting state is not defined
2.  W2: Some states are not reachable from the initial state
3.  W3: FSA is nondeterministic

* * *

### Input file format

**states**=[s1,s2,...] // s1 , s2, ... ∈ latin letters, words and numbers

**alpha**=[a1,a2,...] // a1 , a2, ... ∈ latin letters, words, numbers and character '_’(underscore)

**init.st**=[s] // s ∈ **states**

**fin.st**=[s1,s2,...] // s1, s2 ∈ **states**

**trans**=[s1>a>s2,...] // s1,s2,...∈ **states**; a ∈ **alpha**

* * *

### Example 1

fsa.txt
```
states=[on,off]
alpha=[turn_on,turn_off]    
init.st=[off]
fin.st=[]
trans=[off>turn_on>off,on>turn_off>on]
```
result.txt
```
Error:
E2: Some states are disjoint
```
* * *

### Example 2

fsa.txt
```
states=[on,off]
alpha=[turn_on,turn_off]    
init.st=[off]
fin.st=[]
trans=[off>turn_on>on,on>turn_off>off]
```
result.txt
```
FSA is incomplete
Warning:
W1: Accepting state is not defined
```