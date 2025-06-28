package es.miw.tfm.invierte.core.domain.service;

import es.miw.tfm.invierte.core.domain.exception.NotFoundException;
import es.miw.tfm.invierte.core.domain.model.Project;
import es.miw.tfm.invierte.core.domain.model.ProjectStage;
import es.miw.tfm.invierte.core.domain.model.PropertyGroup;
import es.miw.tfm.invierte.core.domain.model.SubProjectPropertyGroup;
import es.miw.tfm.invierte.core.domain.persistence.ProjectPersistence;
import es.miw.tfm.invierte.core.domain.persistence.PropertyGroupPersistence;
import es.miw.tfm.invierte.core.domain.persistence.SubProjectPersistence;
import es.miw.tfm.invierte.core.domain.persistence.SubProjectPropertyGroupPersistence;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service for handling operations related to property groups and their
 * associations with subprojects.
 * Coordinates persistence and validation logic for property group creation
 * and linking to subprojects.
 *
 * @author denilssonmn
 */
@Service
@RequiredArgsConstructor
public class PropertyGroupService {

  private final ProjectPersistence projectPersistence;

  private final SubProjectPersistence subProjectPersistence;

  private final PropertyGroupPersistence propertyGroupPersistence;

  private final SubProjectPropertyGroupPersistence subProjectPropertyGroupPersistence;

  /**
   * Creates and persists a list of {@link SubProjectPropertyGroup} associations.
   * For each association, ensures the subproject exists, creates the property group,
   * and links it to the subproject.
   *
   * @param subProjectPropertyGroupList the list of associations to create
   * @return a {@link Flux} emitting the created associations
   * @throws NotFoundException if a referenced subproject does not exist
   */
  public Flux<SubProjectPropertyGroup> create(
      @Valid List<SubProjectPropertyGroup> subProjectPropertyGroupList) {

    Map<PropertyGroup, List<ProjectStage>> stagesByPropertyGroup =
        subProjectPropertyGroupList.stream()
            .filter(spg -> spg.getPropertyGroup() != null && spg.getPropertyGroup().getId() == null)
            .collect(Collectors.groupingBy(
                SubProjectPropertyGroup::getPropertyGroup,
                Collectors.mapping(SubProjectPropertyGroup::getStage, Collectors.toList())
            ));

    return Flux.fromIterable(stagesByPropertyGroup.entrySet())
        .flatMap(entry ->
            this.propertyGroupPersistence.create(entry.getKey())
                .flatMapMany(newPropertyGroup ->
                    Flux.fromIterable(entry.getValue())
                        .flatMap(projectStage ->
                            this.subProjectPersistence.readById(projectStage.getId())
                                .flatMap(stage -> {
                                  SubProjectPropertyGroup subProjectPropertyGroup =
                                      new SubProjectPropertyGroup();
                                  subProjectPropertyGroup.setStage(stage);
                                  subProjectPropertyGroup.setPropertyGroup(newPropertyGroup);
                                  return this.subProjectPropertyGroupPersistence
                                      .create(subProjectPropertyGroup);
                                })
                        )
                )
            );

  }

  private void assertSubProjectExists(SubProjectPropertyGroup subProjectPropertyGroup) {
    final var stage = this.subProjectPersistence
        .readById(subProjectPropertyGroup.getStage().getId());
    if (Objects.isNull(stage)) {
      throw new NotFoundException("Non existent SubProject with id: "
          + subProjectPropertyGroup.getStage().getId());
    }
  }

  /**
   * Retrieves all {@link SubProjectPropertyGroup} associations for a given project,
   * identified by tax identification number and project ID.
   * Ensures the project exists before fetching associated property groups
   * for each project stage.
   *
   * @param taxIdentificationNumber the tax identification number of the project owner
   * @param projectId the unique identifier of the project
   * @return a {@link Flux} emitting the found {@link SubProjectPropertyGroup} associations
   */
  public Flux<SubProjectPropertyGroup> readByTaxIdentificationNumberAndProjectId(
      String taxIdentificationNumber, Integer projectId) {
    return this.assertProjectExists(taxIdentificationNumber, projectId)
        .flatMapMany(project -> Flux.fromIterable(project.getProjectStages()))
        .flatMap(projectStage ->
          Flux.fromIterable(this.subProjectPropertyGroupPersistence
              .findAllBySubProjectId(projectStage.getId()))
        );
  }

  /**
   * Asserts that a project exists for the given tax identification number and
   * project ID. Retrieves the project from persistence. If not found, returns
   * an empty {@link Mono}.
   *
   * @param taxIdentificationNumber the tax identification number of the company
   * @param projectId the unique identifier of the project
   * @return a {@link Mono} emitting the found {@link Project} or empty if not found
   * @author denilssonmn
   */
  private Mono<Project> assertProjectExists(String taxIdentificationNumber, Integer projectId) {
    return this.projectPersistence
        .readByTaxIdentificationNumberAndId(taxIdentificationNumber, projectId);
  }

