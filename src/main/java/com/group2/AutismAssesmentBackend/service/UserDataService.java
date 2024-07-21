package com.group2.AutismAssesmentBackend.service;

import com.group2.AutismAssesmentBackend.dtos.UserDataToModelDto;
import com.group2.AutismAssesmentBackend.entities.UserDataEntity;
import com.group2.AutismAssesmentBackend.mappers.Mapper;
import com.group2.AutismAssesmentBackend.repository.UserDataRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserDataService {
    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Mapper<UserDataEntity, UserDataToModelDto> userDataToModelMapper;

    public UserDataEntity processAndSaveUserData(UserDataEntity userData){
        //map userdata here
        UserDataEntity originalData = copyUserData(userData);
        transformDataForModel(userData);
        Long response = callAiModel(userData);
        originalData.setResult(Long.valueOf(response.toString()));
        originalData.setToken(UUID.randomUUID().toString());
        UserDataEntity savedEntity = userDataRepository.save(originalData);
        return savedEntity;
    }

    private UserDataEntity copyUserData(UserDataEntity userData){
        UserDataEntity copy = new UserDataEntity();
        copy.setA1(userData.getA1());
        copy.setA2(userData.getA2());
        copy.setA3(userData.getA3());
        copy.setA4(userData.getA4());
        copy.setA5(userData.getA5());
        copy.setA6(userData.getA6());
        copy.setA7(userData.getA7());
        copy.setA8(userData.getA8());
        copy.setA9(userData.getA9());
        copy.setA10(userData.getA10());
        copy.setAge_Months(userData.getAge_Months());
        copy.setSex(userData.getSex());
        copy.setEthnicity(userData.getEthnicity());
        copy.setJaundice(userData.getJaundice());
        copy.setFamily_mem_with_ASD(userData.getFamily_mem_with_ASD());
        copy.setWho_completed_the_test(userData.getWho_completed_the_test());
        return copy;
    }

    private void transformDataForModel(UserDataEntity userData){
        userData.setA1((long) (userData.getA1() != null && (userData.getA1()) < 3 ? 1 : 0));
        userData.setA2((long) (userData.getA2() != null && userData.getA2() < 3 ? 1 : 0));
        userData.setA3((long) (userData.getA3() != null && userData.getA3() < 3 ? 1 : 0));
        userData.setA4((long) (userData.getA4() != null && userData.getA4() < 3 ? 1 : 0));
        userData.setA5((long) (userData.getA5() != null && userData.getA5() < 3 ? 1 : 0));
        userData.setA6((long) (userData.getA6() != null && userData.getA6() < 3 ? 1 : 0));
        userData.setA7((long) (userData.getA7() != null && userData.getA7() < 3 ? 1 : 0));
        userData.setA8((long) (userData.getA8() != null && userData.getA8() < 3 ? 1 : 0));
        userData.setA9((long) (userData.getA9() != null && userData.getA9() < 3 ? 1 : 0));
        userData.setA10((long) (userData.getA10() != null && userData.getA10() > 1 ? 1 : 0));
    }


    private Long callAiModel(UserDataEntity transformedData){
        String url = "http://localhost:8000/predict";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("A1", transformedData.getA1());
        requestBody.put("A2", transformedData.getA2());
        requestBody.put("A3", transformedData.getA3());
        requestBody.put("A4", transformedData.getA4());
        requestBody.put("A5", transformedData.getA5());
        requestBody.put("A6", transformedData.getA6());
        requestBody.put("A7", transformedData.getA7());
        requestBody.put("A8", transformedData.getA8());
        requestBody.put("A9", transformedData.getA9());
        requestBody.put("A10", transformedData.getA10());
        requestBody.put("Age_Months", transformedData.getAge_Months());
        requestBody.put("Sex", transformedData.getSex());
        requestBody.put("Ethnicity", transformedData.getEthnicity());
        requestBody.put("Jaundice", transformedData.getJaundice());
        requestBody.put("Family_mem_with_ASD", transformedData.getFamily_mem_with_ASD());
        requestBody.put("Who_completed_the_test", transformedData.getWho_completed_the_test());

        HttpEntity<Map<String,Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);

        Map<String, Object> responseBody = response.getBody();
        Double predictionDouble = (Double) responseBody.get("prediction");
        predictionDouble = predictionDouble*100;
        long prediction = predictionDouble.longValue();

        return prediction;
    }

    public UserDataEntity findByToken(String token){
        return userDataRepository.findByToken(token).orElseThrow();
    }

}
