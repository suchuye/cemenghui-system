package cn.neu.edu.cemenghuisystem.mapper;

import cn.neu.edu.cemenghuisystem.pojo.LoginUserInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginUserInfoMapper {

    @Insert("""
    INSERT INTO public.LOGIN_USER_INFO (
        account,password, user_Type, role, avatar, status, 
        company_name, company_contact, nickname, 
        last_login_time, phone_number, email
    ) VALUES (
        #{loginUserInfo.account},#{password}, #{loginUserInfo.userType}, #{loginUserInfo.role}, #{loginUserInfo.avatar}, #{loginUserInfo.status},
        #{loginUserInfo.companyName}, #{loginUserInfo.companyContact}, #{loginUserInfo.nickname},
        #{loginUserInfo.lastLoginAt}, #{loginUserInfo.phoneNumber}, #{loginUserInfo.email}
    )
""")
    @Options(useGeneratedKeys = true, keyProperty = "loginUserInfo.id", keyColumn = "id")
    public boolean insertUserInfo(LoginUserInfo loginUserInfo,String password);



    @Results(id = "loginInfoMap",value = {
            @Result(property = "userType",column = "user_Type"),
            @Result(property ="companyName",column = "company_name"),
            @Result(property = "companyContact",column = "company_contact"),
            @Result(property = "createdAt",column = "create_time"),
            @Result(property = "lastLoginAt",column = "last_login_time"),
            @Result(property = "phoneNumber",column = "phone_number")
    })
    @Select("select * from public.LOGIN_USER_INFO where id=#{id}")
    LoginUserInfo getUserInfoById(Integer id);

    @Select("select * from public.LOGIN_USER_INFO where account=#{account}")
    @ResultMap("loginInfoMap")
    LoginUserInfo getUserInfoByAccount(String account);

    @Select("select password from public.LOGIN_USER_INFO where id=#{id}")
    String getPasswordByUserId(Integer userId);

    @Update("""
        UPDATE public.LOGIN_USER_INFO 
        SET account = #{account},
            user_Type = #{userType},
            role = #{role},
            avatar = #{avatar},
            status = #{status},
            company_name = #{companyName},
            company_contact = #{companyContact},
            nickname = #{nickname},
            last_login_time = #{lastLoginAt},
            phone_number = #{phoneNumber},
            email = #{email}
        WHERE id = #{id}
    """)
    public boolean updateUserInfo(LoginUserInfo loginUserInfo);




}