  /**
   * Deletes a property group association for a specific project stage and real
   * estate company. Delegates to {@link PropertyGroupService#delete(String,
   * Integer, Integer)} to perform the deletion.
   *
   * @param taxIdentificationNumber the tax identification number of the company
   * @param projectId the identifier of the project
   * @param stagePropertyGroupId the identifier of the stage property group
   * @return a {@link Mono} signaling completion when the association is deleted
   * @author denilssonmn
   */
  public Mono<Void> delete(String taxIdentificationNumber, Integer projectId,
      Integer stagePropertyGroupId) {
    return this.assertProjectExists(taxIdentificationNumber, projectId)
        .flatMap(project ->
            this.subProjectPropertyGroupPersistence.deleteById(stagePropertyGroupId));
  }

  /**
   * Deletes all property group associations for a specific property group,
   * project, and real estate company. Delegates to
   * {@link SubProjectPropertyGroupPersistence#deleteAllByPropertyGroup(Integer)}
   * after ensuring the project exists.
   *
   * @param taxIdentificationNumber the tax identification number of the company
   * @param projectId the identifier of the project
   * @param propertyGroupId the identifier of the property group
   * @return a {@link Mono} signaling completion when all associations are deleted
   * @author denilssonmn
   */
  public Mono<Void> deleteAllByPropertyGroup(String taxIdentificationNumber,
      Integer projectId, Integer propertyGroupId) {
    return this.assertProjectExists(taxIdentificationNumber, projectId)
        .flatMap(project ->
            this.subProjectPropertyGroupPersistence.deleteAllByPropertyGroup(propertyGroupId));
  }

  /**
   * Assigns property group associations to subprojects. For each association,
   * ensures the subproject and property group exist, sets the references, and
   * persists the association.
   *
   * @param subProjectPropertyGroupList the list of associations to assign
   * @return a {@link Flux} emitting the assigned associations
   * @author denilssonmn
   */
  public Flux<SubProjectPropertyGroup> assign(
      @Valid List<SubProjectPropertyGroup> subProjectPropertyGroupList) {
    List<SubProjectPropertyGroup> safeList = Optional.ofNullable(subProjectPropertyGroupList)
        .orElseGet(ArrayList::new);

    return Flux.fromIterable(safeList)
        .flatMap(subProjectPropertyGroup -> {
          final var stage = this.subProjectPersistence
              .readById(subProjectPropertyGroup.getStage().getId());
          final var propertyGroup = this.propertyGroupPersistence
              .readById(subProjectPropertyGroup.getPropertyGroup().getId());

          return Mono.zip(stage, propertyGroup)
              .flatMap(tuple -> {
                subProjectPropertyGroup.setStage(tuple.getT1());
                subProjectPropertyGroup.setPropertyGroup(tuple.getT2());
                return Mono.just(subProjectPropertyGroup);
              })
              .flatMap(this.subProjectPropertyGroupPersistence::create);
        });
  }

  /**
   * Duplicates property group associations for the provided list. For each
   * property group, creates a new instance and assigns it to the specified
   * project stages, persisting the new associations.
   *
   * @param subProjectPropertyGroupList the list of associations to duplicate
   * @return a {@link Flux} emitting the duplicated associations
   * @author denilssonmn
   */
  public Flux<SubProjectPropertyGroup> duplicate(
      @Valid List<SubProjectPropertyGroup> subProjectPropertyGroupList) {
    Map<PropertyGroup, List<ProjectStage>> stagesByPropertyGroup =
        subProjectPropertyGroupList.stream()
        .filter(spg -> spg.getPropertyGroup() != null && spg.getPropertyGroup().getId() != null)
        .collect(Collectors.groupingBy(
            SubProjectPropertyGroup::getPropertyGroup,
            Collectors.mapping(SubProjectPropertyGroup::getStage, Collectors.toList())
        ));

    return Flux.fromIterable(stagesByPropertyGroup.entrySet())
        .flatMap(entry ->
            this.propertyGroupPersistence.readById(entry.getKey().getId())
                .flatMapMany(propertyGroupBase -> {
                  propertyGroupBase.setId(null);
                  propertyGroupBase.setName(entry.getKey().getName());
                  return this.propertyGroupPersistence.create(propertyGroupBase)
                      .flatMapMany(newPropertyGroup ->
                          Flux.fromIterable(entry.getValue())
                              .flatMap(projectStage ->
                                  this.subProjectPersistence.readById(projectStage.getId())
                                      .flatMap(stage -> {
                                        SubProjectPropertyGroup subProjectPropertyGroup =
                                            new SubProjectPropertyGroup();
                                        subProjectPropertyGroup.setStage(stage);
                                        subProjectPropertyGroup.setPropertyGroup(newPropertyGroup);
                                        return this.subProjectPropertyGroupPersistence
                                            .create(subProjectPropertyGroup);
                                      })
                              )
                      );
                })
        );
  }

  /**
   * Updates property group associations for the provided list. For each
   * association, updates the corresponding property group and returns the
   * updated association.
   *
   * @param subProjectPropertyGroupList the list of associations to update
   * @return a {@link Flux} emitting the updated associations
   * @author denilssonmn
   */
  public Flux<SubProjectPropertyGroup> update(
      @Valid List<SubProjectPropertyGroup> subProjectPropertyGroupList) {
    return Flux.fromIterable(subProjectPropertyGroupList)
        .flatMap(subProjectPropertyGroup ->
          this.propertyGroupPersistence.update(
              subProjectPropertyGroup.getPropertyGroup().getId(),
                  subProjectPropertyGroup.getPropertyGroup())
          .map(propertyGroup -> subProjectPropertyGroup)
        );
  }
}
