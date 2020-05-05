package org.fasttrackit.simpleexercise.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vacations")
public class Vacation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "location")
    private String location;

    @Column(name = "price")
    private Integer price;

    @Column(name = "duration")
    private Integer duration;

    public Vacation(String location, Integer price, Integer duration) {
        this.location = location;
        this.price = price;
        this.duration = duration;
    }
}
