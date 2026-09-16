# Interpreter

Building an interpreter with the help of the book *Crafting Interpreters*, adding some personal touches along the way. This interpreter is based on **jlox** — Lox, implemented in Java.

> **Note:** the explanations below are based on my own understanding from the book and further research.

## Scanner

The first part of the interpreter is the **scanner** (also known as the *lexical analyzer*). Its job is to turn raw source code into a flat list of tokens.

### How the scanner starts

The scanner supports two modes, both driven from `lox.java`:
- **File mode** — reads and scans an entire `.lox` file at once
- **Prompt mode (REPL)** — reads and scans one line at a time, interactively, from the terminal

Prompt mode is stopped with **Ctrl+D**. Pressing Ctrl+D signals "end of input" to the terminal, which closes the input stream — `readLine()` then returns `null`, which the REPL loop checks for to stop reading further lines.

### File reader

Using `Charset.defaultCharset()`, the raw bytes read from the file are decoded into readable characters (a `String`).

### Prompt reader

Two built-in classes are used here:
- `InputStreamReader` — converts the raw bytes coming from `System.in` into readable characters
- `BufferedReader` — wraps the `InputStreamReader` and reads in larger chunks rather than one character at a time, which is both more convenient (`readLine()`) and more efficient

To make this work, `System.in` is wrapped in an `InputStreamReader`, which is then wrapped in a `BufferedReader`.

## Tokens

A `Token` has 4 fields: `type`, `lexeme`, `literal`, and `line`.

1. **type** — the token's category, e.g. `SEMICOLON`, `COMMA`, or any other pre-registered `TokenType`
2. **lexeme** — the raw text of the *entire* token, e.g. the whole word `var`, not a single letter
3. **literal** — the actual value behind a literal token, e.g. for the text `"4"`, the literal is the number `4`
4. **line** — the line number the token was found on

## `scanToken()`

`scanToken()` is the heart of the scanner — this is where the actual recognition happens. It checks for reserved symbols like `(`, `)`, and multi-character cases like `==`, and also identifies more complex token types (`NUMBER`, `STRING`, `IDENTIFIER`) with the help of several helper methods: `peek()`, `peekNext()`, `isDigit()`, `isAlpha()`, and others.

### `TokenType.STRING`

To scan a string, the scanner consumes characters until it finds the closing double quote (`"`). The lexeme's actual value is then extracted using `start + 1` and `current - 1` (trimming off the surrounding quotes), and that value is stored as the token's literal.

### `TokenType.NUMBER`

`isDigit()` checks whether a character falls between `0` and `9`. If so, `number()` takes over: it consumes all the leading digits, and if it then finds a `.`, it uses `peekNext()` to look one character *past* the dot — this way, the decimal point is only consumed if there's actually a digit after it (avoiding accidentally consuming a `.` that isn't part of a number). `Double.parseDouble()` is then used to convert the matched text into a primitive `double`.

### `TokenType.IDENTIFIER`

If the character isn't a digit, the scanner checks whether it's alphabetic (`a–z`, `A–Z`, or `_`). If so, it scans the rest of the word as long as characters remain alphanumeric, then looks the resulting word up in the `keywords` map. If it matches a reserved word, it's classified as that specific keyword; otherwise, it's a generic `IDENTIFIER`.

# AST:

