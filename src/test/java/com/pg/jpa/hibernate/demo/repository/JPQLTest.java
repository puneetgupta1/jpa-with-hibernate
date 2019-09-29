package com.pg.jpa.hibernate.demo.repository;

import com.pg.jpa.hibernate.demo.entity.Course;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest //This annotation runs the DemoApplication.class - runs the context
public class JPQLTest {
    private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    EntityManager manager;

	@Test
	public void jpql_basic() {
	    List resultList =  manager.createQuery("Select c from Course c").getResultList();

        log.info("Select c from Course c -> {}", resultList);
        /*
            The above will print something like:

            main] c.p.j.h.demo.repository.JPQLTest         :
                Select c from Course c -> [Course{id=1, name='Peter'}, Course{id=2, name='Web Services in 100 steps - updated'},
                Course{id=3, name='Microservices in 200 steps'}
         */
	}

    @Test
    public void jpql_typed() {
        TypedQuery<Course> query = manager.createQuery("Select c from Course c", Course.class); //Use the NamedQuery to test below
        List resultList = query.getResultList();
        log.info("TypedQuery - Select c from Course c -> {}", resultList);
    }

    @Test
    public void jpql_typed_with_where_clause() {
        TypedQuery<Course> query = manager.createQuery("Select c from Course c where fullname like '%Peter'", Course.class);
        List resultList = query.getResultList();
        log.info("Where clause - TypedQuery - Select c from Course c -> {}", resultList);
    }
}
