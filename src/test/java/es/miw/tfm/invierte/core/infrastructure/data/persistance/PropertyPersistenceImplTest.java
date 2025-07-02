package es.miw.tfm.invierte.core.infrastructure.data.persistence;

import es.miw.tfm.invierte.core.domain.model.Property;
import es.miw.tfm.invierte.core.infrastructure.data.dao.PropertyRepository;
import es.miw.tfm.invierte.core.infrastructure.data.entity.PropertyEntity;
import es.miw.tfm.invierte.core.infrastructure.data.entity.SubProjectPropertyGroupEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PropertyPersistenceImplTest {

  private PropertyRepository propertyRepository;

  private PropertyPersistenceImpl propertyPersistence;

  @BeforeEach
  void setUp() {
    propertyRepository = mock(PropertyRepository.class);
    propertyPersistence = new PropertyPersistenceImpl(propertyRepository);
  }

  @Test
  void create_shouldSaveProperty() {
    Property property = mock(Property.class);
    PropertyEntity entity = mock(PropertyEntity.class);
    Property expected = new Property();

    when(propertyRepository.save(any(PropertyEntity.class))).thenReturn(entity);
    when(entity.toProperty()).thenReturn(expected);

    Mono<Property> result = propertyPersistence.create(property, 5);

    StepVerifier.create(result)
        .expectNext(expected)
        .verifyComplete();

    verify(propertyRepository).save(any(PropertyEntity.class));
    verify(entity).toProperty();
  }

  @Test
  void findBySubProjectPropertyGroupId_shouldReturnProperties() {
    int groupId = 3;
    PropertyEntity entity1 = mock(PropertyEntity.class);
    PropertyEntity entity2 = mock(PropertyEntity.class);
    Property property1 = new Property();
    Property property2 = new Property();

    when(propertyRepository.findBySubProjectPropertyGroupId(groupId)).thenReturn(List.of(entity1, entity2));
    when(entity1.toProperty()).thenReturn(property1);
    when(entity2.toProperty()).thenReturn(property2);

    Flux<Property> result = propertyPersistence.findBySubProjectPropertyGroupId(groupId);

    StepVerifier.create(result)
        .expectNext(property1)
        .expectNext(property2)
        .verifyComplete();

    verify(propertyRepository).findBySubProjectPropertyGroupId(groupId);
    verify(entity1).toProperty();
    verify(entity2).toProperty();
  }

  @Test
  void delete_shouldSetGroupToNullAndDeleteEntity() {
    Property property = new Property();
    PropertyEntity propertyEntity = new PropertyEntity(property);

    when(propertyRepository.save(any(PropertyEntity.class))).thenReturn(propertyEntity);
    doNothing().when(propertyRepository).delete(any(PropertyEntity.class));

    Mono<Void> result = propertyPersistence.delete(property);

    StepVerifier.create(result)
        .verifyComplete();

    verify(propertyRepository).save(argThat(entity -> entity.getSubProjectPropertyGroup() == null));
    verify(propertyRepository).delete(any(PropertyEntity.class));
  }

}