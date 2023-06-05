package com.javamentor.qa.platform.dao.impl.pagination;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.tag.TagViewDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository("TagPageDtoDaoByDateImpl")
public class TagPageDtoDaoByDateImpl implements PageDtoDao<TagViewDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TagViewDto> getItems(PaginationData properties) {

        int itemsOnPage = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * itemsOnPage;
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.tag.TagViewDto(t.id, t.name, t.description," +
                "(SELECT COUNT(q.id) FROM Question q JOIN q.tags as qt WHERE qt.id = t.id)," +
                "(SELECT COUNT(q.id) FROM Question q JOIN q.tags as qt WHERE (qt.id = t.id) AND (q.persistDateTime >= now() - interval '24 hours'))," +
                "(SELECT COUNT(q.id) FROM Question q JOIN q.tags as qt WHERE (qt.id = t.id) AND (q.persistDateTime >= now() - interval '7 days')))" +
                "FROM Tag t GROUP BY q.persistDateTime ORDER BY q.persistDateTime";
//        "(SELECT count(id), persist_date FROM Tag INNER JOIN question_has_tag qht on tag.id = qht.tag_id where persist_date >= now() - interval '6 day' GROUP BY persist_date ORDER BY persist_date DESC)"

        return entityManager.createQuery(hql)
                .setFirstResult(offset)
                .setMaxResults(itemsOnPage)
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public TagViewDto transformTuple(Object[] objects, String[] strings) {
                        return new TagViewDto(
                                (Long) objects[0],
                                (String) objects[1],
                                (String) objects[2],
                                (Long) objects[3],
                                (Long) objects[4],
                                (Long) objects[5]
                        );
                    }
                    @Override
                    public List transformList(List list) {return list;}
        })
                .getResultList();
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {

        return (Long) entityManager
                .createQuery("select count(t.id) from Tag t")
                .getSingleResult();
    }
}
