### Description

Given a file `input.txt` containing an array of tuples. Each tuple comprises of three integers, representing the width,
height and length of a postbox. One box can fit into another if and only if all it's dimensions (width, height and
length) are less than the dimensions of the other box.  
Write a program that would return (in `output.txt`) the maximum depth - number of boxes that can be put one inside the
other.

#### Explanatory example:

Suppose there are 3 boxes: `x, y, z`. If `x` fits `y` and `y` fits `z` , then depth for `x in y in z` is 3.

#### Note:

1. You can rotate the boxes.
2. Only one box can be fit into another

#### Assumptions:

* There are at most 50 boxes
* All dimensions are at most 999 units

* * *

### Example 1

input.txt

```
[1 1 1] [2 2 2] [3 3 3]
```

output.txt

```
3
```

* * *

### Example 2

input.txt

```
[5 4 2] [6 4 5] [6 7 4] [2 3 5]
```

output.txt

```
2
```