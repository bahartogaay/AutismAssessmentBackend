package com.group2.AutismAssesmentBackend.mappers.UserDataToModelMapperImpl;

import com.group2.AutismAssesmentBackend.dtos.UserDataToModelDto;
import com.group2.AutismAssesmentBackend.entities.UserDataEntity;
import com.group2.AutismAssesmentBackend.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDataToModelMapperImpl implements Mapper<UserDataEntity, UserDataToModelDto> {
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDataToModelDto mapTo(UserDataEntity userData) {
        return modelMapper.map(userData, UserDataToModelDto.class);
    }

    @Override
    public UserDataEntity mapFrom(UserDataToModelDto userDataToModelDto) {
        return modelMapper.map(userDataToModelDto, UserDataEntity.class);
    }
}
