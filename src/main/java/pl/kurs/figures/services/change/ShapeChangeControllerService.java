package pl.kurs.figures.services.change;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.kurs.figures.dto.ShapeChangeDTO;
import pl.kurs.figures.model.ShapeChange;
import pl.kurs.figures.security.entity.User;
import pl.kurs.figures.security.service.AuthenticationService;

import java.nio.file.AccessDeniedException;

@Service
@AllArgsConstructor
public class ShapeChangeControllerService {
    private ShapeChangeService shapeChangeService;
    private AuthenticationService authenticationService;
    private ModelMapper modelMapper;

    public ShapeChangeDTO getChangeById(long id) throws AccessDeniedException {
        User currentUser = authenticationService.getAuthenticatedUser();

        ShapeChange changeById = shapeChangeService.getChangeById(id);
        Long changeId = changeById.getShape().getId();

        if (currentUser.getUsername().equals(changeById.getShape().getCreatedBy()) || currentUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {

            ShapeChangeDTO changeDTO = modelMapper.map(changeById, ShapeChangeDTO.class);
            changeDTO.setShapeId(changeId);
            return changeDTO;

        } else {
            throw new AccessDeniedException("You do not have permission to display this change.");
        }
    }
}
