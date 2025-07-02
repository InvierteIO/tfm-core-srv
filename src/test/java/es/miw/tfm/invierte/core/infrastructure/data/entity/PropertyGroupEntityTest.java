package es.miw.tfm.invierte.core.infrastructure.data.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import es.miw.tfm.invierte.core.domain.model.Feature;
import es.miw.tfm.invierte.core.domain.model.HouseFloorArea;
import es.miw.tfm.invierte.core.domain.model.PropertyFeature;
import es.miw.tfm.invierte.core.domain.model.PropertyGroup;
import es.miw.tfm.invierte.core.domain.model.enums.Flag;
import es.miw.tfm.invierte.core.domain.model.enums.PropertyCategory;
import es.miw.tfm.invierte.core.domain.model.enums.RoadWay;
import es.miw.tfm.invierte.core.domain.model.enums.TowerLocation;
import org.junit.jupiter.api.Test;

class PropertyGroupEntityTest {

  @Test
  void shouldBuildAndGetFields() {
    PropertyGroupEntity entity = PropertyGroupEntity.builder()
        .id(1)
        .name("Group Name")
        .build();

    assertEquals(1, entity.getId());
    assertEquals("Group Name", entity.getName());
  }

  @Test
  void shouldSetAndGetFields() {
    PropertyGroupEntity entity = new PropertyGroupEntity();
    entity.setId(2);
    entity.setName("Another Group");
    assertEquals(2, entity.getId());
    assertEquals("Another Group", entity.getName());
  }

  @Test
  void shouldConstructHouseFromDomainModel() {

    Feature feature = new Feature();
    feature.setId(1);
    feature.setName("Feature Name");

    PropertyFeature propertyFeature = new PropertyFeature();
    propertyFeature.setId(1);
    propertyFeature.setFeatureValue(Flag.YES);
    propertyFeature.setFeature(feature);

    HouseFloorArea houseFloorArea = new HouseFloorArea();
    houseFloorArea.setId(1);
    houseFloorArea.setArea(140.0);
    houseFloorArea.setNumber(0);

    PropertyGroup domain = new PropertyGroup();
    domain.setId(3);
    domain.setName("Domain Group");
    domain.setFrontPark(Flag.YES);
    domain.setParkingSpace(Flag.NO);
    domain.setRoofedArea(1);
    domain.setPropertyFeatures(List.of(propertyFeature));
    domain.setPropertyCategory(PropertyCategory.HOUSE);
    domain.setHouseFloorAreas(List.of(houseFloorArea));

    PropertyGroupEntity entity = new PropertyGroupEntity(domain);

    assertEquals(domain.getId(), entity.getId());
    assertEquals(domain.getName(), entity.getName());
    assertEquals(Flag.YES, domain.getFrontPark());
    assertEquals(Flag.NO, domain.getParkingSpace());
    assertEquals(Boolean.FALSE, entity.isParkingSpace());
    assertEquals(Boolean.TRUE, entity.isFrontPark());
    assertEquals(1, domain.getPropertyFeatures().size());
    assertEquals(1, domain.getHouseFloorAreas().size());
    assertEquals(domain.getRoofedArea(), entity.getHouse().getRoofedArea());


    assertEquals(1, entity.getPropertyGroupFeatureEntities().size());
    assertNotNull(entity.getPropertyGroupFeatureEntities().getFirst().getFeature());
    assertEquals(propertyFeature.getFeatureValue(),entity.getPropertyGroupFeatureEntities().getFirst().getFeatureValue());
    assertEquals(propertyFeature.getFeature().getId(),entity.getPropertyGroupFeatureEntities().getFirst().getFeature().getId());
    assertNotNull(entity.getPropertyGroupFeatureEntities().getFirst().getPropertyGroup());
    assertNotNull(entity.getHouse().getHouseFloorAreaEntities().getFirst().getHouse());
    assertNull(entity.getHouse().getHouseFloorAreaEntities().getFirst().getId());
    assertNotNull(entity.getHouse());
    assertEquals(1, entity.getHouse().getHouseFloorAreaEntities().size());

  }

