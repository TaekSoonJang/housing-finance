package com.jeanvar.housingfinance.service;

import com.jeanvar.housingfinance.core.Institute;
import com.jeanvar.housingfinance.repository.InstituteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InstituteService {
    private InstituteRepository instituteRepository;

    public List<Institute> getAllInstitutes() {
        return instituteRepository.findAll();
    }
}
