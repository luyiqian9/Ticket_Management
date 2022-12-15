package top.tlinx.ticket_management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer uid;      // 用户id
    private String username;   // 用户名
    private String password;   // 用户密码
    private Integer limit;    // 用户权限

}
