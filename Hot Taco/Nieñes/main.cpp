#include <iostream>
#include <string>

using namespace std;

string countVotes(int[]);

int main() {

    string childs[6];
    int votes[6];

    for(int i = 0; i < 6; i++) {
        cout << "[" << i + 1 << "] - Ingresa tu nombre: ";
        cin >> childs[i];

        cout << "[" << i + 1 << "] - Por quien vas a votar?\n0: Ana\n1: Michel\n";
        cin >> votes[i];
    }

    cout << countVotes(votes);

    return 0;
}

string countVotes(int votes[]) {

    int ana = 0, michel = 0;

    for(int i = 0; i < 6; i++) {
        if(votes[i] == 0) ana++;
        else              michel++;
    }

    if(ana > michel)        return "Ana gano!!";
    else if(ana < michel)   return "Michel gano!!";
    else                    return "Empeate xd";
}