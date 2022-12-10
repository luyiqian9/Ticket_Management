package top.tlinx.ticket_management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Train {
    private Integer tid;  // 班车号
    private Integer load;   // 载客量
    private Integer remainTicks;  // 余票量
    private String routine;  // 路线
    private Date start;   // 发车时间
    private Date end;     // 到站时间
    private String runtime;  // 行车时间
}
