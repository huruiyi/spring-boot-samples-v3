```java

@Entity
public class Student {

  @Id
  @GeneratedValue
  private Long id;

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "passport_id")
  private Passport passport;

  @ManyToMany
  @JoinTable(name = "STUDENT_COURSE",
      joinColumns = @JoinColumn(name = "STUDENT_ID"),
      inverseJoinColumns = @JoinColumn(name = "COURSE_ID"))
  private List<Course> courses = new ArrayList<>();
}
```

```java

@Entity
public class Passport {

  @Id
  @GeneratedValue
  private Long id;

  @OneToOne(fetch = FetchType.LAZY, mappedBy = "passport")
  private Student student;
}
```

```java

@Entity
public class Course {

  @Id
  @GeneratedValue
  private Long id;

  @OneToMany(mappedBy = "course")
  private List<Review> reviews = new ArrayList<>();

  @ManyToMany(mappedBy = "courses")
  private List<Student> students = new ArrayList<>();
}
```

```java

@Entity
public class Review {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  private Course course;
}
```

a） 只有OneToOne,OneToMany,ManyToMany上才有mappedBy属性，ManyToOne不存在该属性；

b） mappedBy标签一定是定义在the owned side（被拥有方的），它指向theowning side（拥有方）；

c） 关系的拥有方负责关系的维护，在拥有方建立外键，所以用到@JoinColumn

d）mappedBy跟JoinColumn/JoinTable总是处于互斥的一方。