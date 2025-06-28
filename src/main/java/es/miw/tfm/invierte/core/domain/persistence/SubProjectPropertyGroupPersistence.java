package es.miw.tfm.invierte.core.domain.persistence;

import es.miw.tfm.invierte.core.domain.model.SubProjectPropertyGroup;
import java.util.List;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Defines persistence operations for SubProjectPropertyGroup entities.
 *
 * @author denilssonmn
 */

@Repository
public interface SubProjectPropertyGroupPersistence {

  Mono<SubProjectPropertyGroup> create(SubProjectPropertyGroup subProjectPropertyGroup);

  List<SubProjectPropertyGroup> findAllBySubProjectId(Integer subProjectId);

  Mono<Void> deleteById(Integer stagePropertyGroupId);

  Mono<Void> deleteAllByPropertyGroup(Integer propertyGroupId);
}
