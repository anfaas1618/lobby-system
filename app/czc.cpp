#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;


int main() {
        int size;
        cin>>size;
        vector<int> v;
        for (int i=0; i<size; i++) {
            int x;
            cin>>x;
        v.push_back(x);
        }
        int del;
        cin>>del;
        v.erase(v.begin()+del-1);
      

        int start,end;
        cin>>start>>end;
        v.erase(v.begin()+start,v.end()+end);
     int   sizee=v.size();
     for (int x=0; x<sizee; x++) {
         cout<<v[x]<<" ";
     }

    return 0;
}
