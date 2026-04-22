package com.neusoft.yth.nepBackend.member.mapper;

import com.neusoft.yth.nepBackend.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author YTH
 */
@Mapper
public interface MemberMapper {
    /**
     * 查询所有用户
     * @return
     */
    public List<Member> selectAll();


    /**
     * 注册
     * @param member
     * @return
     */
    int insert(Member member);

    /**
     * 根据用户名查询用户是否存在,返回count
     * @param memNumber
     * @return
     */
    int selectCountByNumber(@Param("memNumber") String memNumber);


    /**
     * 根据用户名查询用户信息
     * @param logInID
     * @return
     */
    Member selectByNumber(@Param("logInID") String logInID);

    /**
     * 修改用户信息
     * @param member
     * @return
     */
    int updateMember(Member member);


    /**
     * 根据代码时间查询用户
     * @param member
     * @return
     */
    int selectCountByTime(Member member);

}
