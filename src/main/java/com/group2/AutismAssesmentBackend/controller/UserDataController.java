package com.group2.AutismAssesmentBackend.controller;

import com.group2.AutismAssesmentBackend.dtos.UserDataToModelDto;
import com.group2.AutismAssesmentBackend.entities.UserDataEntity;
import com.group2.AutismAssesmentBackend.mappers.Mapper;
import com.group2.AutismAssesmentBackend.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserDataController {
    @Autowired
    private UserDataService userDataService;

    @Autowired
    private Mapper<UserDataEntity, UserDataToModelDto> mapper;

    @RequestMapping(method= RequestMethod.POST,value= "/process", consumes= MediaType.ALL_VALUE )
    public ResponseEntity<UserDataEntity> processUserData(@RequestBody UserDataToModelDto userData){
        UserDataEntity data = mapper.mapFrom(userData);
        UserDataEntity savedData = userDataService.processAndSaveUserData(data);
        return new ResponseEntity<>(savedData, HttpStatus.CREATED);
    }

    @GetMapping(path = "/userdata/{token}")
    public ResponseEntity<UserDataEntity> getByToken(@PathVariable("token") String token){
        UserDataEntity userData = userDataService.findByToken(token);
        return new ResponseEntity<>(userData, HttpStatusCode.valueOf(200));
    }

}
