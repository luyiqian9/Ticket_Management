package top.tlinx.ticket_management.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import top.tlinx.ticket_management.pojo.Train;

import java.util.List;

public interface TrainMapper {
    @Insert("insert into train values(#{tid}, #{load}, #{remainTicks}," +
            "#{startStation}, #{endStation}," +
            "#{startTime}, #{endTime}, #{runtime})")
    void insert(Train train);


    @Update("delete from train where tid = #{tid}")
    void delete(int tid);
    @Update("update train set `load`=#{load}, remainTicks=#{remainTicks}," +
            "startStation=#{startStation}, endStation=#{endStation}," +
            "startTime=#{startTime}, endTime=#{endTime}, runtime=#{runtime} " +
            "where tid=#{tid}")
    void update(Train train);


    @Select("select * from train where tid = #{tid}")
    Train selectByid(int tid);
    @Select("select * from train")
    List<Train> query();
    @Select("select startStation, endStation from train where tid = #{tid}")
    List<Train> queryById(int tid);
    @Select("select startStation, endStation from train where endStation = #{endStation}")
    List<Train> queryByEd(String endStation);

}
