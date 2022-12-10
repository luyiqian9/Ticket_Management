package top.tlinx.ticket_management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {
    private Integer pid;
    private Integer uid;
    private Integer tid;
    private Integer status;
}
