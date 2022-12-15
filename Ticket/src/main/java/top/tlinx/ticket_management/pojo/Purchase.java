package top.tlinx.ticket_management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {
    private Integer pid;  // 主键
    private Integer uid;  // 用户id
    private Integer tid;  // 班车号
    private Integer status;  // value = 1 购票  ||   value = 0  退票
}
