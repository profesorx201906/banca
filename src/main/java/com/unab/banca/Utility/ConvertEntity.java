package com.unab.banca.Utility;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConvertEntity {
    @Autowired
    ModelMapper modelMapper;
    public Object convert(Object obj,Object objDto){
        System.out.println(objDto.getClass());
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
                objDto = modelMapper.map(obj, objDto.getClass());
        return objDto;
    }
}
