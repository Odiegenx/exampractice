package healthStore.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import healthStore.DOA.HealthProductDAO;
import healthStore.DOA.HealthProductDAOMock;
import healthStore.DOA.iDAO;
import healthStore.DTO.HealthProductDTO;
import healthStore.exceptions.ApiException;
import healthStore.exceptions.ExceptionHandler;
import healthStore.system.Logger;
import healthStore.system.Populator;
import io.javalin.http.Handler;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class HealthProductController implements IHealthProductController {

    private static HealthProductController instance;
    private static iDAO healthDAO;
    //private static HealthProductDAO healthDAO;

    /**
     * An ObjectMapper used for mapping between different object types.
     * It is configured to not fail on empty beans and to support Java Time objects.
     */
    @SuppressWarnings({"FieldMayBeFinal", "unused"})
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static HealthProductController getInstance(boolean isMemory, boolean isTest) {
        if (instance == null) {
            instance = new HealthProductController();
            if(isMemory) {
                healthDAO = HealthProductDAOMock.getInstance();
            }
            if(!isMemory) {
                healthDAO = HealthProductDAO.getInstance(isTest);
            }
            Populator.populateDatabase(isTest);
        }
        return instance;
    }

    @Override
    public Handler getAll() {
        return ctx -> {
            List<HealthProductDTO> healthProductDTOList = null;
            try {
                healthProductDTOList = healthDAO.getAll();
                ctx.json(healthProductDTOList);
            } catch (ApiException e) {
                Logger.exceptionLog(e.getStatusCode(),e.toString());
                ctx.json(ExceptionHandler.exceptionHandler(e));
            }
        };
    }

    @Override
    public Handler getById() {
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            try {
                Object healthProductDTO = healthDAO.getById(id);
                ctx.json(healthProductDTO);
            }catch (ApiException e){
                Logger.exceptionLog(e.getStatusCode(),e.toString());
                ctx.json(ExceptionHandler.exceptionHandler(e));
            }
        };
    }

    public Handler getByCategory() {
        return ctx -> {
            String category = ctx.pathParam("category");
            try {
                List<HealthProductDTO> healthProductDTOList = healthDAO.getByCategory(category);
                ctx.json(healthProductDTOList);
            }catch (ApiException e){
                Logger.exceptionLog(e.getStatusCode(),e.toString());
                ctx.json(ExceptionHandler.exceptionHandler(e));
            }
        };
    }

    @Override
    public Handler create() {
        return ctx -> {
            HealthProductDTO toCreate = ctx.bodyAsClass(HealthProductDTO.class);
            try {
                HealthProductDTO created = (HealthProductDTO) healthDAO.create(toCreate);
                ctx.json(created);
            }catch (ApiException e){
                Logger.exceptionLog(e.getStatusCode(),e.toString());
                ctx.json(ExceptionHandler.exceptionHandler(e));
            }
        };
    }

    @Override
    public Handler update() {
        return ctx -> {
            int idToUpdate = Integer.valueOf(ctx.pathParam("id"));
            HealthProductDTO toUpdate = ctx.bodyAsClass(HealthProductDTO.class);
            HealthProductDTO updated = null;
            try {
                updated = (HealthProductDTO) healthDAO.update(toUpdate,idToUpdate);
                ctx.json(updated);
            } catch (ApiException e) {
                Logger.exceptionLog(e.getStatusCode(),e.toString());
                ctx.json(ExceptionHandler.exceptionHandler(e));
            }
        };
    }

    @Override
    public Handler delete() {
        return ctx -> {
            String category = ctx.pathParam("id");
            try {
                List<HealthProductDTO> healthProductDTOList = healthDAO.getByCategory(category);
                ctx.json(healthProductDTOList);
            }catch (ApiException e){
                Logger.exceptionLog(e.getStatusCode(),e.toString());
                ctx.json(ExceptionHandler.exceptionHandler(e));
            }
        };
    }
}
