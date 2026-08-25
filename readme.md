**interpreter** 
 * making interpreter with the help of the book crafting interpreters and adding some personal touch to the whole project, this interpreter is based on jlox which is lox but in java language.

**NOTE -** this explanation is based on my personal understanding from the book and research.

 # scanner
 The first part of the interpreter was understanding the scanner part of the interpreter which is also known as lexical analyzer.
 ## working of scanner
* The scanner starts with two options which is file reading and prompt reading as coded in `lox.java` scanner can either read from the file directly or if you can interact via terminal and can stop with ctrl D
* ctrl D is a proccess out of the interpreter meaning if ctrl D is pressed terminal will stop with inputs and the code will get a null thus stopping reading the new lines

### file reader:
with the help of the `Charset.defaultCharset()` it turn the incoming Bytes into readable characters 
### prompt run:
two inbuilt methods have been used here which is input stream read which converts the raw bytes into the readable character and the other one is buffer reader which instead of reading one char at a time it reads whole chunks at a time, which makes it much faster.

* To make it work in prompt run i wrapped the system.in which is the input that we get with the input reader and then that with the buffer reader.

## Tokens
* Tokens has 4 field which is type, lexeme, literal, line.
1. Type- tells us about the token type wether if its a semicolon or a comma or anyother pre registered token
2. lexeme- it is the indivisual letter of the whole word like v in the "var"
3. literal- literal is the the value of the lexeme for eg. "4" the literal will be 4
4. line- line is the int value which gives us the current line number.
