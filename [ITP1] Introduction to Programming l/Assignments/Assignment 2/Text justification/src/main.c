#include <stdio.h>
#include <string.h>

int main() {
    FILE *input_file = fopen("input.txt", "r");
    FILE *output_file = fopen("output.txt", "w");
    char text[1000];
    char temp[100];
    int max_width;

    // reading
    fgets(text, 1000, input_file);
    fgets(temp, 100, input_file);
    sscanf(temp, "%d", &max_width);

    int start_index = 0;
    int words_count = 0;
    int last_i = 0;

    int rem;
    int spaces;
    int left;
    int words_passed = 0;

    // looking for all chars
    for (int i = 0; i < strlen(text) + 1; i++) {
        // if met space or end of string
        if ((text[i] == ' ') || (text[i] == '\n')) {
            // if we need to print it
            if (i - start_index > max_width) {
                if (words_count > 1) {
                    rem = max_width - (last_i - start_index - words_count + 1);
                    spaces = rem / (words_count - 1);
                    left = rem % (words_count - 1);

                    for (int j = start_index; j < last_i; j++) {
                        if (text[j] == ' ') {
                            words_passed++;
                            for (int k = 0; k < spaces; k++) {
                                fprintf(output_file, " ");
                            }
                            if (words_passed <= left) {
                                fprintf(output_file, " ");
                            }
                        } else {
                            fprintf(output_file, "%c", text[j]);
                        }
                    }
                } else {
                    for (int j = start_index; j < last_i; j++) {
                        fprintf(output_file, "%c", text[j]);
                    }
                }
                fprintf(output_file, "\n");

                start_index = last_i + 1;
                words_count = 1;
                words_passed = 0;

            } else {
                words_count++;
            }
            last_i = i;
        }
    }

    for (int j = start_index; j < strlen(text) - 1; j++) {
        fprintf(output_file, "%c", text[j]);
    }

    return 0;
}