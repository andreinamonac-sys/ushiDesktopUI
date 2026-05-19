package com.andreina.ushi.desktop.controller;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JOptionPane;

import com.andreina.ushi.dao.criteria.AnimalCriteria;
import com.andreina.ushi.dao.criteria.ParametroCriteria;
import com.andreina.ushi.desktop.MainWindow;
import com.andreina.ushi.desktop.RolePermissions;
import com.andreina.ushi.desktop.renderer.AnimalActionsEditor;
import com.andreina.ushi.desktop.view.AnimalView;
import com.andreina.ushi.desktop.view.AnimalSearchView;
import com.andreina.ushi.model.AnimalDTO;
import com.andreina.ushi.model.GranjaDTO;
import com.andreina.ushi.model.Results;
import com.andreina.ushi.service.AnimalService;
import com.andreina.ushi.service.EventoService;
import com.andreina.ushi.service.GranjaService;
import com.andreina.ushi.service.ParametroService;
import com.andreina.ushi.service.impl.AnimalServiceImpl;
import com.andreina.ushi.service.impl.EventoServiceImpl;
import com.andreina.ushi.service.impl.GranjaServiceImpl;
import com.andreina.ushi.service.impl.ParametroServiceImpl;

public class AnimalSearchController extends AbstractController implements AnimalActionsEditor.AnimalActionHandler {

    private final AnimalSearchView view;
    private final AnimalService animalService;
    private final GranjaService granjaService;
    private final EventoService eventoService;
    private final ParametroService parametroService;

    public AnimalSearchController(AnimalSearchView view) {
        this.view = view;
        this.animalService = new AnimalServiceImpl();
        this.granjaService = new GranjaServiceImpl();
        this.eventoService = new EventoServiceImpl();
        this.parametroService = new ParametroServiceImpl();
        this.view.getBtnBuscar().addActionListener(this);
        this.view.setAnimalActionsHandler(this);
        this.view.setAnimalActionsVisible(canWriteAnimals());
        this.view.getResultadosTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openAnimalFromTable(e);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        buscar();
    }

    private void buscar() {
        try {
            AnimalCriteria criteria = view.getCriteria();
            if (!applyFarmScope(criteria)) {
                view.setModel(new java.util.ArrayList<AnimalDTO>());
                return;
            }
            Results<AnimalDTO> results = animalService.findByCriteria(criteria, 1, 100);
            List<AnimalDTO> resultados = results == null ? null : results.getPageResults();
            view.setModel(resultados);
        } catch (Exception e) {
            view.showError("No se pudo buscar animales: " + e.getMessage());
        }
    }

    private void openAnimalFromTable(MouseEvent e) {
        if (view.getResultadosTable().getSelectedRow() < 0) {
            return;
        }
        int column = view.getResultadosTable().columnAtPoint(e.getPoint());
        if (column == com.andreina.ushi.desktop.model.AnimalTableModel.ACTIONS_COLUMN) {
            return;
        }
        int modelRow = view.getResultadosTable().convertRowIndexToModel(view.getResultadosTable().getSelectedRow());
        AnimalDTO animal = view.getAnimalAt(modelRow);
        if (animal == null) {
            return;
        }
        try {
            AnimalDTO fullAnimal = animalService.findById(animal.getId());
            ParametroCriteria parametroCriteria = new ParametroCriteria();
            parametroCriteria.setAnimalId(animal.getId());
            MainWindow.getInstance().setView(new AnimalView(
                    fullAnimal == null ? animal : fullAnimal,
                    eventoService.findByAnimalId(animal.getId()),
                    parametroService.findByCriteria(parametroCriteria, 0, 100)));
        } catch (Exception ex) {
            view.showError("No se pudo abrir la ficha del animal: " + ex.getMessage());
        }
    }

    @Override
    public void editAnimal(AnimalDTO animal) {
        if (!canWriteAnimals()) {
            view.showError("Tu rol solo permite consultar animales.");
            return;
        }
        if (animal == null) {
            return;
        }
        try {
            AnimalDTO fullAnimal = animalService.findById(animal.getId());
            AnimalView form = new AnimalView();
            form.setAnimal(fullAnimal == null ? animal : fullAnimal);
            int option = JOptionPane.showConfirmDialog(view, form, "Modificar animal", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            if (option != JOptionPane.OK_OPTION) {
                return;
            }
            AnimalDTO updated = form.updateAnimal(fullAnimal == null ? animal : fullAnimal);
            updateGranjaIdFromNif(updated, form.getGranjaNif());
            animalService.update(updated);
            buscar();
        } catch (Exception e) {
            view.showError("No se pudo modificar el animal: " + e.getMessage());
        }
    }

    private void updateGranjaIdFromNif(AnimalDTO animal, String granjaNif) throws Exception {
        if (granjaNif == null) {
            return;
        }
        GranjaDTO granja = granjaService.findByNif(granjaNif);
        if (granja == null || granja.getId() == null) {
            throw new IllegalArgumentException("No existe ninguna granja con NIF " + granjaNif);
        }
        animal.setGranjaId(granja.getId());
        animal.setGranjaNif(granja.getNif());
    }

    @Override
    public void deleteAnimal(AnimalDTO animal) {
        if (!canWriteAnimals()) {
            view.showError("Tu rol solo permite consultar animales.");
            return;
        }
        if (animal == null || animal.getId() == null) {
            return;
        }
        int option = JOptionPane.showConfirmDialog(view,
                "Quieres borrar el animal " + animal.getNumRegistro() + "?",
                "Borrar animal", JOptionPane.YES_NO_OPTION);
        if (option != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            animalService.delete(animal.getId());
            buscar();
        } catch (Exception e) {
            view.showError("No se pudo borrar el animal: " + e.getMessage());
        }
    }

    private boolean applyFarmScope(AnimalCriteria criteria) {
        String role = MainWindow.getInstance().getCurrentRoleName();
        if (!RolePermissions.isFarmScoped(role)) {
            return true;
        }
        Long selectedGranjaId = MainWindow.getInstance().getSelectedGranjaId();
        if (selectedGranjaId == null) {
            view.showError("Selecciona una granja para consultar los animales de tu rol.");
            return false;
        }
        criteria.setGranjaId(selectedGranjaId);
        return true;
    }

    private boolean canWriteAnimals() {
        return RolePermissions.canWriteAnimals(MainWindow.getInstance().getCurrentRoleName());
    }
}
