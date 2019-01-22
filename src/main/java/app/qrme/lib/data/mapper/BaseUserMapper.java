package app.qrme.lib.data.mapper;

import app.qrme.lib.data.dto.UserDTO;
import app.qrme.lib.data.entity.BaseUser;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface BaseUserMapper {
    UserDTO map(BaseUser baseUser);
    List<UserDTO> map(List<BaseUser> baseUserList);
}
