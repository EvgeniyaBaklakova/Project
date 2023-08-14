package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.GroupBookmarksDao;
import com.javamentor.qa.platform.models.entity.GroupBookmark;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class GroupBookmarksDaoImpl extends ReadWriteDaoImpl<GroupBookmark, Long> implements GroupBookmarksDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<String> getGroupBookMarkByName(Long authUserId) {
        return (List<String>) entityManager.createQuery("SELECT g.title FROM GroupBookmark g join g.bookMarks b where b.user.id = :authUserId")
                .setParameter("authUserId", authUserId).getResultList();
    }
}
