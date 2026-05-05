package com.andreina.ushi.desktop.controller;

import java.util.List;

import com.andreina.ushi.dao.criteria.AnimalCriteria;
import com.andreina.ushi.desktop.view.AnimalSearchView;
import com.andreina.ushi.model.AnimalDTO;
import com.andreina.ushi.service.AnimalService;
import com.andreina.ushi.service.impl.AnimalServiceImpl;

public class AnimalSearchController extends AbstractController implements ActionListener {

    private final AnimalSearchView view;
    private final AnimalService animalService;
    

    public AnimalSearchController(AnimalSearchView view) {
        this.view = view;
        this.animalService = new AnimalServiceImpl();
        
    }

   

    private void buscar() {
        AnimalCriteria criteria = view.getCriteria();
        List<AnimalDTO> resultados = animalService.findByCriteria(criteria, from, pageSize);
        view.setModel(resultados);
    }

   
   
}

