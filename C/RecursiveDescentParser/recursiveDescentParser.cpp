Name: Anand Suresh Kamathi Campus ID: 013694471
CECS 444 [Compiler Construction] Term Project 2
Problem Statement: Develop a recursive descent parser which processes floating
point numbers for BODMAS along with factorial, nn, en computation
#include <stdio.h>
#include <stdlib.h>
#include <string>
#include <cctype>
#include <cmath>
#include <iostream>
using namespace std;
char token; /*global token variable*/
/*function prototypes for recursive calls*/
float exponent(void); //exponential right associative [n^n ; e^n]
float toExpo(void); //* / ! operations
int factorial(int);
float exp(void); //+ - operations
float term(void); //unary minus
float factor();
void error(void)
{
fprintf(stderr, "Error\n");
exit(1);
}
void match(char expectedToken)
{
if (token == expectedToken)
token = getchar();
else
error();
}
int main()
{
float result;
token = getchar();/*Reading token with lookahead for first character*/
result = exp();
if (token == '\n')/*check for end of line*/
printf("Result = %f\n", result);
else
{
error();//Incorrect input
}
return 0;
}
//Treating + - operations with same precedence
float exp()
{
float temp = term();
while ((token == '+') || (token == '-'))
{
switch (token)
{
case '+':
match('+');
temp += term();
break;
case '-':
match('-');
temp -= term();
break;
}
}
return temp;
}
//Treating * / ! operations with same precedence
float term()
{
float temp = toExpo();
while ((token == '*') || (token == '/') || (token == '!'))
{
switch (token)
{
case '*':
match('*');
temp *= factor();
break;
case '/':
match('/');
temp /= factor();
break;
case '!':
match('!');
temp = factorial(temp);
break;
}
}
return temp;
}
//Directing to highest priority power function
float toExpo()
{
return exponent();
}
//right associative exponent
float exponent()
{
float temp = factor();
while (token == '^')
{
switch (token)
{
case '^':
match('^');
temp = pow(temp, exponent()); //utilizing right associativity of power function
break;
}
}
return temp;
}
float factor()
{
float temp,value;
float test;
if (token == '(')
{
match('(');
temp = exp();
match(')');
value=temp;
}
else if (isdigit(token) || (token == '-') || (token == 'e'))
{
string str;
str = token;
if (token == 'e')
{
ungetc(token, stdin);
scanf_s("%c", &temp);
token = getchar();
value = 2.718;
}
else
{
while (isdigit(token) || (token == '.')) //Buffering all length of float number
str += token = getchar(); // before and after decimal point
ungetc(token, stdin);
scanf_s("%c", &temp);
std::string::size_type stype;
value = stof(str, &stype);
}
}
else
error();
return value;
}
int factorial(int n)
{
int rVal;
rVal = (n == 1 || n == 0) ? 1 : n*factorial(n - 1);
return rVal;
}
