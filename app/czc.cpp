#include<iostream>
using namespace std;
 template <class c>
 c big(c a, c b)
 {
     if
         (a < b)
         cout << b;
     else
     {
         cout << a;
     }
     return a;
 }
 template <class v>

 void big(v a)
 {
    
     
         cout << a;
    
  
 }
 int main()
 {
  int x=   big(1.2,33.4);
  return 0;
 }