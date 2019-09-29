package com.pg.jpa.hibernate.demo.repository;

import com.pg.jpa.hibernate.demo.entity.Course;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest //This annotation runs the DemoApplication.class - runs the context
public class NativeQueryTest {
    private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    EntityManager manager;

	@Test
	public void native_query_basic() {
	    List resultList =  manager.createNativeQuery("Select * from Course", Course.class).getResultList();
        log.info("Select * from Course -> {}", resultList);
	}

    @Test
    public void native_query_basic_with_parameter() {
        Query query =  manager.createNativeQuery("Select * from Course where id = ?", Course.class);
        query.setParameter(1, 1001L);
        List<Course> resultWithWhereClause = query.getResultList();
        log.info("Select * from Course where id = ? -> {}", resultWithWhereClause);
    }

    @Test
    public void native_query_basic_with_named_parameter() {
        Query query =  manager.createNativeQuery("Select * from Course where id = :id", Course.class);
        query.setParameter("id", 1001L);
        List<Course> resultWithNamedParameter = query.getResultList();
        log.info("Select * from Course where id = :id -> {}", resultWithNamedParameter);
    }

    @Test
    @Transactional
    public void native_query_update_all_rows() {
        Query query =  manager.createNativeQuery("update COURSE set last_updated_date = sysdate()", Course.class);
        int numberOfRowsUpdated = query.executeUpdate();
        log.info("Bulk Update of rows -> {}", numberOfRowsUpdated);
    }
}
