package com.zzyl.mapper;

import com.zzyl.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {
    @Select("select * from member where open_id = #{openId} ")
    Member getByOpenId(String openId);

    void updateById(Member member);

    void insert(Member member);
}
