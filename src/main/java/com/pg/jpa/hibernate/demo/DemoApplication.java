package com.pg.jpa.hibernate.demo;

import com.pg.jpa.hibernate.demo.entity.Course;
import com.pg.jpa.hibernate.demo.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private CourseRepository courseRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        Course course = courseRepository.findById(1001L);

        log.info("Course -> {}", course);

        //courseRepository.deleteById(1001L);

        //Create a new course
        courseRepository.save(new Course("Peter"));

        //Play with entity manager
        courseRepository.playWithEntityManager();
	}
}
