#include <iostream>
#include <bitset>
#include <cmath>
using namespace std;

int N;
const int MAX = 10000000;
bitset<MAX> isPrime;

bool isAmazing(int candi) {
    for(int div10=candi/10; div10>0; div10/=10) {
        if(!isPrime[div10]) return false;
    }
    return true;
}

int main() {

    isPrime.set();
    cin >> N;
    

    int limit = pow(10, N);
    isPrime[0] = isPrime[1] = false;
    for(int i=2; i<=sqrt(limit); i++) {
        if(isPrime[i]) {
            for(int mul=i*i; mul<limit; mul+=i) {
                isPrime[mul] = false;
            }
        }
    }

   
    for(int candi=limit/10*2; candi<limit; candi++)  {
        if(isPrime[candi]) {
            if(isAmazing(candi)) cout << candi << "\n";
        }
    }

}
