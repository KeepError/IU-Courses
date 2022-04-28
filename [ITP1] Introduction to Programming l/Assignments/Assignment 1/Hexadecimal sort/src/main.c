#include <stdio.h>
#include <stdlib.h>

int compare(const void *a, const void *b) {
    int f = *((int *) a);
    int s = *((int *) b);
    if (f > s) return 1;
    if (f < s) return -1;
    return 0;
}

int main() {
    FILE *filePointer;
    char buffer[1000];

    filePointer = fopen("input.txt", "r");
    fscanf(filePointer, "%[^\n]", buffer);
    printf("%s", buffer);
    fclose(filePointer);

    char *end;
    long int li;
    end = buffer;
    int base = 16;
    int n = 0;

    int nums[1000];

    li = strtol(end, &end, base);
    while (li) {
        nums[n] = li;
        n += 1;
        li = strtol(end, &end, base);
    }

    qsort(nums, n, sizeof(n), compare);

    char out[1000];

    int index = 0;
    for (int i = 0; i < n; i++) {
        index += sprintf(&out[index], "%X ", nums[i]);
    }


    filePointer = fopen("output.txt", "w");
    fputs(out, filePointer);
    fclose(filePointer);

    return 0;
}