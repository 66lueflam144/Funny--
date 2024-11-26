package fast.skyss.firstclass.repository;

import fast.skyss.firstclass.entity.basicEntity.Post_ci;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post_ci, Long> {
    List<Post_ci> findAllByOrderByCreateTimeDesc();
    List<Post_ci> findByUserId(Long userId);
    List<Post_ci> findByIsPublicTrueOrderByCreateTimeDesc();

    @Modifying
    @Transactional
    @Query("DELETE FROM Post_ci p WHERE p.user.id = :userId")
    void deleteAllByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Post_ci p WHERE p.id = :postId")
    void deletePostById(@Param("postId") Long postId);

    @Query("SELECT p FROM Post_ci p WHERE LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Post_ci> searchByKeyword(@Param("keyword") String keyword);
}
