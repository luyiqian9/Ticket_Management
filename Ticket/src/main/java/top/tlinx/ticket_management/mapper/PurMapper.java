package top.tlinx.ticket_management.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import top.tlinx.ticket_management.pojo.Purchase;
import top.tlinx.ticket_management.pojo.Train;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public interface PurMapper {
    @Insert("insert into purchase values(#{pid}, #{uid}, #{tid}, #{status})")
    void insert(Purchase purchase);

    @Select("select t.*, p.status, p.pid from purchase p, train t where p.uid = #{uid} and p.tid = t.tid")
    List<HashMap<String, Objects>> select(int uid);

    @Update("update purchase set status = 0 where pid = #{pid}")
    void update(int pid);
}
