
package com.torrent;
/***
 * Ошибка, возникающая при неверно введенных аргументах
 */
public class BadArgumentsException extends Exception{

    public BadArgumentsException() {
        super("Bad arguments");
    }
    
}
