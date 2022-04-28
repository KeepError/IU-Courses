#include <stdio.h>
#include <string.h>

char result[500];
int invalid_input = 0;

int get_split_index(char s[]) {
    int spaces_count = 0;
    int current_index = strlen(s) - 1;
    while (spaces_count < 3) {
        if (s[current_index] == ' ') {
            spaces_count++;
        }
        current_index--;
    }
    return current_index + 1;
}

void remove_n(char s[]) {
    for (int i = 0; i < strlen(s); i++) {
        if (s[i] == '\n') {
            s[i] = '\0';
            break;
        }
    }
}

struct Item {
    char name[100];
    float size;
    int amount;
    char unit[10];
    char temp[30];
};

struct User {
    char name[100];
    struct {
        int day;
        int month;
        int year;
        int hour;
        int minute;
        int second;
    } datetime;
    struct Item items[50];
    int items_count;
};

int eng_letters_check(char s[]) {
    for (int i = 0; i < strlen(s); i++) {
        if (!((s[i] >= 'a' && s[i] <= 'z') || (s[i] >= 'A' & s[i] <= 'Z') || (s[i] == ' '))) {
            return 0;
        }
    }
    return 1;
}

int check_name(struct User user) {
    char s[100];
    strcpy(s, user.name);
    if (!(eng_letters_check(s))) {
        return 0;
    }
    if ((strlen(user.name) < 2) || (strlen(user.name) > 30)) {
        return 0;
    }
    return 1;
}

int check_datetime(struct User user) {
    if ((user.datetime.year < 0) || (user.datetime.month <= 0) || (user.datetime.day <= 0)) {
        printf("%s", user.name);
        return 0;
    } // Non negative check
    if (user.datetime.month > 12) {
        return 0;
    } // month check
    if (user.datetime.month == 2) {
        if (user.datetime.year % 4 == 0) {
            if (user.datetime.day > 29) {
                return 0;
            }
        } else {
            if (user.datetime.day > 28) {
                return 0;
            }
        }
    } // leap years check
    int mon = user.datetime.month;
    if (user.datetime.day > 31) {
        return 0;
    }
    if ((mon == 4) || (mon == 6) || (mon == 9) || (mon == 11)) {
        if (user.datetime.day > 30) {
            return 0;
        }
    } // days in month check
    if ((user.datetime.hour < 0) || (user.datetime.hour > 23)) {
        return 0;
    } // hour check
    if ((user.datetime.minute < 0) || (user.datetime.minute > 59)) {
        return 0;
    } // minute check
    if ((user.datetime.second < 0) || (user.datetime.minute > 59)) {
        return 0;
    } // second check

    return 1;
}

int check_items(struct User user) {
    if (user.items_count < 1) {
        return 0;
    }
    for (int i = 0; i < user.items_count; i++) {
        if ((strlen(user.items[i].name) < 4) || (strlen(user.items[i].name) > 15)) {
            return 0;
        }
        if ((user.items[i].size <= 0) || (user.items[i].size > 200)) {
            return 0;
        }
        if ((user.items[i].amount <= 0) || (user.items[i].amount > 30)) {
            return 0;
        }
        if (!((strcmp(user.items[i].unit, "pcs") == 0) || (strcmp(user.items[i].unit, "pair") == 0))) {
            return 0;
        }
    }
    return 1;
}

int check_user(struct User user) {
    if (!(check_name(user)) || !(check_datetime(user)) || !(check_items(user))) {
        invalid_input = 1;
    }
    return 1;
}

void process_user(struct User user) {
    check_user(user);
    if (invalid_input == 1) {
        return;
    }
    char tmp [1000];
    sprintf(tmp, "%s has rented ", user.name);
    strcat(result, tmp);
    for (int i = 0; i < user.items_count; i++) {
        struct Item item = user.items[i];

        if ((strcmp(item.unit, "pair") == 0) & (item.amount > 1)) {
            strcpy(item.unit, "pairs");
        }

        sprintf(tmp, "%d %s of %s of size %.9g", item.amount, item.unit, item.name, item.size);
        strcat(result, tmp);
        if (i + 1 + 1 < user.items_count) {
            sprintf(tmp, ", ");
            strcat(result, tmp);
        } else if (i + 2 == user.items_count) {
            sprintf(tmp, " and ");
            strcat(result, tmp);
        }
    }
    sprintf(tmp, " on %02d/%02d/%02d at %02d:%02d:%02d.\n", user.datetime.day, user.datetime.month, user.datetime.year, user.datetime.hour, user.datetime.minute, user.datetime.second);
    strcat(result, tmp);
}

int main() {
    FILE *input_file = fopen("input.txt", "r");
    FILE *output_file = fopen("output.txt", "w");
    char s[100];
    int state = 0;
    struct User user[50];
    int users_count = 0;
    user[users_count].items_count = 0;

    struct Item item;

    while (!feof(input_file)) {
        fgets(s, 1000, input_file);
        // if we go to the next user
        if (strcmp(s, "\n") == 0) {
            users_count++;
            user[users_count].items_count = 0;
            state = 0;
        } else {
            if (state == 0) {
                // read user name
                strcpy(user[users_count].name, s);
                remove_n(user[users_count].name);
                state++;
            } else if (state == 1) {
                // read date and time
                sscanf(s, "%d/%d/%d %d:%d:%d", &user[users_count].datetime.day, &user[users_count].datetime.month,
                       &user[users_count].datetime.year,&user[users_count].datetime.hour, &user[users_count].datetime.minute, &user[users_count].datetime.second);
                state++;
            } else {
                // read item
                item = (const struct Item) {0};

                int index = get_split_index(s);
                if (index < 0) {
                    invalid_input = 1;
                } else {
                    strncpy(item.name, s, index);
                    strncpy(item.temp, s + index, strlen(s));
                    sscanf(item.temp, "%f %d %s", &item.size, &item.amount, item.unit);

                    user[users_count].items[user[users_count].items_count] = item;
                    user[users_count].items_count++;
                }
            }
        }
    }
    for (int i = 0; i <= users_count; i++) {
        // process users and collect output in the string variable
        process_user(user[i]);
    }
    if (invalid_input == 0) {
        fprintf(output_file, "%s", result);
    } else {
        fprintf(output_file, "Invalid input!");
    }

    return 0;
}