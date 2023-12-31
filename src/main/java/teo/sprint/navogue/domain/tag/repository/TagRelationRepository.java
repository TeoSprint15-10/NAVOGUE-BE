package teo.sprint.navogue.domain.tag.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teo.sprint.navogue.domain.tag.data.entity.TagRelation;

import java.util.List;

public interface TagRelationRepository extends JpaRepository<TagRelation, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM TagRelation r WHERE r.tag.name IN :tagNames AND r.memo.id = :memoId")
    void deleteByMemoIdAndTagNames(int memoId, List<String> tagNames);

    @Query("SELECT DISTINCT tr.tag.name FROM TagRelation tr WHERE tr.memo.user.id = :id")
    List<String> findAllByUser(Long id);

    @Transactional
    void deleteByMemoId(int id);

    boolean existsByMemoIdAndTagName(@Param("memoId") int memoId, @Param("tagName") String tagName);
}