  @Test
  void shouldConstructApartmentFromDomainModel() {

    Feature feature = new Feature();
    feature.setId(1);
    feature.setName("Feature Name");

    PropertyFeature propertyFeature = new PropertyFeature();
    propertyFeature.setId(1);
    propertyFeature.setFeatureValue(Flag.YES);
    propertyFeature.setFeature(feature);

    PropertyGroup domain = new PropertyGroup();
    domain.setId(3);
    domain.setName("Domain Group");
    domain.setFrontPark(Flag.YES);
    domain.setParkingSpace(Flag.NO);
    domain.setPropertyFeatures(List.of(propertyFeature));
    domain.setPropertyCategory(PropertyCategory.APARTMENT);
    domain.setRoofedArea(1);
    domain.setTowerLocation(TowerLocation.EXTERIOR);

    PropertyGroupEntity entity = new PropertyGroupEntity(domain);

    assertEquals(domain.getId(), entity.getId());
    assertEquals(domain.getName(), entity.getName());
    assertEquals(Flag.YES, domain.getFrontPark());
    assertEquals(Flag.NO, domain.getParkingSpace());
    assertEquals(Boolean.FALSE, entity.isParkingSpace());
    assertEquals(Boolean.TRUE, entity.isFrontPark());
    assertEquals(PropertyCategory.APARTMENT, domain.getPropertyCategory());
    assertEquals(1, domain.getPropertyFeatures().size());
    assertEquals(1, domain.getPropertyFeatures().size());
    assertEquals(0, domain.getHouseFloorAreas().size());

    assertEquals(1, entity.getPropertyGroupFeatureEntities().size());
    assertNull(entity.getPropertyGroupFeatureEntities().getFirst().getId());
    assertEquals(propertyFeature.getFeatureValue(),entity.getPropertyGroupFeatureEntities().getFirst().getFeatureValue());
    assertNotNull(entity.getPropertyGroupFeatureEntities().getFirst().getPropertyGroup());
    assertEquals(domain.getTowerLocation(), entity.getApartment().getTowerLocation());
    assertNull( entity.getApartment().getId());
    assertNotNull(entity.getApartment());


  }

  @Test
  void shouldConstructLandFromDomainModel() {

    Feature feature = new Feature();
    feature.setId(1);
    feature.setName("Feature Name");

    PropertyFeature propertyFeature = new PropertyFeature();
    propertyFeature.setId(1);
    propertyFeature.setFeatureValue(Flag.YES);
    propertyFeature.setFeature(feature);

    PropertyGroup domain = new PropertyGroup();
    domain.setId(3);
    domain.setName("Domain Group");
    domain.setFrontPark(Flag.YES);
    domain.setParkingSpace(Flag.NO);
    domain.setPropertyFeatures(List.of(propertyFeature));
    domain.setPropertyCategory(PropertyCategory.LAND);
    domain.setRoadWay(RoadWay.AVENUE);
    domain.setRoofedArea(1);

    PropertyGroupEntity entity = new PropertyGroupEntity(domain);

    assertEquals(domain.getId(), entity.getId());
    assertEquals(domain.getName(), entity.getName());
    assertEquals(Flag.YES, domain.getFrontPark());
    assertEquals(Flag.NO, domain.getParkingSpace());
    assertEquals(Boolean.FALSE, entity.isParkingSpace());
    assertEquals(Boolean.TRUE, entity.isFrontPark());
    assertEquals(PropertyCategory.LAND, domain.getPropertyCategory());
    assertEquals(1, domain.getPropertyFeatures().size());
    assertEquals(1, domain.getPropertyFeatures().size());
    assertEquals(0, domain.getHouseFloorAreas().size());

    assertEquals(1, entity.getPropertyGroupFeatureEntities().size());
    assertNull(entity.getPropertyGroupFeatureEntities().getFirst().getId());
    assertEquals(propertyFeature.getFeatureValue(),entity.getPropertyGroupFeatureEntities().getFirst().getFeatureValue());
    assertNotNull(entity.getPropertyGroupFeatureEntities().getFirst().getPropertyGroup());
    assertNotNull(entity.getLand());
    assertEquals(domain.getRoadWay(), entity.getLand().getRoadWay());
    assertNull( entity.getLand().getId());

  }

  @Test
  void shouldConvertToPropertyGroupWithAllFields() {
    PropertyGroupEntity entity = new PropertyGroupEntity();
    entity.setId(4);
    entity.setName("Full Group");
    entity.setFrontPark(true);
    entity.setParkingSpace(false);

    ApartmentEntity apartmentEntity = new ApartmentEntity();
    apartmentEntity.setId(2);
    apartmentEntity.setTotalRooms(2);
    entity.setApartment(apartmentEntity);

    PropertyGroup group = entity.toPropertyGroup();

    assertNotNull(group);
    assertEquals(entity.getId(), group.getId());
    assertEquals(entity.getName(), group.getName());
    assertEquals(apartmentEntity.getTotalRooms(), group.getTotalRooms());


  }

}