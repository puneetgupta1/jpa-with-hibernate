package com.pg.jpa.hibernate.demo.repository;

import com.pg.jpa.hibernate.demo.entity.Course;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest //This annotation runs the DemoApplication.class - runs the context
public class CourseRepositoryTest {

	@Autowired
    CourseRepository repository;

	@Test
	public void findById_basic() {
	    Course course = repository.findById(1001L);
	    assertEquals("Puneet", course.getName());
	}

    @Test
    @DirtiesContext //Spring will automatically reset the data once the test is run because we are deleting an entry from DB
    public void deleteById_basic() {
        repository.deleteById(1002L);
        assertNull(repository.findById(1002L));
    }

    @Test
    @DirtiesContext
    public void save_basic() {
        Course course = repository.findById(1002L);
        assertEquals("Ria", course.getName());

        course.setName("JPA in 50 steps - Updated");

        Course course1 = repository.findById(1002L);
        assertEquals("JPA in 50 steps - Updated", course.getName());
    }
}
