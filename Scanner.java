import java.util.List;
import java.util.ArrayList;
public class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    Scanner(String source) {
        this.source = source;

    }
    private boolean isAtEnd() {
        return current >= source.length();
    }
    public List<Token> scanTokens() {
        while(!isAtEnd()) {
            start = current;
            scanToken();
        }
        tokens.add(new Token(TokenType.EOF, "", null, line));
        return tokens;
    }
   
    private void scanToken() {
        char c = advance();
        switch(c) {
        case '(': addToken(TokenType.LEFT_PAREN); break;
        case ')': addToken(TokenType.RIGHT_PAREN); break;
        case '{': addToken(TokenType.LEFT_BRACE); break;
        case '}': addToken(TokenType.RIGHT_BRACE); break;
        case ',': addToken(TokenType.COMMA); break;
        case '.': addToken(TokenType.DOT); break;
        case '-': addToken(TokenType.MINUS); break;
        case '+': addToken(TokenType.PLUS); break;
        case ';': addToken(TokenType.SEMICOLON); break;
        case '*': addToken(TokenType.STAR); break;
        case ' ':
        case '\r':
        case '\t':
            break;
        case '\n':
            line++;
            break;

        case '!':
            addToken(match('=') ? TokenType.BANG_EQUAL : TokenType.BANG); // !=
            break;  
        case '=':
        addToken(match('=') ? TokenType.EQUAL_EQUAL : TokenType.EQUAL);  // ==
        break;
        
        case '<':
        addToken(match('=') ? TokenType.LESS_EQUAL : TokenType.LESS);  // <=
        break;
        
        case '>':
        addToken(match('=') ? TokenType.GREATER_EQUAL : TokenType.GREATER);  // >=  
        break;
        
        case '/':
            if (match('/')) {
                while (peek() != '\n' && !isAtEnd()) advance();
            } else {
                addToken(TokenType.SLASH);
            }
            break;

        case '"':
            if(match('"')) {
                if(peek() == '\n' && !isAtEnd()) {
                     advance();
                } else {
                    if (isAtEnd()) {
                        // Unterminated string
                    } else {
                        if (peek() == '"') {
                        advance();
                        String value = source.substring(start + 1, current - 1);
                        addToken(TokenType.STRING, value);
                   
                }


                }
                }
            
            }
        }
    }
    private char advance() {
        return source.charAt(current++);
    }
    private void addToken(TokenType type) {
        addToken(type, null);
    }
    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start,current);
        tokens.add(new Token(type, text, literal, line));
    }
    private boolean match(char expected) {
        if(isAtEnd()) return false;
        char c = source.charAt(current);
        if(c != expected) return false;
        current++;
        return true;
    }
    private char peek() {
        if (isAtEnd())
            return '\0';
        return source.charAt(current);
    }
}
    

