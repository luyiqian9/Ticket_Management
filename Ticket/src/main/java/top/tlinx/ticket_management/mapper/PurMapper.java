package top.tlinx.ticket_management.mapper;

import org.apache.ibatis.annotations.Insert;
import top.tlinx.ticket_management.pojo.Purchase;

public interface PurMapper {
    @Insert("insert into purchase values(#{pid}, #{uid}, #{tid}, #{status})")
    void insert(Purchase purchase);
}
