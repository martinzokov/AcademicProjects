#include <stdlib.h>
#include <time.h>
#include <stdio.h>
#include "stream_handler.h"

void add_to_list(node ** list_head, location loc) {
    if (*list_head == NULL) {
        *list_head = (node*) malloc(sizeof (node));
        (**list_head).loc = loc;
        (**list_head).next = NULL;
    } else {
        node * current = *list_head;
        while (current->next != NULL) {
            current = current->next;
        }
        current->next = (node*) malloc(sizeof (node));
        current->next->loc = loc;
        current->next->next = NULL;
    }
}
