package top.tlinx.ticket_management.exception;

import lombok.Getter;

import javax.servlet.ServletException;

@Getter
public class GlobalException extends ServletException {
    private Integer code;
    private String msg;
    public GlobalException(Integer code,String msg){
        super(msg);
        this.msg = msg;
        this.code = code;
    }
}
