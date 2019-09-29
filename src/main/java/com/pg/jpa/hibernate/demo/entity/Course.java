package com.pg.jpa.hibernate.demo.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Course") //If we change the name to CourseDetails - JPA will create the table name in SQL as CourseDetails
//If the table name is not different, we do not need @Table

//Below is a named query and query_get_all_courses can be used everywhere in code or in tests.
//Go over to JPQLTest to see how we used the named query below
@NamedQueries(
    value = {
        @NamedQuery(name = "query_get_all_courses", query = "Select c from Course c"),
        @NamedQuery(name = "query_get_all_peters_courses", query = "Select c from Course c where fullname like '%Peter'")
    }
)
public class Course {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "fullname", nullable = false) //Name cannot have null value
    private String name;

    //@UpdateTimestamp and @CreationTimestamp
    //Remember that the above 2 annotations are provided by HIBERNATE and not JPA!

    @UpdateTimestamp
    private LocalDateTime lastUpdatedDate; //LocalDateTime is provided by Java 8 for clarity, earlier we used java.util.Date which was unclear

    @CreationTimestamp
    private LocalDateTime createdDate;

    //JPA Mandates a default no-arg constructor
    protected Course() {}

    //Hibernate will provide the ID
    public Course(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
