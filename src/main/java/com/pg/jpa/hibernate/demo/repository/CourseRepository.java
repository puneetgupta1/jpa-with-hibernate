package com.pg.jpa.hibernate.demo.repository;

import com.pg.jpa.hibernate.demo.entity.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class CourseRepository {

    Logger log = LoggerFactory.getLogger(CourseRepository.class);

    @Autowired
    private EntityManager entityManager;

    public Course findById(Long id) {
        return entityManager.find(Course.class, id);
    }

    //If you do not have @Transactional then remove will complain about it. If you are doing any change any data, @Transactional is needed.
    public void deleteById(Long id) {
        Course course = findById(id);
        entityManager.remove(course);
    }

    public Course save(Course course) {
        if (course.getId() == null) {
            entityManager.persist(course); //insert a row
        } else {
            entityManager.merge(course); //update a row
        }

        return course;
    }

    public void playWithEntityManager() {
        log.info("Inside playWithEntityManager()");

        Course course1 = new Course("Web Services in 100 steps");
        entityManager.persist(course1);
        entityManager.flush(); //This saves into DB at this step

        course1.setName("Web Services in 100 steps - updated");
        //The above will update the data in database!!! - IMPORTANT
        //This is because of the @Transactional annotation - entityManager will keep track of all changes I do and persist them to DB
        //We did not have to do em.merge()!!!
        entityManager.flush();

        //Create another course
        Course course2 = new Course("Microservices in 200 steps");
        entityManager.persist(course2);
        entityManager.flush();

        //What detach does is that it will stop updating course2 after a detach is called on it
        entityManager.detach(course2);

        //Another way of clearing EntityManager is to called - entityManager.clear()
        //Above will clear out Course1 and Course2 both!!!

        //Below lines will have no affect in DB because detach was called above - entitymanager will not track this entity
        course2.setName("Microservices in 200 steps - updated");
        entityManager.flush();
    }
}
